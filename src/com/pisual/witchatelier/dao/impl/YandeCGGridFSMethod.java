package com.pisual.witchatelier.dao.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.pisual.witchatelier.dao.YandeCGGridFSMethodInterface;
import com.pisual.witchatelier.model.YandeCGGridFS;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

/**
 * YandeCG GridFS 大型文件存储库
 * 
 * **/
public class YandeCGGridFSMethod implements YandeCGGridFSMethodInterface {

	private static DB db;
	private static Mongo mg;
	private static GridFS yandeCGFS;
	private static DBCollection collection;
	/**每个主机的连接数**/
	private static int CONNECTIONSPERHOST = 20;
	/**线程队列数，它以上面connectionsPerHost值相乘的结果就是线程队列最大值。
	 * 如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误**/
	private static int THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER = 20;
	/**日志初始化**/
	static Logger logger = Logger.getLogger(YandeCGGridFSMethod.class.getName());
	/**数据库初始化**/
	static { 
		try {
		mg = new Mongo(PropertiesUtil.getValue("MongodbIP"),Integer.parseInt(PropertiesUtil.getValue("MongodbPort")));
		MongoOptions opt = mg.getMongoOptions();
		opt.connectionsPerHost = CONNECTIONSPERHOST ;
		opt.threadsAllowedToBlockForConnectionMultiplier = THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER;
		db = mg.getDB(PropertiesUtil.getValue("MongodbDataBase"));
		collection = db.getCollection(PropertiesUtil.getValue("MongodbYandeCGGridFSCollection"));
		yandeCGFS = new GridFS(db);
	} catch (Exception e) {
		logger.error(e.getMessage());
	}
		} 
	/**
	 * 根据临时文件地址或累计库存储指定文件 如果ID存在的话会抛出异常
	 * 
	 * @author felixerio
	 * **/
	@Override
	public void saveYandeCGFileToMethod(YandeCGGridFS yandeCGGridFS) {
		DBObject query = new BasicDBObject("_id", yandeCGGridFS.getId());
		GridFSDBFile gridFSDBFile = yandeCGFS.findOne(query);
		if (gridFSDBFile != null) {
			logger.info("存储异常 存储层报告 ID:"+yandeCGGridFS.getId()+" 数据已经存在 此次存储将跳过");
			return;
		}
		GridFSInputFile gridFSInputFile;
		try {
			FileInputStream fileInputStream = new FileInputStream(yandeCGGridFS.getFileMenuList());
			gridFSInputFile = yandeCGFS.createFile(fileInputStream);
			gridFSInputFile.setFilename(yandeCGGridFS.getId() + "");
			gridFSInputFile.setId(yandeCGGridFS.getId());
			gridFSInputFile.save();
			try {
				fileInputStream.close();
			} catch (IOException e) {
				logger.equals(e.getMessage());
			}
			logger.info("完成存储 存储层报告 ID:"+yandeCGGridFS.getId()+" 数据完成存储");
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 根据id获取指定文件
	 * 
	 * @author felixerio
	 * **/
	@Override
	public void getYandeCGFileFromMethod(YandeCGGridFS yandeCGGridFS) {
		DBObject query = new BasicDBObject("_id", yandeCGGridFS.getId());
		GridFSDBFile gridFSDBFile = yandeCGFS.findOne(query);
		try {
			gridFSDBFile.writeTo(new FileOutputStream(PropertiesUtil.getValue("MongodbYandeCGGridFSDownloadTempPath")+ yandeCGGridFS.getId() + ".jpg"));
			logger.info("完成指定ID: "+yandeCGGridFS.getId()+" 的文件获取 存储层报告 ID:"+yandeCGGridFS.getId()+" 数据完成下载");
		} catch (IOException e) {
			logger.error(e.getMessage());
		} 
	}

	/**
	 * 查询所有数据
	 * 
	 * @author felixerio
	 * **/
	@Override
	public void viewallData() {
		DBCursor cur = yandeCGFS.getFileList();
		while (cur.hasNext()) {
			System.out.println(cur.next());
		}
		cur.close();
	}

	/**
	 * 删掉数据库
	 * 
	 * @author felixerio
	 * **/
	@Override
	public void dropDataBase() {
		db.dropDatabase();
	}

	/**
	 * 下载数据库所有文件
	 * 
	 * @author felixerio
	 * **/
	@Override
	public List<YandeCGGridFS> getAllMenuFromMongodb() {
		List<YandeCGGridFS> yandeCGGridFSList = new ArrayList<YandeCGGridFS>();
		DBCursor cur = yandeCGFS.getFileList();
		while (cur.hasNext()) {
			YandeCGGridFS temp = new YandeCGGridFS();
			temp.setId(cur.next().get("_id").toString());
			yandeCGGridFSList.add(temp);
			logger.info("完成指定ID: "+temp.getId()+" 的文件获取 存储层报告 ID:"+temp.getId()+" 数据完成加载");
			
		}
		return yandeCGGridFSList;
	}
	/**
	 * 测试指定ID是否存在
	 * 
	 * @author felixerio
	 * **/
	@Override
	public Boolean testIDExist(String id) {
		System.out.println("查询id "+id);
		DBObject query = new BasicDBObject("_id",id);
		GridFSDBFile gridFSDBFile = yandeCGFS.findOne(query);
		if (gridFSDBFile != null) {
			System.out.println("目标存在");
			return true;
		}
		else{
			System.out.println("目标不存在");
			return false;
		}
	}
}
