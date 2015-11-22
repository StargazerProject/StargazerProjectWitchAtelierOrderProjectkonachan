package com.pisual.witchatelier.eventtranslator;

import com.lmax.disruptor.EventTranslatorThreeArg;
import com.lmax.disruptor.RingBuffer;
import com.pisual.witchatelier.event.OrderEvent;
import com.stargazer.witchatelier.model.event.Order;

public class OrderEventTranslator {
	private final RingBuffer<OrderEvent> ringBuffer;

	public OrderEventTranslator(RingBuffer<OrderEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	private static final EventTranslatorThreeArg<OrderEvent, Order, String, String> TRANSLATOR = new EventTranslatorThreeArg<OrderEvent, Order, String, String>() {
		@Override
		public void translateTo(OrderEvent event, long arg1, Order order,
				String title, String content) {
			event.setOrder(order);
		}
	};

	public void onData(Order order, String title, String content) {
		ringBuffer.publishEvent(TRANSLATOR, order, title, content);
	}
}