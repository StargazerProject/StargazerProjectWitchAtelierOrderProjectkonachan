package com.stargazer.witchatelier.model.event;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import com.mongodb.ReflectionDBObject;
import com.pisual.witchatelier.dao.impl.OrderMongoDBMethod;
import com.stargazer.witchatelier.model.shared.Entity;

/**
 * Pisual Order CElls系统的核心领域模型
 * @author felixsion
 * 
 * **/
public class Order extends ReflectionDBObject implements Entity<Order>,Serializable{
	
	private static final long serialVersionUID = -3094263133312630338L;
	private OrderID orderID;
	private OrderMessage orderMessage;
	private User user;
	private OrderMongoDBMethod orderMongoDBMethod;
	
	public Order(OrderID orderID,OrderMessage orderMessage,User user) {
		Validate.notNull(orderID, "Some orderID is required");
		Validate.notNull(orderMessage, "Some orderMessage is required");
		Validate.notNull(user, "Some user is required");
		this.orderID = orderID;
		this.orderMessage = orderMessage;
		this.user = user;
	}
	/**保存单个对象**/
	public void save()
	{
		orderMongoDBMethod.saveOrder(this);
	}
	@Override
	public boolean sameIdentityAs(Order other) {
		return false;
	}
	public OrderID OrderID() {
		return orderID;
	}
	public OrderMessage OrderMessage() {
		return orderMessage;
	}
	public User User() {
		return user;
	}
	public OrderID getOrderID() {
		return orderID;
	}
	public void setOrderID(OrderID orderID) {
		this.orderID = orderID;
	}
	public OrderMessage getOrderMessage() {
		return orderMessage;
	}
	public void setOrderMessage(OrderMessage orderMessage) {
		this.orderMessage = orderMessage;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
