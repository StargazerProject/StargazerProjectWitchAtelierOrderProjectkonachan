package com.stargazer.witchatelier.model.event;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.mongodb.ReflectionDBObject;
import com.stargazer.witchatelier.model.shared.ValueObject;

public class OrderMethod extends ReflectionDBObject implements ValueObject<OrderMethod>,Serializable{
	
	private static final long serialVersionUID = -7098056163781399388L;
	private List<Method> method;

	public OrderMethod(List<Method> method) {
		Validate.notEmpty(method);
		Validate.noNullElements(method);
		this.method = method;
	}

	/**获取方法**/
	public List<Method> Method() {
		return method;
	}
	
	public int totalWeight() {
		int totalWeight = 0;
		int num = this.method.size();
		for (int i = 0; i < num; i++) {
			totalWeight = totalWeight + method.get(i).getWeight();
		}
		return totalWeight;
	}
	
	public Method nextMethod()
	{
		return null;
	}

	@Override
	public boolean sameValueAs(OrderMethod other) {
		return false;
	}

	public List<Method> getMethod() {
		return method;
	}

	public void setMethod(List<Method> method) {
		this.method = method;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}