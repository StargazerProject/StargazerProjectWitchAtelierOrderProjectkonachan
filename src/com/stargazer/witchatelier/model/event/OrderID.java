package com.stargazer.witchatelier.model.event;

import java.io.Serializable;

import com.mongodb.ReflectionDBObject;
import com.stargazer.witchatelier.model.shared.CreatID;
import com.stargazer.witchatelier.model.shared.ValueObject;
import com.stargazer.witchatelier.util.system.IDUtil;

public class OrderID extends ReflectionDBObject implements ValueObject<OrderID>,CreatID,Serializable{

	private static final long serialVersionUID = 5101177440980466035L;
	private String ID;
	public OrderID() {
		this.ID=this.ID();
	}
	@Override
	public String ID() {
		return IDUtil.ID();
	}
	@Override
	public boolean sameValueAs(OrderID other) {
		// TODO Auto-generated method stub
		return false;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}