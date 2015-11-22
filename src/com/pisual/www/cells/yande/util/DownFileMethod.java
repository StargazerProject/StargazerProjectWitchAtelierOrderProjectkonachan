package com.pisual.www.cells.yande.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.log4j.Logger;


public class DownFileMethod {
	/** 日志初始化 **/
	static Logger logger = Logger.getLogger(DownFileMethod.class.getName());
	CellsUtil cellsUtil = new CellsUtil();
	
	/**单线程普通下载文件模式**/
	public String normalDownFileIncludePercent(String url, String file,int filesize){
		FileOutputStream output = null;
		InputStream input = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpClientSendPost.enableSSL(httpClient);
			HttpGet httpGet = new HttpGet(url);
      		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
			HttpResponse response = httpClient.execute(httpGet);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					File storeFile = new File(file);
					output = new FileOutputStream(storeFile);
					input = entity.getContent();
					byte b[] = new byte[102400];
					int j = 0;
					int f = 1;
					int oldsize = 1024;
					int oldnum = 1;
					System.out.println("开始下载文件 文件大小:"+filesize+" 字节");
					while ((j = input.read(b)) != -1) {
						output.write(b, 0, j);
						oldnum = CellsUtil.downLoadFileProgressBar(oldsize,filesize,oldsize+10240*f,oldnum);
						oldsize = oldsize+1024*f;
						f++;
					}
					System.out.print(" 100% ");
					System.out.println("本文件全部下载完成 等待存储层校验 文件大小:"+filesize+" 字节");
					output.flush();
					output.close();
					input.close();
				}
				if (entity != null) {
					entity.consumeContent();
				}
				return "true";
			}
		} catch (Exception e) {
			try {
				if(output!=null)
				{
					output.close();
				}
				if(input!=null)
				{
					input.close();
				}
			} catch (IOException e1) {
				logger.error(e1.getLocalizedMessage());
			}
			logger.error(e.getLocalizedMessage());
			File storeFile = new File(file);
			if(storeFile.delete()==Boolean.FALSE)
			{
				logger.warn("删除失败 文件名: "+file);

			}
			else
			{
				logger.info("已经删除校验失败的文件 文件名: "+file);
			}
			return "false";
		}
		return "NULL";
	}
}
