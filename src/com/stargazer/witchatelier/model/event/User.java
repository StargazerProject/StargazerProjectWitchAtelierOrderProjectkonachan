package com.stargazer.witchatelier.model.event;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

import com.mongodb.ReflectionDBObject;

public class User extends ReflectionDBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ID;
	private String name;
	private String password;
	private Role role;

	public User(String name,String password) {
		Validate.notNull(name, "Some name is required");
		Validate.notNull(password, "Some password is required");
		this.name = name;
		this.password = password;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}