package com.stargazer.witchatelier.messageservice;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.pisual.witchatelier.event.MessageEvent;
import com.pisual.witchatelier.eventfactory.MessageEventFactory;
import com.pisual.witchatelier.eventhandler.MessageEventHandler;
import com.pisual.witchatelier.eventtranslator.MessageEventTranslator;
import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

public class QueueUnitThread extends Thread {
	/** Disruptor任务队列 线程安全级别变量 FIFO类型 **/
	public ConcurrentLinkedQueue<Order> tasklist;
	/** Disruptor获取队列 **/
	MessageEventTranslator producer;
	/** 日志初始化 **/
	static Logger logger = Logger.getLogger(QueueUnitThread.class.getName());

	/**
	 * 初始化
	 * 
	 * @param tasklist
	 *            Disruptor任务队列 线程安全级别变量 FIFO类型
	 * **/
	public QueueUnitThread(ConcurrentLinkedQueue<Order> tasklist) {
		this.tasklist = tasklist;
		Executor executor = Executors.newCachedThreadPool();
		MessageEventFactory factory = new MessageEventFactory();
		int bufferSize = 8;
		Disruptor<MessageEvent> disruptor = new Disruptor<>(factory,
				bufferSize, executor);
		disruptor.handleEventsWith(new MessageEventHandler());
		disruptor.start();
		RingBuffer<MessageEvent> ringBuffer = disruptor.getRingBuffer();
		producer = new MessageEventTranslator(ringBuffer);
	}

	@Override
	public void run() {
		while (PropertiesUtil.getValue(
				"CenterControlMessageServiceMessageDisruptorStatus").equals(
				"true")) {
			if (tasklist.size() == 0) {
				try {
					Thread.sleep(Long.parseLong(PropertiesUtil.getValue("NullQueueWaitTime")));
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
				continue;
			} else {
				Order order = tasklist.poll();
				logger.info("New Order Has ListingOut! Order Id is:"
						+ order.OrderID().ID());
				producer.onData(order.OrderID().ID(), order.OrderMessage()
						.OrderMethod().Method().get(0).Parameter().get(0)
						.Parameter(), "<p>系统指令ID:"
						+ order.OrderID().ID()
						+ "</p>"
						+ order.OrderMessage().OrderMethod().Method().get(0)
								.Parameter().get(1).Parameter());
			}
		}
	}
}
