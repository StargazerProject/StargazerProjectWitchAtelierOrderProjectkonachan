package com.pisual.witchatelier.eventfactory;

import com.lmax.disruptor.EventFactory;
import com.pisual.witchatelier.event.OrderEvent;

public class OrderEventFactory implements EventFactory<OrderEvent> {
	@Override
	public OrderEvent newInstance() {
		return new OrderEvent();
	}
}
