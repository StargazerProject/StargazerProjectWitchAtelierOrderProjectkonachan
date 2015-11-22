package com.pisual.witchatelier.dao;

import java.util.List;

import com.pisual.witchatelier.model.YandeCGGridFS;


/**
 * YandeCG的GridFS存储
 * 存储根据为已经下载好的临时文件，这样就提供了两种存储方式 一种是实时更新 一种是根据累计数据更新存储
 * @author felixerio
 * 
 * **/
public interface YandeCGGridFSMethodInterface {
	public void saveYandeCGFileToMethod(YandeCGGridFS yandeCGGridFS);
	public void getYandeCGFileFromMethod(YandeCGGridFS yandeCGGridFS);
	public List<YandeCGGridFS> getAllMenuFromMongodb();
	public void dropDataBase();
	public void viewallData();
	public Boolean testIDExist(String id);
}
