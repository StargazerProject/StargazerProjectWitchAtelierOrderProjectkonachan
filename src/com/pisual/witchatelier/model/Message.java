package com.pisual.witchatelier.model;

import java.io.Serializable;

import com.mongodb.ReflectionDBObject;

/**
 * 消息通报系统Model
 * @author felixsion
 * 
 * 
 * **/
public class Message extends ReflectionDBObject implements Serializable {
	private String id;
	private String title;
	private String content;
	private String md5;
	/**初始化并且校验**/
	public void messageInit(String id,String title,String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public String getMd5() {
		return md5;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
}