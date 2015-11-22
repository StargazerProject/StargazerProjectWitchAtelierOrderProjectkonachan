package com.stargazer.witchatelier.model.event;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import com.mongodb.ReflectionDBObject;
import com.stargazer.witchatelier.model.shared.ValueObject;


public class OrderMessage extends ReflectionDBObject implements ValueObject<OrderMessage>,Serializable{

	private static final long serialVersionUID = -1753128882452133987L;
	private String orderLevel;/**指令级别 1:系统级指令 SystemLevelCommand 2:事务级指令 TransactionLevelCommand**/
	private String orderGetIP;/**指令源IP**/
	private String orderSentIP;/**指令目标IP**/
	private boolean idPostOrder;	/**指令方向**/
	private Integer orderGetPort;/**指令源端口**/
	private Integer orderSentPort;/**指令目标端口**/
	private String targetCellsName;/**目标Cells的名称**/
	private OrderMethod orderMethod;/**指令事件集**/
	private boolean isOrdersComplete;/**指令是否完成标识 初始化是将为False**/
	
	public OrderMessage(String orderLevel,String orderGetIP,String orderSentIP,OrderMethod orderMethod,Integer orderGetPort,Integer orderSentPort,String targetCellsName) {
		Validate.notNull(orderLevel, "Some orderLevel is required");
		Validate.notNull(orderGetIP, "Some orderGetIP is required");
		Validate.notNull(orderMethod, "Some orderMethod is required");
		Validate.notNull(orderSentIP, "Some orderSentIP is required");
		Validate.notNull(orderGetPort, "Some orderGetPort is required");
		Validate.notNull(orderSentPort, "Some orderSentPort is required");
		Validate.notNull(targetCellsName, "Some targetCellsName is required");
		this.idPostOrder = true;
		this.idPostOrder = true;
		this.orderLevel = orderLevel;
		this.orderGetIP = orderGetIP;
		this.isOrdersComplete = false;
		this.orderSentIP = orderSentIP;
		this.orderMethod = orderMethod;
		this.orderGetPort = orderGetPort;
		this.orderSentPort = orderSentPort;
		this.targetCellsName = targetCellsName;
	}
	/**获取*指令事件集**/
	public OrderMethod OrderMethod() {
		return orderMethod;
	}
	/**指令已经成功发送**/
	public void orderHasReceiveSuccess()
	{
		this.isOrdersComplete = true;
		this.idPostOrder = false;
	}
	@Override
	public boolean sameValueAs(OrderMessage other) {
		// TODO Auto-generated method stub
		return false;
	}
	public String getOrderLevel() {
		return orderLevel;
	}
	public void setOrderLevel(String orderLevel) {
		this.orderLevel = orderLevel;
	}
	public String getOrderGetIP() {
		return orderGetIP;
	}
	public void setOrderGetIP(String orderGetIP) {
		this.orderGetIP = orderGetIP;
	}
	public String getOrderSentIP() {
		return orderSentIP;
	}
	public void setOrderSentIP(String orderSentIP) {
		this.orderSentIP = orderSentIP;
	}
	public boolean isIdPostOrder() {
		return idPostOrder;
	}
	public void setIdPostOrder(boolean idPostOrder) {
		this.idPostOrder = idPostOrder;
	}
	public Integer getOrderGetPort() {
		return orderGetPort;
	}
	public void setOrderGetPort(Integer orderGetPort) {
		this.orderGetPort = orderGetPort;
	}
	public Integer getOrderSentPort() {
		return orderSentPort;
	}
	public void setOrderSentPort(Integer orderSentPort) {
		this.orderSentPort = orderSentPort;
	}
	public String getTargetCellsName() {
		return targetCellsName;
	}
	public void setTargetCellsName(String targetCellsName) {
		this.targetCellsName = targetCellsName;
	}
	public OrderMethod getOrderMethod() {
		return orderMethod;
	}
	public void setOrderMethod(OrderMethod orderMethod) {
		this.orderMethod = orderMethod;
	}
	public boolean isOrdersComplete() {
		return isOrdersComplete;
	}
	public void setOrdersComplete(boolean isOrdersComplete) {
		this.isOrdersComplete = isOrdersComplete;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}