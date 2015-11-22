package com.stargazer.witchatelier.summusdensitatesegmentation;


import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.model.factory.OrderFactory;
import com.stargazer.witchatelier.util.socketnio.NIOServer;

public class SentMessageService {
	public static void main(String[] args) {
		try {
			SocketChannel socketChannel = SocketChannel.open();
			SocketAddress socketAddress = new InetSocketAddress("10.0.1.12",
					10240);
			socketChannel.connect(socketAddress);
			OrderFactory o = new OrderFactory();
			Order order = o.SampleMessgeOrder();
			NIOServer.sendData(socketChannel, order);
			socketChannel.socket().shutdownOutput();
		} catch (Exception ex) {
			System.out.println("me: " + ex.getMessage());
		}
	}
}