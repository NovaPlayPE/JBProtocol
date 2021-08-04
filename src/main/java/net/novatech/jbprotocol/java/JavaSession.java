package net.novatech.jbprotocol.java;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.GameSession;
import net.novatech.jbprotocol.MinecraftProtocol;
import net.novatech.jbprotocol.ProtocolServer;
import net.novatech.jbprotocol.auth.GameProfile;
import net.novatech.jbprotocol.auth.SessionHandler;
import net.novatech.jbprotocol.java.data.JavaPong;
import net.novatech.jbprotocol.java.packets.JavaPacket;
import net.novatech.jbprotocol.java.packets.handshake.HandshakePacket;
import net.novatech.jbprotocol.java.packets.login.EncryptionRequestPacket;
import net.novatech.jbprotocol.java.packets.login.EncryptionResponsePacket;
import net.novatech.jbprotocol.java.packets.login.LoginStartPacket;
import net.novatech.jbprotocol.java.packets.login.LoginSuccessPacket;
import net.novatech.jbprotocol.java.packets.login.SetCompressionPacket;
import net.novatech.jbprotocol.java.packets.status.RequestPacket;
import net.novatech.jbprotocol.java.packets.status.ResponsePacket;
import net.novatech.jbprotocol.listener.GameListener;
import net.novatech.jbprotocol.listener.LoginListener;
import net.novatech.jbprotocol.listener.LoginServerListener;
import net.novatech.jbprotocol.listener.LoginClientListener;
import net.novatech.jbprotocol.packet.AbstractPacket;
import net.novatech.jbprotocol.tcp.TcpSession;
import net.novatech.jbprotocol.util.CryptUtils;
import net.novatech.jbprotocol.util.SessionData;
import net.novatech.library.utils.ByteBufUtils;

public class JavaSession implements GameSession {

	@Getter
	@Setter
	public LoginListener loginListener;
	@Getter
	@Setter
	public GameListener gameListener;
	@Getter
	@Setter
	public MinecraftProtocol protocol = null;
	@Getter
	@Setter
	private TcpSession mcConnection;
	
	private byte[] verifyToken = new byte[4];
	
	private boolean authRequired = false;
	
	@Getter
	private JavaSessionData sessionData;
	
	public JavaSession(TcpSession mcConnection) {
		this(mcConnection, false);
	}
	
	public JavaSession(TcpSession mcConnection, boolean isClient) {
		this.mcConnection = mcConnection;
		if(this.protocol == null) {
			this.protocol = new JavaProtocol(isClient);
		}
		Random rand = new Random();
		rand.nextBytes(this.verifyToken);
		
		this.sessionData = new JavaSessionData();
		this.sessionData.setAddress(this.mcConnection.getAddress());
	}
	
	public void requireAuthentication(boolean value) {
		this.authRequired = value;
	}

	@Override
	public void sendPacket(AbstractPacket pk) {
		this.mcConnection.sendPacket(pk);
	}

	@Override
	public void tick() {
		while(this.mcConnection.receivePacket() != null) {
			ByteBuf buf = this.mcConnection.receivePacket();
			int id = ByteBufUtils.readUnsignedVarInt(buf);
			if(getProtocol() instanceof JavaProtocol) {
				JavaProtocol protocol = (JavaProtocol)getProtocol();
				AbstractPacket packet = protocol.createPacket((byte)id);
				if(packet == null) {
					throw new NullPointerException("Packet with id " + id + " does not exist");
				}
				JavaPacket pk = (JavaPacket)packet;
				try {
					pk.read(buf);
				} catch (Exception e) {}
				if(protocol.isClient()) {
					handleClientPacket(pk, protocol);
				} else {
					handleServerPacket(pk, protocol);
				}
				if(protocol.getGameState() == JavaGameState.GAME) {
					getGameListener().receivePacket(pk);
				}
			}
		}
	}
	
	private void handleServerPacket(AbstractPacket pk, JavaProtocol protocol) {
		if(protocol.getGameState() == JavaGameState.HANDSHAKE) {
			HandshakePacket handshake = (HandshakePacket)pk;
			switch(handshake.state) {
			case STATUS:
				protocol.setGameState(JavaGameState.STATUS);
				protocol.registerPackets();
				break;
			case LOGIN:
				protocol.setGameState(JavaGameState.LOGIN);
				protocol.registerPackets();
				if(handshake.protocolVersion > protocol.getProtocolVersion()) {
					this.mcConnection.disconnect("Could not connect: Outdated client!");
				} else if(handshake.protocolVersion < protocol.getProtocolVersion()) {
					this.mcConnection.disconnect("Could not connect: Outdated server!");
				}
				break;
			}
		} else if(protocol.getGameState() == JavaGameState.STATUS) { 
			if(pk instanceof RequestPacket) {
				JavaPong pong = (JavaPong)ProtocolServer.getInstance().getPong();
				ProtocolServer.getInstance().getServerListener().handlePong(pong);
				
				ResponsePacket pongResponse = new ResponsePacket();
				pongResponse.pong = pong;
				this.sendPacket(pongResponse);
			}
		} else if(protocol.getGameState() == JavaGameState.LOGIN) {
			if(pk instanceof LoginStartPacket) {
				LoginStartPacket login = (LoginStartPacket)pk;
				this.sessionData.setUsername(login.username);
				if(!this.authRequired) {
					LoginSuccessPacket pk1 = new LoginSuccessPacket();
					sessionData.setUuid(UUID.nameUUIDFromBytes(("OfflinePlayer:"+login.username).getBytes(StandardCharsets.UTF_8)));
					
					pk1.uuid = sessionData.getUuid();
					pk1.username = sessionData.getUsername();
					this.sendPacket(pk1);
					protocol.setGameState(JavaGameState.GAME);
					protocol.registerPackets();
					
					LoginServerListener listener = (LoginServerListener)this.getLoginListener();
					listener.loginCompleted(this.sessionData);
					return;
				}
				EncryptionRequestPacket encrypt = new EncryptionRequestPacket();
				encrypt.publicKey = CryptUtils.JAVA_KEY.getPublic().getEncoded();
				encrypt.verifyToken = this.verifyToken;
				this.sendPacket(encrypt);
			} else if(pk instanceof EncryptionResponsePacket) {
				EncryptionResponsePacket encrypt = (EncryptionResponsePacket)pk;
				PrivateKey pKey = CryptUtils.JAVA_KEY.getPrivate();
				
				SecretKey sKey = CryptUtils.secretFromShared(pKey, encrypt.sharedKey);
				this.getMcConnection().setSecretKey(sKey);
				this.getMcConnection().updateSecretKey();
				
				authPlayer(sKey, protocol);
			}
		}
	}
	
	private synchronized void authPlayer(SecretKey sKey, JavaProtocol protocol) {
		String serverId = CryptUtils.generateServerId(sKey, CryptUtils.JAVA_KEY.getPublic());
		if(SessionHandler.getProfile(this.sessionData.getUsername(), serverId) != null) {
			GameProfile profile = SessionHandler.getProfile(this.sessionData.getUsername(), serverId);
			this.sessionData.setUuid(profile.getUuid());
			if(this.getMcConnection().getCompressionTreshold() >= 0) {
				SetCompressionPacket pk = new SetCompressionPacket();
				pk.treshold = this.getMcConnection().getCompressionTreshold();
				this.sendPacket(pk);
				this.getMcConnection().setCompressionTreshold(this.getMcConnection().getCompressionTreshold());
				this.getMcConnection().updateCompressionTreshold();
			}
			
			LoginSuccessPacket success = new LoginSuccessPacket();
			success.username = sessionData.getUsername();
			success.uuid = sessionData.getUuid();
			this.sendPacket(success);
			protocol.setGameState(JavaGameState.GAME);
			protocol.registerPackets();
			
			LoginServerListener listener = (LoginServerListener)this.getLoginListener();
			listener.loginCompleted(sessionData);
		}
		
	}
	
	private void handleClientPacket(AbstractPacket pk, JavaProtocol protocol) {

	}

}
