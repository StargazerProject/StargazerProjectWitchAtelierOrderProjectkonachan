package com.pisual.witchatelier.start;

import com.pisual.liliaui.ui.LiliaMessageUIStart;
import com.stargazer.witchatelier.messageservice.MessageService;

public class MessageServerStart {
	public static void main(String[] args) {
		LiliaMessageUIStart liliaMessageUIStart = new LiliaMessageUIStart();
		MessageService messageService = new MessageService(10240);
		messageService.startMessageService();
	}
}
