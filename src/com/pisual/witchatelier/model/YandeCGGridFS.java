package com.pisual.witchatelier.model;

/**
 * YandeCG GridFS Model
 * @author felixerio
 * **/
public class YandeCGGridFS {
	/**同步id 和yandeCG的Model的ID同步**/
	private String id;
	/**文件内容列表**/
	private String fileMenuList;
	/**_id 用于索引源表数据的辅助ID**/
	private String _id;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileMenuList() {
		return fileMenuList;
	}
	public void setFileMenuList(String fileMenuList) {
		this.fileMenuList = fileMenuList;
	}
}