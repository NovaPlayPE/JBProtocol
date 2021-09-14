package net.novatech.jbprotocol.util;

public interface MessageConsumer<T> {
	
	void success();
	
	void failed(Throwable t);
	
}