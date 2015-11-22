package com.stargazer.witchatelier.util.system;

import org.apache.log4j.Logger;

import com.pisual.witchatelier.dao.impl.YandeCGMongoDBMethod;
import com.pisual.witchatelier.model.YandeCG;
import com.pisual.www.cells.yande.util.DownFileMethod;
import com.pisual.www.cells.yande.util.FileMD5;

public class DownLoadFileThread extends Thread {
	private YandeCG yandeCG;
	YandeCGMongoDBMethod yandeCGMongoDBMethod = new YandeCGMongoDBMethod();
	/** 日志初始化 **/
	static Logger logger = Logger.getLogger(DownLoadFileThread.class.getName());
	/**数据初始化**/
	public DownLoadFileThread(YandeCG yandeCG) {
		this.yandeCG = yandeCG;
	}
	@Override
	public void run() {
		logger.info("开始下载文件 ID: "+yandeCG.getId());
		DownFileMethod downFileMethod = new DownFileMethod();
		int indexNum = yandeCG.getFile_url().lastIndexOf('.');
		if ((indexNum > -1) && (indexNum < (yandeCG.getFile_url().length() - 1))) {
			String fileName = PropertiesUtil.getValue("MongodbYandeCGGridFSTempPath")+PropertiesUtil.getValue("webStatus")+" "+yandeCG.getId()+" ."+yandeCG.getFile_url().substring(indexNum + 1);		
			switch (downFileMethod.normalDownFileIncludePercent(yandeCG.getFile_url(),fileName,Integer.parseInt(yandeCG.getFile_size()))) {
			case "true":
				logger.info("开始对文件进行校验 文件名: "+fileName);
				try {
					String md5 = FileMD5.getFileMD5String(fileName);
					if(md5.endsWith(yandeCG.getMd5())==true){
						logger.info("校验相符 原文件MH5:"+yandeCG.getMd5()+" 下载文件MH5: "+md5);
						yandeCG.setDownComplete(true);
						yandeCG.setLocal_url(fileName);
						yandeCGMongoDBMethod.changeYandeCG(yandeCG);
					}
					else{
						logger.error("校验失败 原文件MH5:"+yandeCG.getMd5()+" 下载文件MH5: "+md5);
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				System.gc();
				break;
			case "false":
				logger.error("原文件下载失败 将不进行下载级MD5校验");
				break;
			default:
				logger.error("下载过程错误 将不进行下载级MD5校验");
				break;
			}
		}
		else
		{
			String fileName = PropertiesUtil.getValue("MongodbYandeCGGridFSTempPath")+PropertiesUtil.getValue("webStatus")+" "+yandeCG.getId()+" "+yandeCG.getTags()+".png";
			switch (	downFileMethod.normalDownFileIncludePercent(yandeCG.getFile_url(),fileName,Integer.parseInt(yandeCG.getFile_size()))) {
			case "true":
				logger.info("开始对文件进行校验 文件名: "+fileName);
				try {
					String md5 = FileMD5.getFileMD5String(fileName);
					if(md5.endsWith(yandeCG.getMd5())==true){
						System.out.println("校验相符 原文件MH5:"+yandeCG.getMd5()+" 下载文件MH5: "+md5);
						yandeCGMongoDBMethod.changeYandeCG(yandeCG);
					}
					else{
						System.out.println("校验失败 原文件MH5:"+yandeCG.getMd5()+" 下载文件MH5: "+md5);
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				System.gc();
				break;
			case "false":
				logger.error("原文件下载失败 将不进行下载级MD5校验");
				break;
			default:
				logger.error("下载过程错误 将不进行下载级MD5校验");
				break;
			}
		}
	}
}
