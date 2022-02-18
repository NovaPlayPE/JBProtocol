package net.novatech.jbprotocol;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.gomint.jraknet.Connection;
import net.novatech.jbprotocol.bedrock.BedrockSession;
import net.novatech.jbprotocol.java.JavaSession;
import net.novatech.jbprotocol.tcp.TcpServerSession;
import net.novatech.jbprotocol.tcp.TcpSession;

public class SessionManager {
	
	private ProtocolServer server;
	
	public List<GameSession> sessions = new ArrayList<GameSession>();
	public Queue<Connection> comingBedrockConnections = new ConcurrentLinkedQueue<>();
	public Queue<TcpSession> comingJavaConnections = new ConcurrentLinkedQueue<>();
	
	public SessionManager(ProtocolServer server) {
		this.server = server;
	}
	
	public void tick() {
		if(server.getGameProtocol() == GameEdition.BEDROCK) {
			while(!this.comingBedrockConnections.isEmpty()) {
				System.out.println("Adding new session");
				Connection con = this.comingBedrockConnections.poll();
				if (con != null) {
					BedrockSession session = new BedrockSession(con);
					this.sessions.add(session);
					this.server.getServerListener().sessionConnected(session);
				}
			}
		} else {
			while(!this.comingJavaConnections.isEmpty()) {
				System.out.println("Adding new session");
				TcpSession con = this.comingJavaConnections.poll();
				if (con != null) {
					JavaSession session = new JavaSession(con);
					this.sessions.add(session);
					this.server.getServerListener().sessionConnected(session);
				}
			}
		}
		this.sessions.forEach(sus -> {sus.tick();});
	}
	
	public void addBedrockConection(Connection connection) {
		this.comingBedrockConnections.add(connection);
	}
	
	public void addJavaConection(TcpSession connection) {
		this.comingJavaConnections.add(connection);
	}
	
	public BedrockSession searchSession(Connection connection) {
		for(GameSession session : sessions) {
			if(!(session instanceof BedrockSession)) return null;
			BedrockSession sus = (BedrockSession) session;
			if(sus.getConnection() == connection) {
				return sus;
			}
		}
		return null;
	}
	
	public JavaSession searchSession(TcpServerSession tcp) {
		for(GameSession session : sessions){
			if(!(session instanceof JavaSession)) return null;
			JavaSession sus = (JavaSession)session;
			if(sus.getMcConnection() == tcp) {
				return sus;
			}
		}
		return null;
	}
	
	
}