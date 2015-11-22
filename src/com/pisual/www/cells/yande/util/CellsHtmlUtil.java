package com.pisual.www.cells.yande.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;


/**
 * Html辅助工具包
 * @author felixerio
 * @version1.0
 * **/
public class CellsHtmlUtil {
	static Logger logger = Logger.getLogger(CellsHtmlUtil.class.getName());
	/**
	 * 获取指定URL的XML数据或Json数据
	 * @author felixerio
	 * @version 1.0
	 * **/
	public String getHtmlXMLOrJson(String url)
	{
		try {
			StringBuffer temp = new StringBuffer();
			URLConnection uc = new URL(url).openConnection();
            uc.setConnectTimeout(3000);
            uc.setDoOutput(true);
            InputStream in = new BufferedInputStream(uc.getInputStream());
            Reader rd = new InputStreamReader(in,"UTF-8");
            int c = 0;
            while ((c = rd.read()) != -1) {
                temp.append((char) c);
            }
            in.close();
            logger.info("URL :"+url+" has read Success");
           return(temp.toString());
			
		} catch (Exception e) {
			logger.error("URL :"+url+" read fault");
			logger.error("Fault Messages :"+e.getMessage());
		}finally
		{
		}
		return "fault";
	}
	/**
	 * 获取当前根目录
	 * @author felixerio
	 * @version 1.0
	 * 
	 * **/
	public static String getAbsuletyPath()
	{
		return System.getProperty("user.dir");
	}

}
