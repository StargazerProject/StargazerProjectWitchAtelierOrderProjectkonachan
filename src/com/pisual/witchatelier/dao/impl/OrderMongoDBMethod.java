package com.pisual.witchatelier.dao.impl;

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
import com.pisual.witchatelier.dao.OrderBackUPMongoDBMethodInterFace;
import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

public class OrderMongoDBMethod implements OrderBackUPMongoDBMethodInterFace {

    /**Mongo 初始化**/
	private static Mongo mg;
	private static DB db;
	private static DBCollection collection;
	/**每个主机的连接数**/
	private static int CONNECTIONSPERHOST = 20;
	/**线程队列数，它以上面connectionsPerHost值相乘的结果就是线程队列最大值。
	 * 如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误**/
	private static int THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER = 20;
	/**日志初始化**/
	static Logger logger = Logger.getLogger(OrderMongoDBMethod.class.getName());
	/**数据库初始化**/
	static
	{
		try {
			mg = new Mongo(PropertiesUtil.getValue("OrderCacheMongodbIP"),Integer.parseInt(PropertiesUtil.getValue("OrderCacheMongodbPort")));
			MongoOptions opt = mg.getMongoOptions();
			opt.connectionsPerHost = CONNECTIONSPERHOST ;
			opt.threadsAllowedToBlockForConnectionMultiplier = THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER;
			db = mg.getDB(PropertiesUtil.getValue("OrderCacheMongodbDataBase"));
			collection = db.getCollection(PropertiesUtil.getValue("OrderCacheMongodbDataBaseCollection"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	/**保存单个原始Order对象**/
	@Override
	public void saveOrder(Order order) {
		DBObject mongoOrder = new BasicDBObject();
		mongoOrder.put("_id", order.OrderID().getID());
		mongoOrder.put("oeder", order);
		int num = collection.save(mongoOrder).getN();
		logger.info("完成存储Order 受影响的行数: "+num);
	}
	@Override
	public void delateOrder(Order order) {
		DBObject mongoOrder = new BasicDBObject();
		mongoOrder.put("_id", order.OrderID().getID());
		int num = collection.remove(mongoOrder).getN();
		logger.info("完成删除Order 受影响的行数: "+num);
	}
	@Override
	public void changeOrder(Order order) {
		DBObject mongoOrder = new BasicDBObject();
		mongoOrder.put("_id", order.OrderID().getID());
		DBObject mongoOrderNew = new BasicDBObject();
		mongoOrderNew.put("_id", order.OrderID().getID());
		mongoOrderNew.put("oeder", order);
		WriteResult num = collection.update(mongoOrder, mongoOrderNew);
		logger.info("完成更新Order 受影响的行数: "+num.getN());
	}
	/**删除数据库Order表文件**/
	@Override
	public void dropAllOrder() {
		collection.drop();
		mg.close();
	}
	@Override
	public List<Order> querySpecifiedConditionsForTags(List<String> tags) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void ensureIndexForTags() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void DateBaseConnectionTest() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getDataBaseCollectionToyalNum() {
		DBCursor cur = collection.find();
		int num = cur.size();
		cur.close();
		return num;
	}
}