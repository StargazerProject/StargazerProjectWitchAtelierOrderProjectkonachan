package com.stargazer.witchatelier.messageservice;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.util.socketnio.NIOServer;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

/**
 * 消息系统监听模块
 * 
 * **/
public class MessageService {
	/** 日志初始化 **/
	static Logger logger = Logger.getLogger(MessageService.class.getName());
	/**Disrupter任务队列 线程安全级别变量 FIFO类型**/
	public ConcurrentLinkedQueue<Order> tasklist = new ConcurrentLinkedQueue<Order>();
	/** 通道管理器 **/
	 Selector selector;  
     ServerSocketChannel serverSocketChannel; 
    /**初始化NIO**/
	public MessageService(int port) {
		try {
			selector = Selector.open();  
	        serverSocketChannel = ServerSocketChannel.open();  
	        serverSocketChannel.configureBlocking(false);  
	        serverSocketChannel.socket().setReuseAddress(true);  
	        serverSocketChannel.socket().bind(new InetSocketAddress(port));  
	        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	/**指令交换服务器**/
	public void startMessageService()
	{
		QueueUnitThread queueUnitThread = new QueueUnitThread(tasklist);
		queueUnitThread.start();
		while(PropertiesUtil.getValue("ControlMessageStatus").equals("true"))
		{
			try {
				selector.select();
	            Iterator<SelectionKey> it = selector.selectedKeys().iterator();  
	            while (it.hasNext()) {  
	                SelectionKey readyKey = it.next();  
	                it.remove();
	                SocketChannel socketChannel = serverSocketChannel.accept();  
	                Order order =  NIOServer.receiveData(socketChannel);
	                logger.info("获取新指令 ID："+order.OrderID().ID());
	  	            tasklist.add(order);
	            } 
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}