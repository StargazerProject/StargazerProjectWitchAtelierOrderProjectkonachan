package com.pisual.witchatelier.eventhandler;

import org.apache.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.pisual.witchatelier.event.OrderEvent;

public class OrderEventHandler implements EventHandler<OrderEvent> {
	/**日志初始化**/
	static Logger logger = Logger.getLogger(OrderEventHandler.class.getName());
	@Override
	public void onEvent(OrderEvent orderEvent, long sequence,
			boolean endOfBatch) throws Exception {
		logger.info("Disruptor 任务开始 任务ID: " + orderEvent.getOrder().getOrderID().getID());
		//
	    //job
		//
	}
}
