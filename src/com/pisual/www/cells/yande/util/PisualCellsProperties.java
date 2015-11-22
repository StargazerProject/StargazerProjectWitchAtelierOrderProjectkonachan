package com.pisual.www.cells.yande.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PisualCellsProperties {
	
	/**根据指定的Key读取配置 获取到绝对地址**/
	public static String readValue(String filePath, String key) {
		String realFilePath = CellsHtmlUtil.getAbsuletyPath()+"/WebRoot/WEB-INF/classes/"+filePath;
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					realFilePath));
			props.load(in);
			String value = props.getProperty(key);
			System.out.println(key + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**读取properties的全部信息**/
    public static void readProperties(String filePath) {
     Properties props = new Properties();
        try {
         InputStream in = new BufferedInputStream (new FileInputStream(filePath));
         props.load(in);
            Enumeration en = props.propertyNames();
             while (en.hasMoreElements()) {
              String key = (String) en.nextElement();
                    String Property = props.getProperty (key);
                    System.setProperty(key, Property);
                }
        } catch (Exception e) {
         e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	PisualCellsProperties p = new PisualCellsProperties();
    	p.readProperties(CellsHtmlUtil.getAbsuletyPath()+"/properties/"+"pisualCellsYandeCG.properties");
    	p.readProperties(CellsHtmlUtil.getAbsuletyPath()+"/properties/"+"pisualCellsCenterControl.properties");
	}
}
