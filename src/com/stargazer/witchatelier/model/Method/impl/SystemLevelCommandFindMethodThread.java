package com.stargazer.witchatelier.model.Method.impl;

import java.lang.reflect.Method;

import com.stargazer.witchatelier.model.event.Order;

public class SystemLevelCommandFindMethodThread extends Thread {
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
			Class<SystemLevelCommandMrthod> systemLevelCommandMrthod = (Class<SystemLevelCommandMrthod>) Class.forName("com.stargazer.witchatelier.model.Method.impl.SystemLevelCommandMrthod");
			Method method = systemLevelCommandMrthod.getMethod(order.getOrderMessage().getOrderMethod().getMethod().get(0).getMethodName(), SystemLevelCommandMrthod.class);
			method.invoke(systemLevelCommandMrthod.newInstance(), order);
		} catch (Exception e) {
		}
	}
}
