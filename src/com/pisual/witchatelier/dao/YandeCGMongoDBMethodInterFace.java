package com.pisual.witchatelier.dao;

import java.util.List;

import com.pisual.witchatelier.model.YandeCG;
/**
 * BigCG Mongodb 数据库接口
 * **/
public interface YandeCGMongoDBMethodInterFace {
	/**保存单个BigCG对象**/
	public void saveYandeCG(YandeCG yandeCG);
	/**批量保存BigCG列表**/
	public void saveListYandeCG(List<YandeCG> yandeCGList);
	/**删除单个BigCG对象**/
	public void delateYandeCG(YandeCG yandeCG);
	/**修改单个BigCG对象**/
	public void changeYandeCG(YandeCG yandeCG);
	/**按分页形式查询YandeCG表中所有文件**/
	public List<YandeCG> listYandeCGPages(int startPages,int getNum);
	/**查询特定条件文件函数 查询根据 Tags（标签）Tags支持复合条件查询**/
	public List<YandeCG> selectTagesYandeCG(String tages);
	/**查询YandeCG表中所有文件**/
	public List<YandeCG> listAllYandeCG();
	/**删除数据库YandeCG表文件**/
	public void dropAllYandeCG();
	/**查询特定条件文件函数 查询根据 Tags（标签）Tags支持复合条件查询**/
	public List<YandeCG> querySpecifiedConditionsForTags(List<String> tags);
	/**创建Tags全文索引**/
	public void ensureIndexForTags();
	/**数据库测试**/
	public void DateBaseConnectionTest();
	/**获取数据库总条数**/
	public int getDataBaseCollectionToyalNum();
	/** 下载10个未下载任务 指定开始页面 及结束页面 **/
	public List<YandeCG> getTenNumFileToDownLoad(int startPags,int endPages);
	/**测试主索引是否存在该文件及获得MD5**/
	public YandeCG testGetIndexByID(String _id);
	/**更新数据库下载状态 一般用于数据库初始化**/
	public void updateDownloadComplete(YandeCG yandeCG);
	/**获取没有下载的文件总素**/
	public int noDownloadCompleteFileNum();
}