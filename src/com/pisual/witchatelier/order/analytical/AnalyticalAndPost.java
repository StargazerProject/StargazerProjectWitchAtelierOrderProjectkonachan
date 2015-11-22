package com.pisual.witchatelier.order.analytical;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import com.pisual.witchatelier.dao.impl.OrderBackUPMongoDBMethod;
import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.util.socketnio.NIOServer;

/**指令解析器 解析指令及其他操作**/
public class AnalyticalAndPost {
	/**日志初始化**/
	static Logger logger = Logger.getLogger(AnalyticalAndPost.class.getName());
	/**发送指令**/
	public static void postOrder(Order order)
	{
		try {
			SocketChannel socketChannel = SocketChannel.open();
			SocketAddress socketAddress = new InetSocketAddress(order.getOrderMessage().getOrderSentIP(),order.getOrderMessage().getOrderSentPort());
			socketChannel.connect(socketAddress);
			NIOServer.sendData(socketChannel, order);
			socketChannel.socket().shutdownOutput();
			OrderBackUPMongoDBMethod orderBackUPMongoDBMethod = new OrderBackUPMongoDBMethod();
			orderBackUPMongoDBMethod.saveOrder(order);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}
}