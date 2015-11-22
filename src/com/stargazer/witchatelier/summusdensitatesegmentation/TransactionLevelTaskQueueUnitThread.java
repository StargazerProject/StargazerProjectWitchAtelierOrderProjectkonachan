package com.stargazer.witchatelier.summusdensitatesegmentation;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.pisual.witchatelier.dao.impl.OrderMongoDBMethod;
import com.pisual.witchatelier.eventtranslator.MessageEventTranslator;
import com.stargazer.witchatelier.model.Method.impl.TransactionLevelCommandMrthod;
import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

/**
 * 事务级Disrupter线程
 * @author felixsion
 * **/
public class TransactionLevelTaskQueueUnitThread extends Thread {
	/** Disruptor任务队列 线程安全级别变量 FIFO类型 **/
	public ConcurrentLinkedQueue<Order> tasklist;
	/** Disruptor获取队列 **/
	MessageEventTranslator producer;
	/** 日志初始化 **/
	static Logger logger = Logger.getLogger(TransactionLevelTaskQueueUnitThread.class.getName());
    /**数据库初始化**/
	OrderMongoDBMethod orderMongoDBMethod = new OrderMongoDBMethod();
	/**
	 * 初始化
	 * 
	 * @param tasklist
	 *            Disruptor任务队列 线程安全级别变量 FIFO类型
	 * **/
	public TransactionLevelTaskQueueUnitThread(ConcurrentLinkedQueue<Order> tasklist) {
		this.tasklist = tasklist;
	}

	@Override
	public void run() {
		while (PropertiesUtil.getValue("SummusDensitateSegmentationServiceDisruptorStatus").equals("true")) {
			if (tasklist.size() == 0) {
				try {
					Thread.sleep(Long.parseLong(PropertiesUtil.getValue("NullQueueWaitTime")));
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
				continue;
			} else {
				Order order = tasklist.poll();
				try {
					int taskNum = order.getOrderMessage().getOrderMethod().getMethod().size();
					for(int i=0;i<taskNum;i++)
					{
						Class<TransactionLevelCommandMrthod> transactionLevelCommandMrthod = (Class<TransactionLevelCommandMrthod>) Class.forName("com.stargazer.witchatelier.model.Method.impl.TransactionLevelCommandMrthod");
						Method method = transactionLevelCommandMrthod.getMethod(order.getOrderMessage().getOrderMethod().getMethod().get(i).getMethodName(), com.stargazer.witchatelier.model.event.Method.class);
						method.invoke(transactionLevelCommandMrthod.newInstance(), order.getOrderMessage().getOrderMethod().getMethod().get(i));
						order.getOrderMessage().getOrderMethod().getMethod().get(i).setIsCpmplete(true);
						orderMongoDBMethod.changeOrder(order);
					}
					/**更改标志为指令完成**/
					order.getOrderMessage().setOrdersComplete(true);
					/**删除完成的指令**/
					orderMongoDBMethod.delateOrder(order);
				} catch (Exception e) {
					logger.error("指令调用过程出错 检查系统包名 "+e.getMessage());
				}
				logger.info("本条远程指令已经执行完毕 调用标识"+order.getOrderMessage().getOrderMethod().getMethod().get(0).getMethodName()+" ID:"+ order.OrderID().getID());
			}
		}
	}
}
