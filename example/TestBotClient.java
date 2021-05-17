import java.net.InetSocketAddress;
import java.util.concurrent.ThreadLocalRandom;

import net.novatech.protocol.GameSession;
import net.novatech.protocol.GameVersion;
import net.novatech.protocol.ProtocolClient;
import net.novatech.protocol.ServerConnectInfo;
import net.novatech.protocol.listener.ClientListener;
import net.novatech.protocol.listener.GameListener;
import net.novatech.protocol.listener.LoginListener;
import net.novatech.protocol.packet.AbstractPacket;
import net.novatech.protocol.util.SessionData;

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
				session.setLoginListener(new LoginListener() {
					@Override
					public void loginCompleted(SessionData data) {
						System.out.println("Successfully connected to server");
					}
		    		});
				session.setGameListener(new GameListener() {
					@Override
					public void receivePacket(AbstractPacket packet) {
						System.out.println("Received packet " + packet.toString());						
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
