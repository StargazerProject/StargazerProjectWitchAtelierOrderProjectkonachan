package com.stargazer.witchatelier.model.event;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.mongodb.ReflectionDBObject;
import com.stargazer.witchatelier.model.shared.Entity;

public class Method extends ReflectionDBObject implements Entity<Method>,Serializable {

	private static final long serialVersionUID = -6700138177098563949L;
	private String methodName;/**事件名称**/
	private Integer weight;/**事件权重**/
	private Boolean isCpmplete;/**时间是否完成 初始化为False**/
	private String needAuthority;
	private List<MethodParameter> parameter;/**参数包**/
	
	public Method(String methodName,Integer weight,List<MethodParameter> parameter) {
		Validate.notNull(methodName, "Some method is required");
		Validate.notNull(weight, "Some weight is required");
		Validate.notNull(parameter,"Some parameter is required");
		this.methodName = methodName;
		this.weight = weight;
		this.parameter = parameter;
		this.isCpmplete = false;
	}
	/**获取方法参数**/
	public List<MethodParameter> Parameter() {
		return parameter;
	}

	public Boolean Cpmplete() {
		return isCpmplete;
	}
	public void setIsCpmplete(Boolean isCpmplete) {
		this.isCpmplete = isCpmplete;
	}
	public int getWeight()
	{
		return this.weight;
	}
	@Override
	public boolean sameIdentityAs(Method other) {
		// TODO Auto-generated method stub
		return false;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getNeedAuthority() {
		return needAuthority;
	}
	public void setNeedAuthority(String needAuthority) {
		this.needAuthority = needAuthority;
	}
	public List<MethodParameter> getParameter() {
		return parameter;
	}
	public void setParameter(List<MethodParameter> parameter) {
		this.parameter = parameter;
	}
	public Boolean getIsCpmplete() {
		return isCpmplete;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
