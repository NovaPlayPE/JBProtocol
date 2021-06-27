import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import net.novatech.jbprotocol.GameEdition;
import net.novatech.jbprotocol.GameSession;
import net.novatech.jbprotocol.ProtocolServer;
import net.novatech.jbprotocol.listener.GameListener;
import net.novatech.jbprotocol.listener.LoginServerListener;
import net.novatech.jbprotocol.listener.ServerListener;
import net.novatech.jbprotocol.packet.AbstractPacket;
import net.novatech.jbprotocol.util.SessionData;

public class TestServer {
	
	public ArrayList<GameSession> players = new ArrayList<GameSession>()
	
	public void startServer() {
		InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", 19132);
		
		ProtocolServer server = new ProtocolServer(bindAddress, GameEdition.BEDROCK);
		server.setMaxConnections(20);
		server.setServerListener(new ServerListener() {

			@Override
			public void sessionConnected(GameSession session) {
				session.requireAuthentication(true);
				session.setGameListener(new GameListener() {

					@Override
					public void receivePacket(AbstractPacket packet) {
						
					}
					
				});
				session.setLoginListener(new LoginServerListener() {

					@Override
					public void loginCompleted(SessionData data) {
						// TODO Auto-generated method stub
						
					}
					
				});
			}

			@Override
			public void sessionDisconnected(GameSession session, String cause) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
}