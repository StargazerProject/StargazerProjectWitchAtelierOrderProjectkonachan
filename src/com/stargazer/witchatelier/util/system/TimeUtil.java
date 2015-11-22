package com.stargazer.witchatelier.util.system;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	/** 输出格式: 20060101000000***/
	public static String IDTime(){
		DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
		return format2.format(new Date());
	}
}
