package com.pisual.witchatelier.dao;

import java.util.List;

import com.pisual.witchatelier.model.YandeCG;
import com.stargazer.witchatelier.model.event.Order;
/**
 * BigCG Mongodb 数据库接口
 * **/
public interface OrderMongoDBMethodInterFace {
	/**保存单个原始Order对象**/
	public void saveOrder(Order order);
	/**删除单个Order对象**/
	public void delateOrder(Order order);
	/**修改单个Order对象**/
	public void changeOrder(Order order);
	/**删除数据库Order表文件**/
	public void dropAllOrder();
	/**查询特定条件文件函数 查询根据 Tags（标签）Tags支持复合条件查询**/
	public List<Order> querySpecifiedConditionsForTags(List<String> tags);
	/**创建Tags全文索引**/
	public void ensureIndexForTags();
	/**数据库测试**/
	public void DateBaseConnectionTest();
	/**获取数据库总条数**/
	public int getDataBaseCollectionToyalNum();
}