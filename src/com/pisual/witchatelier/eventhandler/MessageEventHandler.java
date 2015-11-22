package com.pisual.witchatelier.eventhandler;

import org.apache.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.pisual.witchatelier.event.MessageEvent;
import com.pisual.www.mail.PisualSimpleMail;

public class MessageEventHandler implements EventHandler<MessageEvent> {
	/**日志初始化**/
	static Logger logger = Logger.getLogger(MessageEventHandler.class.getName());
	@Override
	public void onEvent(MessageEvent messageEvent, long sequence,
			boolean endOfBatch) throws Exception {
		logger.info("Event: " + messageEvent + "Event: " + messageEvent.getMessage().getTitle() + "Event: " + messageEvent.getMessage().getContent());
		PisualSimpleMail pisualSimpleMail = new PisualSimpleMail(messageEvent.getMessage().getTitle(),messageEvent.getMessage().getContent());
	}
}
