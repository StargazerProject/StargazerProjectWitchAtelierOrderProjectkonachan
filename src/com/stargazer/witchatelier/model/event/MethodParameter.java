package com.stargazer.witchatelier.model.event;

import java.io.Serializable;

import com.mongodb.ReflectionDBObject;

public class MethodParameter extends ReflectionDBObject implements Serializable{

	private static final long serialVersionUID = 1383305984878202860L;
	private String parameter;
	/**获取参数**/
	public String Parameter() {
		return parameter;
	}
	public MethodParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}