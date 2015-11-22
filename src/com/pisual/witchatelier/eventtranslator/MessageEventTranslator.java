package com.pisual.witchatelier.eventtranslator;

import com.lmax.disruptor.EventTranslatorThreeArg;
import com.lmax.disruptor.RingBuffer;
import com.pisual.witchatelier.event.MessageEvent;
import com.pisual.witchatelier.model.Message;

public class MessageEventTranslator {
	private final RingBuffer<MessageEvent> ringBuffer;

	public MessageEventTranslator(RingBuffer<MessageEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	private static final EventTranslatorThreeArg<MessageEvent, String, String, String> TRANSLATOR = new EventTranslatorThreeArg<MessageEvent, String, String, String>() {
		@Override
		public void translateTo(MessageEvent event, long arg1, String id,
				String title, String content) {
			Message message = new Message();
			message.messageInit(id, title, content);
			event.setMessage(message);
		}
	};

	public void onData(String id, String title, String content) {
		ringBuffer.publishEvent(TRANSLATOR, id, title, content);
	}
}
