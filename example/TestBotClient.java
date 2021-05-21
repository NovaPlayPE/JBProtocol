import java.net.InetSocketAddress;
import java.util.concurrent.ThreadLocalRandom;

import net.novatech.jbprotocol.GameSession;
import net.novatech.jbprotocol.GameVersion;
import net.novatech.jbprotocol.ProtocolClient;
import net.novatech.jbprotocol.ServerConnectInfo;
import net.novatech.jbprotocol.bedrock.packets.*;
import net.novatech.jbprotocol.listener.ClientListener;
import net.novatech.jbprotocol.listener.GameListener;
import net.novatech.jbprotocol.listener.LoginListener;
import net.novatech.jbprotocol.packet.AbstractPacket;
import net.novatech.jbprotocol.util.SessionData;

public class TestBotClient {
	
	public GameSession session = null;
	
	public void createBot() {	
		InetSocketAddress targetAddress = new InetSocketAddress("1.2.3.4", 19132);
		InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", ThreadLocalRandom.current().nextInt(20000, 60000));
    	
		ProtocolClient client = new ProtocolClient(bindAddress.getAddress().toString(), bindAddress.getPort(), GameVersion.BEDROCK);
    		client.setClientListener(new ClientListener() {
			@Override
			public void sessionConnected(GameSession session) {
				TestBotClient.this.session = session;
				session.setGameListener(new GameListener() {
					@Override
					public void receivePacket(AbstractPacket packet) {
						System.out.println("Received packet " + packet.toString());		
						if(packet instanceof DissconnectPacket) {
							
						}
					}
				});
			}
			
			@Override
			public void sessionDisconnected(GameSession session, String cause) {}

			@Override
			public void sessionFailed(GameSession session, String cause) {}
    		
    		});
	    	client.connectTo(new ServerConnectInfo(targetAddress));
	}
	
	public void sendPacketToThisUnknownServer(AbstractPacket packet) {
		this.session.sendPacket(packet);
		System.out.println("Sent packet " + packet.toString());
	}
	
}
