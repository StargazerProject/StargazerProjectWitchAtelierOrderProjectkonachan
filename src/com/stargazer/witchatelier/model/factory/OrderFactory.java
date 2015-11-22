package com.stargazer.witchatelier.model.factory;

import java.util.ArrayList;
import java.util.List;

import com.stargazer.witchatelier.model.event.Method;
import com.stargazer.witchatelier.model.event.MethodParameter;
import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.model.event.OrderID;
import com.stargazer.witchatelier.model.event.OrderMessage;
import com.stargazer.witchatelier.model.event.OrderMethod;
import com.stargazer.witchatelier.model.event.User;
import com.stargazer.witchatelier.util.socketnio.SerializableUtil;

public class OrderFactory {
	public Order order(List<Method> method,String name,String password,String orderLevel,String orderGetIP,String orderSentIP,Integer orderGetPort,Integer orderSentPort,String targetCellsName)
	{
		OrderID orderID = new OrderID();
		OrderMethod orderMethod = new OrderMethod(method);
		OrderMessage orderMessage = new OrderMessage(orderLevel,orderGetIP,orderSentIP,orderMethod,orderGetPort,orderSentPort,targetCellsName);
		User user = new User(name,password);
		Order order = new Order(orderID,orderMessage,user);
		return order;
	}
	
	public Order SampleDateOrder()
	{
		OrderID orderID = new OrderID();
		List<MethodParameter> parameter = new ArrayList<MethodParameter>();
		MethodParameter mp1 = new MethodParameter("SystemSample");
		MethodParameter mp2 = new MethodParameter("SystemSample");
		parameter.add(mp1);
		parameter.add(mp2);
		List<Method> method = new ArrayList<Method>();
		Method m1 = new Method("SystemSample", 2, parameter);
		method.add(m1);
		OrderMethod orderMethod = new OrderMethod(method);
		OrderMessage om1 = new OrderMessage("Sample order Level","10.0.1.13","10.0.1.13",orderMethod,10240,10240,"Yandecg");
	    User user = new User("felix","creat");
		Order order = new Order(orderID,om1,user);
		return order;
	}
	public Order SampleMessgeOrder()
	{
		OrderID orderID = new OrderID();
		List<MethodParameter> parameter = new ArrayList<MethodParameter>();
		MethodParameter mp1 = new MethodParameter("Pisual Message Title Test");
		parameter.add(mp1);
		MethodParameter mp2 = new MethodParameter("Pisual Message Concent Test");
		parameter.add(mp2);
		MethodParameter mp3 = new MethodParameter("Pisual Message Concent Test");
		parameter.add(mp3);
		List<Method> method = new ArrayList<Method>();
		Method m1 = new Method("SystemMessageTest", 2, parameter);
		method.add(m1);
		OrderMethod orderMethod = new OrderMethod(method);
		OrderMessage om1 = new OrderMessage("String orderLevel","10.0.1.13","10.0.1.13",orderMethod,10240,10240,"Yandecg");
	    User user = new User("felix","password");
		Order order = new Order(orderID,om1,user);
		return order;
	}
}
