package com.stargazer.witchatelier.model.Method.impl;

import java.lang.reflect.Method;

import com.stargazer.witchatelier.model.event.Order;

public class TransactionLevelCommandFindMethodThread extends Thread {
	private Order order;
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	/** 无阻塞的监听队列 **/
	@Override
	public void run() {
		try {
			int taskNum = order.getOrderMessage().getOrderMethod().getMethod().size();
			for(int i=0;i<taskNum;i++)
			{
				Class<TransactionLevelCommandMrthod> transactionLevelCommandMrthod = (Class<TransactionLevelCommandMrthod>) Class.forName("com.stargazer.witchaaelier.model.Method.impl.TransactionLevelCommandMrthod");
				Method method = transactionLevelCommandMrthod.getMethod(order.getOrderMessage().getOrderMethod().getMethod().get(i).getMethodName(), com.stargazer.witchatelier.model.event.Method.class);
				method.invoke(transactionLevelCommandMrthod.newInstance(), order.getOrderMessage().getOrderMethod().getMethod().get(i));
			}
		} catch (Exception e) {
		}
	}
}
