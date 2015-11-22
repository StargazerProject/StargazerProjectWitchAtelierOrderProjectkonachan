package com.stargazer.witchatelier.summusdensitatesegmentation;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.pisual.witchatelier.eventtranslator.MessageEventTranslator;
import com.stargazer.witchatelier.model.Method.impl.SystemLevelCommandMrthod;
import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

/**
 * 事务级Disrupter线程
 * @author felixsion
 * **/
public class SystemLevelTaskQueueUnitThread extends Thread {
	/** Disruptor任务队列 线程安全级别变量 FIFO类型 **/
	public ConcurrentLinkedQueue<Order> tasklist;
	/** Disruptor获取队列 **/
	MessageEventTranslator producer;
	/** 日志初始化 **/
	static Logger logger = Logger.getLogger(SystemLevelTaskQueueUnitThread.class.getName());

	/**
	 * 初始化
	 * 
	 * @param tasklist
	 *            Disruptor任务队列 线程安全级别变量 FIFO类型
	 * **/
	public SystemLevelTaskQueueUnitThread(ConcurrentLinkedQueue<Order> tasklist) {
		this.tasklist = tasklist;
	}

	@Override
	public void run() {
		while (PropertiesUtil.getValue("SystemServiceDisruptorStatus").equals("true")) {
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
					Class<SystemLevelCommandMrthod> systemLevelCommandMrthod = (Class<SystemLevelCommandMrthod>) Class.forName("com.stargazer.witchatelier.model.Method.impl.SystemLevelCommandMrthod");
					Method method = systemLevelCommandMrthod.getMethod(order.getOrderMessage().getOrderMethod().getMethod().get(0).getMethodName(),Order.class);
					method.invoke(systemLevelCommandMrthod.newInstance(), order);
				} catch (Exception e) {
					logger.error("指令调用过程出错 检查系统包名 "+e.getMessage());
				}
				logger.info("New Transaction Level Order Has ListingOut! Order Id is:"+ order.OrderID().getID());
			}
		}
	}
}
