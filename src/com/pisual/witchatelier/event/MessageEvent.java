package com.pisual.witchatelier.event;

import com.pisual.witchatelier.model.Message;

public class MessageEvent {
private Message message;
	
	public void setMessage(Message message) {
		this.message = message;
	}
	public Message getMessage() {
		return message;
	}
}
