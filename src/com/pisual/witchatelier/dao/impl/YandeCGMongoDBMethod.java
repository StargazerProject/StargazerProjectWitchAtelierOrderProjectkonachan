package com.pisual.witchatelier.dao.impl;

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
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFSDBFile;
import com.pisual.witchatelier.dao.YandeCGMongoDBMethodInterFace;
import com.pisual.witchatelier.model.YandeCG;
import com.pisual.witchatelier.model.YandeCGGridFS;
import com.pisual.www.cells.yande.util.CellsUtil;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

public class YandeCGMongoDBMethod implements YandeCGMongoDBMethodInterFace {

	/** Mongo 初始化 **/
	private static Mongo mg;
	private static DB db;
	private static DBCollection collection;
	/** 每个主机的连接数 **/
	private static int CONNECTIONSPERHOST = 20;
	/**
	 * 线程队列数，它以上面connectionsPerHost值相乘的结果就是线程队列最大值。 如果连接线程排满了队列就会抛出“Out of
	 * semaphores to get db”错误
	 **/
	private static int THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER = 20;
	/** 日志初始化 **/
	static Logger logger = Logger.getLogger(YandeCGMongoDBMethod.class
			.getName());
	/** 数据库初始化 **/
	static {
		try {
			mg = new Mongo(PropertiesUtil.getValue("MongodbIP"),Integer.parseInt(PropertiesUtil.getValue("MongodbPort")));
			MongoOptions opt = mg.getMongoOptions();
			opt.connectionsPerHost = CONNECTIONSPERHOST;
			opt.threadsAllowedToBlockForConnectionMultiplier = THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER;
			db = mg.getDB(PropertiesUtil.getValue("MongodbDataBase"));
			collection = db.getCollection(PropertiesUtil.getValue("MongodbDataBaseCollection"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 存储单个对象YandeCG
	 * 
	 * @author felixerio
	 * **/
	@Override
	public void saveYandeCG(YandeCG yandeCG) {
		System.out.println("查询ID："+yandeCG.getId());
		DBObject dBObject = collection.findOne(new BasicDBObject("id",yandeCG.getId()));
		if(null==dBObject){
			DBObject mongoYandeCG = new BasicDBObject();
			mongoYandeCG.put("_id", yandeCG.getMd5());
			mongoYandeCG.put("id", yandeCG.getId());
			mongoYandeCG.put("md5", yandeCG.getMd5());
			mongoYandeCG.put("downComplete",Boolean.FALSE);
			mongoYandeCG.put("tags", yandeCG.getTags());
			mongoYandeCG.put("source", yandeCG.getSource());
			mongoYandeCG.put("file_url", yandeCG.getFile_url());
			mongoYandeCG.put("file_size", yandeCG.getFile_size());
			mongoYandeCG.put("local_url", yandeCG.getLocal_url());
			collection.save(mongoYandeCG);
		}
		else{
			System.out.println("数据已经存在 将略过此次存储 ID："+yandeCG.getId());
		}
	}

	/**
	 * 获取指定分页数据记录用于下载
	 * 
	 * @author felixsion
	 * **/
	@Override
	public List<YandeCG> getTenNumFileToDownLoad(int startPags,int endPages) {
		List<YandeCG> yandeCGList = new ArrayList<YandeCG>();
		DBCursor cur = collection.find(new BasicDBObject("downComplete",Boolean.FALSE));
		int num = 0;
		while (cur.hasNext()&&num>=startPags&&num<endPages) {
			    DBObject dBObject = cur.next();
			    System.out.println(dBObject);
				++num;
				YandeCG yanedCG = new YandeCG();
				yanedCG.setId(Integer.parseInt(dBObject.get("id").toString()));
				yanedCG.setTags(dBObject.get("tags").toString());
				yanedCG.setMd5(dBObject.get("md5").toString());
				yanedCG.setDownComplete(Boolean.parseBoolean(dBObject.get("downComplete").toString()));
				yanedCG.setSource(dBObject.get("source").toString());
				yanedCG.setFile_url(dBObject.get("file_url").toString());
				yanedCG.setFile_size(dBObject.get("file_size").toString());
				yandeCGList.add(yanedCG);
				logger.info("已经获取到第 " + num + " 个未下载任务 图像ID："+dBObject.get("id").toString());
		}
		/**判断是否已经加载数据**/
		if(num==0)
		{
			logger.warn("未获取到未下载数据列表 此次获取未下载数据失败 检查数据库是否为全部下载状态");
		}
		cur.close();
		return yandeCGList;
	}

	/**
	 * 存储YandeCG List文件
	 * 
	 * @author felixerio
	 * **/
	@Override
	public void saveListYandeCG(List<YandeCG> yandeCGList) {
		int num = yandeCGList.size();
		for (int i = 0; i < num; i++) {
			logger.info("批量保存任务 正在处理第 " + i + " 个任务");
			YandeCG yandeCG = new YandeCG();
			yandeCG = yandeCGList.get(i);
			DBObject mongoYandeCG = new BasicDBObject();
			mongoYandeCG.put("_id", yandeCG.getMd5());
			mongoYandeCG.put("id", yandeCG.getId());
			mongoYandeCG.put("md5", yandeCG.getMd5());
			mongoYandeCG.put("downComplete",yandeCG.isDownComplete());
			mongoYandeCG.put("tags", yandeCG.getTags());
			mongoYandeCG.put("source", yandeCG.getSource());
			mongoYandeCG.put("file_url", yandeCG.getFile_url());
			mongoYandeCG.put("file_size", yandeCG.getFile_size());
			mongoYandeCG.put("local_url", yandeCG.getLocal_url());
			logger.info("批量保存任务 完成处理第 " + i + " 个任务");
			logger.info(collection.save(mongoYandeCG).getN());
		}
		mg.close();
	}

	/**
	 * 删除数据库YandeCG表文件
	 * 
	 * @author felixerio
	 * **/
	@Override
	public void dropAllYandeCG() {
		collection.drop();
		mg.close();
	}

	/**
	 * 查询YandeCG表中所有文件
	 * 
	 * @author felixerio
	 * **/
	@Override
	public List<YandeCG> listAllYandeCG() {
		try {
			List<YandeCG> yandeCGList = new ArrayList<YandeCG>();
			DBCursor cur = collection.find();
			int num = 1;
			while (cur.hasNext()) {
				YandeCG yanedCG = new YandeCG();
				DBObject dBObject = cur.next();
				yanedCG.setId(Integer.parseInt(dBObject.get("id").toString()));
				yanedCG.setTags(dBObject.get("tags").toString());
				yanedCG.setMd5(dBObject.get("md5").toString());
				yanedCG.setDownComplete(Boolean.parseBoolean(dBObject.get("downComplete").toString()));
				yanedCG.setSource(dBObject.get("source").toString());
				yanedCG.setFile_url(dBObject.get("file_url").toString());
				yanedCG.setFile_size(dBObject.get("file_size").toString());
				yandeCGList.add(yanedCG);
				num++;
			}
			logger.info("批量获取全部数据数据任务 完成处理 " + num + " 个任务");
			cur.close();
			return yandeCGList;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 查询特定条件文件函数 查询根据 Tags（标签） Tags支持复合条件查询
	 * 
	 * @author felixerio
	 * **/
	@Override
	public List<YandeCG> querySpecifiedConditionsForTags(List<String> tags) {
		String query[] = tags.toArray(new String[tags.size()]);
		DBCursor cur = collection.find(new BasicDBObject("tags", query));
		logger.info("批量获取指定Tags数据数据任务 查询到 " + cur.size() + " 条数据");
		int num = 1;
		List<YandeCG> yandeCGList = new ArrayList<YandeCG>();
		while (cur.hasNext()) {
			YandeCG yanedCG = new YandeCG();
			DBObject dBObject = cur.next();
			yanedCG.setId(Integer.parseInt(dBObject.get("id").toString()));
			yanedCG.setTags(dBObject.get("tags").toString());
			yanedCG.setMd5(dBObject.get("md5").toString());
			yanedCG.setDownComplete(Boolean.parseBoolean(dBObject.get("downComplete").toString()));
			yanedCG.setSource(dBObject.get("source").toString());
			yanedCG.setFile_url(dBObject.get("file_url").toString());
			yanedCG.setFile_size(dBObject.get("file_size").toString());
			yanedCG.setLocal_url(dBObject.get("local_url").toString());
			yandeCGList.add(yanedCG);
			logger.info("批量获取指定Tags数据数据任务 完成处理第 " + num + " 个任务");
			num++;
		}
		cur.close();
		return yandeCGList;
	}

	/**
	 * 创建Tags全文索引
	 * 
	 * @author felixerio
	 * **/
	@Override
	public void ensureIndexForTags() {
		DBObject mongoYandeCG = new BasicDBObject("id", 1);
		collection.ensureIndex(mongoYandeCG, "ensureIndexTags");
	}

	/**
	 * 获取数据库总条数
	 * 
	 * @author felixsion
	 * **/
	@Override
	public int getDataBaseCollectionToyalNum() {
		DBCursor cur = collection.find();
		int num = cur.size();
		cur.close();
		return num;
	}

	/** 数据库连通测试 **/
	@Override
	public void DateBaseConnectionTest() {
	}

	@Override
	public void delateYandeCG(YandeCG yandeCG) {
	}
	/**更新YandeCG**/
	@Override
	public void changeYandeCG(YandeCG yandeCG) {
		DBObject mongoYandeCG = new BasicDBObject();
		mongoYandeCG.put("_id", yandeCG.getMd5());
		DBObject mongoYandeCGNew = new BasicDBObject();
		mongoYandeCGNew.put("_id", yandeCG.getMd5());
		mongoYandeCGNew.put("id", yandeCG.getId());
		mongoYandeCGNew.put("md5", yandeCG.getMd5());
		mongoYandeCGNew.put("downComplete", yandeCG.isDownComplete());
		mongoYandeCGNew.put("tags", yandeCG.getTags());
		mongoYandeCGNew.put("source", yandeCG.getSource());
		mongoYandeCGNew.put("file_url", yandeCG.getFile_url());
		mongoYandeCGNew.put("file_size", yandeCG.getFile_size());
		WriteResult num = collection.update(mongoYandeCG, mongoYandeCGNew);
		logger.info("完成更新YandeCG 受影响的行数: "+num.getN());
	}
	
	/**更新数据库下载状态 一般用于数据库初始化**/
	@Override
	public void updateDownloadComplete(YandeCG yandeCG) {
		DBObject mongoYandeCG = new BasicDBObject();
		mongoYandeCG.put("_id", yandeCG.getMd5());
		DBObject mongoYandeCGNew = new BasicDBObject();
		mongoYandeCGNew.put("_id", yandeCG.getMd5());
		mongoYandeCGNew.put("id", yandeCG.getId());
		mongoYandeCGNew.put("md5", yandeCG.getMd5());
		mongoYandeCGNew.put("downComplete",yandeCG.isDownComplete());
		mongoYandeCGNew.put("tags", yandeCG.getTags());
		mongoYandeCGNew.put("source", yandeCG.getSource());
		mongoYandeCGNew.put("file_url", yandeCG.getFile_url());
		mongoYandeCGNew.put("file_size", yandeCG.getFile_size());
		WriteResult num = collection.update(mongoYandeCG, mongoYandeCGNew);
		logger.info("完成更新YandeCG下载状态 受影响的行数: "+num.getN());
	}

	@Override
	public List<YandeCG> listYandeCGPages(int startPages, int getNum) {
		return null;
	}

	@Override
	public List<YandeCG> selectTagesYandeCG(String tages) {
		return null;
	}
	/**测试主索引是否存在该文件及获得MD5**/
	public YandeCG testGetIndexByID(String _id)
	{
		YandeCG yanedCG = new YandeCG();
		yanedCG.setMd5("InNULL");
		try {
			logger.info("查询ID: "+_id+" 的MD5值");
			System.out.println("查询ID："+_id);
			DBObject dBObject = collection.findOne(new BasicDBObject("id", Integer.parseInt(_id)));
			if(null==dBObject)
			{
				logger.warn("撞库失败 没有查询到指定对象 ID: "+Integer.parseInt(_id));
				return yanedCG;
			}
			else
			{
				yanedCG.setId(Integer.parseInt(dBObject.get("id").toString()));
				yanedCG.setTags(dBObject.get("tags").toString());
				yanedCG.setMd5(dBObject.get("md5").toString());
				yanedCG.setDownComplete(Boolean.parseBoolean(dBObject.get("downComplete").toString()));
				yanedCG.setSource(dBObject.get("source").toString());
				yanedCG.setFile_url(dBObject.get("file_url").toString());
				yanedCG.setFile_size(dBObject.get("file_size").toString());
				yanedCG.setLocal_url("Has Save in Server");
				logger.info("查询到指定对象 ID: "+Integer.parseInt(_id));
				return yanedCG;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return yanedCG;
	}
	
	/**获取没有下载的文件总素**/
	public int noDownloadCompleteFileNum()
	{
		DBCursor cur = collection.find(new BasicDBObject("downComplete",Boolean.FALSE));
		return cur.size();
	}

	public static void main(String[] args) {
		YandeCGMongoDBMethod yandeCGMongoDBMethod = new YandeCGMongoDBMethod();
		System.out.println(yandeCGMongoDBMethod.getDataBaseCollectionToyalNum());
		System.out.println(yandeCGMongoDBMethod.noDownloadCompleteFileNum());
		System.out.println(CellsUtil.filePercent(yandeCGMongoDBMethod.getDataBaseCollectionToyalNum(), yandeCGMongoDBMethod.noDownloadCompleteFileNum()));
}
}