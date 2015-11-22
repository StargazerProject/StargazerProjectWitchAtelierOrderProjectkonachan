package com.pisual.witchatelier.eventfactory;

import com.lmax.disruptor.EventFactory;
import com.pisual.witchatelier.event.MessageEvent;

public class MessageEventFactory implements EventFactory<MessageEvent> {
	@Override
	public MessageEvent newInstance() {
		return new MessageEvent();
	}
}
