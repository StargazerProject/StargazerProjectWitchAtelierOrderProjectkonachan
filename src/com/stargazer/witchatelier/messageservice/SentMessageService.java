package com.stargazer.witchatelier.messageservice;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.model.factory.OrderFactory;
import com.stargazer.witchatelier.util.socketnio.NIOServer;

public class SentMessageService {
	public SentMessageService(String title,String content) {
		try {
			SocketChannel socketChannel = SocketChannel.open();
			SocketAddress socketAddress = new InetSocketAddress("10.0.1.25",10240);
			socketChannel.connect(socketAddress);
			OrderFactory o = new OrderFactory();
			Order order = o.SampleMessgeOrder();
			order.getOrderMessage().getOrderMethod().getMethod().get(0).getParameter().get(0).setParameter(title);
			order.getOrderMessage().getOrderMethod().getMethod().get(0).getParameter().get(1).setParameter(content);
			NIOServer.sendData(socketChannel, order);
			socketChannel.socket().shutdownOutput();
		} catch (Exception ex) {
			System.out.println("me: " + ex.getMessage());
		}
	}
}