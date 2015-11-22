package com.stargazer.witchatelier.model.Method.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.pisual.witchatelier.dao.impl.YandeCGGridFSMethod;
import com.pisual.witchatelier.dao.impl.YandeCGMongoDBMethod;
import com.pisual.witchatelier.model.YandeCG;
import com.pisual.witchatelier.model.YandeCGGridFS;
import com.pisual.www.cells.yande.util.CellsHtmlUtil;
import com.pisual.www.cells.yande.util.CellsUtil;
import com.pisual.www.cells.yande.util.FileMD5;
import com.pisual.www.cells.yande.util.HtmlDataHandling;
import com.pisual.www.cells.yande.util.PisualIOUtil;
import com.stargazer.witchatelier.messageservice.SentMessageService;
import com.stargazer.witchatelier.model.event.Method;
import com.stargazer.witchatelier.util.system.DownLoadFileThread;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

public class TransactionLevelCommandMrthod {
	/** 日志初始化 **/
	static Logger logger = Logger.getLogger(TransactionLevelCommandMrthod.class.getName());

	/** 获取并且存储数据 将根据分页数据构建 **/
	public boolean receiveAndSaveMessage(Method method) {
		logger.info("Start receive And Save Job page form "+ method.getParameter().get(0).getParameter() + " Pages to "+ method.getParameter().get(1).getParameter());
		List<String> menu = new ArrayList<String>();
		CellsHtmlUtil cellsHtmlUtil = new CellsHtmlUtil();
		YandeCGMongoDBMethod yandeCGMongoDBMethod = new YandeCGMongoDBMethod();
		HtmlDataHandling htmlDataHandling = new HtmlDataHandling();
		int totalUpdateNum = 0;/**总更新条数**/
		menu.add("id");
		menu.add("md5");
		menu.add("tags");
		menu.add("file_url");
		menu.add("file_size");
		menu.add("source");
		int startPages = Integer.parseInt(method.getParameter().get(0).getParameter());
		int endPages = Integer.parseInt(method.getParameter().get(1).getParameter());
		for (int i = endPages; i >= startPages; i--) {
			List<YandeCG> yandeCGList = new ArrayList<YandeCG>();
			yandeCGList = htmlDataHandling.XmlHandling(menu,cellsHtmlUtil.getHtmlXMLOrJson(PropertiesUtil.getValue("getYandeCGPages") + i));
			logger.info("已经获得第 " + i + "分页");
			int num = yandeCGList.size();
			for (int j = num - 1; j > 0; j--) {
				yandeCGList.get(j).setDownComplete(false);
				yandeCGMongoDBMethod.saveYandeCG(yandeCGList.get(j));
				++totalUpdateNum;
				logger.info("已经保存第 " + j + "个数据");
			}
		}
		SentMessageService sentMessageService = new SentMessageService("PISUAL CELLS SYSTEM BigCG CELLS "+System.getProperty("webStatus")+"Model 远程数据更新完成","本次远程数据更新已完成 已经更新 "+totalUpdateNum+" 个数据");
		return true;
	}

	/** 显示数据库所有文件 **/
	public void listAllMongoDBFile(Method method) {
		YandeCGMongoDBMethod yandeCGMongoDBMethod = new YandeCGMongoDBMethod();
		yandeCGMongoDBMethod.listAllYandeCG();
	}

	/** 删除数据库指定表文件 **/
	public void dropAllMongoDBFile(Method method) {
		YandeCGMongoDBMethod yandeCGMongoDBMethod = new YandeCGMongoDBMethod();
		yandeCGMongoDBMethod.dropAllYandeCG();
	}

	/** 上传指定文件夹下的所有文件 **/
	public void uploadPathAllFileToMongoDB(Method method) {
		try {
			PisualIOUtil pisualIOUtil = new PisualIOUtil();
			YandeCGGridFSMethod yandeCGGridFSMethod = new YandeCGGridFSMethod();
			YandeCGMongoDBMethod yandeCGMongoDBMethod = new YandeCGMongoDBMethod();
			List<YandeCGGridFS> YandeCGGridFSList = new ArrayList<YandeCGGridFS>();
			pisualIOUtil.getPathFileListSmartID(PropertiesUtil.getValue("MongodbYandeCGGridFSTempPath"),YandeCGGridFSList);
			int num = YandeCGGridFSList.size();
			for (int i = 0; i < num; i++) {
				String md5 = FileMD5.getFileMD5String(YandeCGGridFSList.get(i).getFileMenuList());
				if(md5.endsWith(yandeCGMongoDBMethod.testGetIndexByID(YandeCGGridFSList.get(i).get_id()).getMd5())==true)
				{
					logger.info("校验成功 核心存储数据库入库级MD5校验成功 ID: "+YandeCGGridFSList.get(i).getId());
					yandeCGGridFSMethod.saveYandeCGFileToMethod(YandeCGGridFSList.get(i));
				}
				else
				{
					logger.warn("校验失败 核心存储数据库入库级MD5校验失败 原文件存在在异常 将跳过此次存储入库 ID: "+YandeCGGridFSList.get(i).get_id());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/** 上传指定文件夹下的所有文件 并更新下载状态到核心数据库 一般用于数据库初始化 **/
	public void UpdateDownloadStatus(Method method) {
		try {
			PisualIOUtil pisualIOUtil = new PisualIOUtil();
			YandeCGGridFSMethod yandeCGGridFSMethod = new YandeCGGridFSMethod();
			YandeCGMongoDBMethod yandeCGMongoDBMethod = new YandeCGMongoDBMethod();
			List<YandeCGGridFS> YandeCGGridFSList = new ArrayList<YandeCGGridFS>();
			pisualIOUtil.getPathFileListSmartID(PropertiesUtil.getValue("MongodbYandeCGGridFSTempPath"),YandeCGGridFSList);
			int num = YandeCGGridFSList.size();
			for (int i = 0; i < num; i++) {
				String md5 = FileMD5.getFileMD5String(YandeCGGridFSList.get(i).getFileMenuList());
				 YandeCG yandeCG = yandeCGMongoDBMethod.testGetIndexByID(YandeCGGridFSList.get(i).get_id());
				if(md5.endsWith(yandeCG.getMd5())==true)
				{
					logger.info("校验成功 核心存储数据库入库级MD5校验成功 ID: "+YandeCGGridFSList.get(i).getId());
					yandeCGGridFSMethod.saveYandeCGFileToMethod(YandeCGGridFSList.get(i));
					logger.info("文件上传成功 ID: "+YandeCGGridFSList.get(i).getId());
					yandeCG.setDownComplete(true);
					yandeCGMongoDBMethod.updateDownloadComplete(yandeCG);
					logger.info("更新下载状态成功 ID: "+YandeCGGridFSList.get(i).getId());
					File storeFile = new File(YandeCGGridFSList.get(i).getFileMenuList());
					if(storeFile.delete()==Boolean.FALSE){
						logger.warn("删除失败 文件名: "+YandeCGGridFSList.get(i).getFileMenuList());
					}
					else{
						logger.info("上传存储数据库成功 已经删除原文件 文件名: " + YandeCGGridFSList.get(i).getFileMenuList());
					}
				}
				else
				{
					logger.warn("校验失败 核心存储数据库入库级MD5校验失败 原文件存在在异常 将跳过此次存储入库 ID: "+YandeCGGridFSList.get(i).get_id());
					yandeCG.setDownComplete(false);
					yandeCGMongoDBMethod.updateDownloadComplete(yandeCG);
					logger.info("更新下载状态为失败 将删除原文件 ID: "+YandeCGGridFSList.get(i).getId());
					File storeFile = new File(YandeCGGridFSList.get(i).getFileMenuList());
					if(storeFile.delete()==Boolean.FALSE){
						logger.warn("删除失败 文件名: "+YandeCGGridFSList.get(i).getFileMenuList());
					}
					else{
						logger.info("已经删除原文件 文件名: " + YandeCGGridFSList.get(i).getFileMenuList());
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/** 下载指定个数的未下载任务 指定开始页面 及结束页面 **/
	public void downLoadTenRadomNumFile(Method method) {
		YandeCGMongoDBMethod yandeCGMongoDBMethod = new YandeCGMongoDBMethod();
		List<YandeCG> yandeCGList = new ArrayList<YandeCG>();
		/**获取下载前 未下载文件的占比**/
		String frontDownloadPercent = CellsUtil.filePercent(yandeCGMongoDBMethod.getDataBaseCollectionToyalNum(), yandeCGMongoDBMethod.noDownloadCompleteFileNum());
		yandeCGList = yandeCGMongoDBMethod.getTenNumFileToDownLoad(Integer.parseInt(method.getParameter().get(0).getParameter()),Integer.parseInt(method.getParameter().get(1).getParameter()));
		ExecutorService pool = Executors.newFixedThreadPool(Integer.parseInt(method.getParameter().get(2).getParameter()));
		logger.info("远程下载系统启动 本次创建线程池大小: "+method.getParameter().get(2).getParameter()+" Pool");
		int num = yandeCGList.size();
		for (int i = 0; i < num; i++) {
			DownLoadFileThread downLoadFileThread = new DownLoadFileThread(yandeCGList.get(i));
			pool.execute(downLoadFileThread);
		}
		pool.shutdown();
		while(pool.isTerminated()==false)
		{
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("正在搜集本次下载任务的详细情况");
		CellsUtil cellsUtil = new CellsUtil();
		String result = cellsUtil.fileDownLoadSuccessPercent(yandeCGList);
		logger.info("本次下载的成功率: "+result);
		SentMessageService sentMessageService = new SentMessageService("PISUAL CELLS SYSTEM BigCG CELLS "+System.getProperty("webStatus")+"Model 每日定时下载任务报告","本次下载任务总数: "+num+"</p> 本次下载的成功率:"+result+"</p> 此次未下载同步前数据库的未下载文件百分比:"+frontDownloadPercent+"</p>下载完成后数据库没有下载同步数据的百分比:"+CellsUtil.filePercent(yandeCGMongoDBMethod.getDataBaseCollectionToyalNum(), yandeCGMongoDBMethod.noDownloadCompleteFileNum())+"</p>");
	}
	
	/**下载数据库所有文件**/
	public void getAllFileFromDataBase(Method method)
	{
		YandeCGGridFSMethod YandeCGGridFSMethod = new YandeCGGridFSMethod();
		List<YandeCGGridFS> yandeCGGridFSList = YandeCGGridFSMethod.getAllMenuFromMongodb();
		int num = yandeCGGridFSList.size();
		for(int i=0;i<num;i++)
		{
			YandeCGGridFSMethod.getYandeCGFileFromMethod(yandeCGGridFSList.get(i));
		}
	}
	
	/**列表数据库与存储数据库进行同步 一般用于同步文件两端的现在不同步的问题**/
	public void synchronousListAndStoreDownloadStatus(Method method)
	{
		try {
			logger.info("开始进行远程同步");
			YandeCGGridFSMethod yandeCGGridFSMethod = new YandeCGGridFSMethod();
			YandeCGMongoDBMethod yandeCGMongoDBMethod = new YandeCGMongoDBMethod();
			PisualIOUtil pisualIOUtil = new PisualIOUtil();
			List<YandeCG> YandeCGList = new ArrayList<YandeCG>();
			YandeCGList = yandeCGMongoDBMethod.listAllYandeCG();
			System.out.println(YandeCGList.size());
			int totalYandeCGNum = YandeCGList.size();
			if(totalYandeCGNum==0)
			{
				logger.info("指定目录下没有文件");
				return;
			}
			for(int i=0;i<totalYandeCGNum;i++){
				if(yandeCGGridFSMethod.testIDExist(YandeCGList.get(i).getId().toString())==true){
					YandeCGList.get(i).setDownComplete(true);
					yandeCGMongoDBMethod.updateDownloadComplete(YandeCGList.get(i));
					logger.info("更新下载状态为已下载");
				}
				else{
					YandeCGList.get(i).setDownComplete(false);
					yandeCGMongoDBMethod.updateDownloadComplete(YandeCGList.get(i));
					logger.info("更新下载状态为未下载");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
}
