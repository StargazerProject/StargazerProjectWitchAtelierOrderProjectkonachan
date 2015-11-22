package com.stargazer.witchatelier.summusdensitatesegmentation;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.pisual.witchatelier.dao.impl.OrderMongoDBMethod;
import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.util.socketnio.NIOServer;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

/**高密度切分核心
 * 高密度切分包含一个无限制的线程安全的队列入口
 * 所有通信需求通过NIO模块就收，迅速保存在OrderCache Mongodb数据库中，
 * 接下来进行Order初始化解析 如果为事务级指令，存储在高带宽的高速内存缓存 TransactionLevelTaskList 内
 * 如果为系统级指令 保存在低带宽的高速专用缓存 SystemLevelTaskList 内
 * TransactionLevelTaskList 与 SystemLevelTaskList 均为独立的Disrupter线程在运行所需的队列
 * 
 * 
 * **/
public class SummusDensitateSegmentationService {
	/** 日志初始化 **/
	static Logger logger = Logger.getLogger(SummusDensitateSegmentationService.class.getName());
	/**Disrupter任务队列 线程安全级别变量 FIFO类型**/
	public ConcurrentLinkedQueue<Order> transactionLevelTaskList = new ConcurrentLinkedQueue<Order>();
	/**Disrupter任务队列 线程安全级别变量 FIFO类型**/
	public ConcurrentLinkedQueue<Order> systemLevelTaskList = new ConcurrentLinkedQueue<Order>();
	/** 通道管理器 **/
	 Selector selector;  
     ServerSocketChannel serverSocketChannel; 
    /**初始化NIO**/
	public SummusDensitateSegmentationService(int port) {
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
		/**启动事务级Disrupter线程 No 1**/
		TransactionLevelTaskQueueUnitThread transactionLevelTaskQueueUnitThread = new TransactionLevelTaskQueueUnitThread(transactionLevelTaskList);
		transactionLevelTaskQueueUnitThread.start();
		/**启动系统级Disrupter线程**/
		SystemLevelTaskQueueUnitThread systemLevelTaskQueueUnitThread = new SystemLevelTaskQueueUnitThread(systemLevelTaskList);
		systemLevelTaskQueueUnitThread.start();
		while(PropertiesUtil.getValue("SummusDensitateSegmentationServiceListenStatus").equals("true"))
		{
			try {
				selector.select();
	            Iterator<SelectionKey> it = selector.selectedKeys().iterator();  
	            while (it.hasNext()) {  
	              	/**获取下一个链接**/
	                SelectionKey readyKey = it.next();  
	                /**移除本链接**/
	                it.remove();
	                SocketChannel socketChannel = serverSocketChannel.accept(); 
	                /**获取指令**/
	                Order order =  NIOServer.receiveData(socketChannel);
	                /**指令初始化保存**/
	                OrderMongoDBMethod orderMongoDBMethod = new OrderMongoDBMethod();
	                orderMongoDBMethod.saveOrder(order);
	                /****/
	                /**返还收到信息的状态**/
	                /****/
	                logger.info("获取新指令 ID："+order.OrderID().getID());
	                switch(order.getOrderMessage().getOrderLevel()) 
	                {
	                /**获取系统级指令 并且入队**/
	                case "SystemLevelCommand":
	                	systemLevelTaskList.add(order);
		            logger.info("系统级指令入队 ID："+order.OrderID().getID());
	                break;
	                /**获取事务级指令 并且入队**/
	                case "TransactionLevelCommand": 
	                	transactionLevelTaskList.add(order);
	                	logger.info("事务级指令入队 ID："+order.OrderID().getID());
	                break; 
	                default:
	                logger.error("未辨别的指令 ID："+order.OrderID().getID());
	                break; 
	                } 
	            } 
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}