package com.stargazer.witchatelier.util.system;

import java.util.UUID;

public class IDUtil {
	public static String ID()
	{
		String uuid = UUID.randomUUID().toString();
		return TimeUtil.IDTime()+uuid.replaceAll("-", "");
	}
	public static void main(String[] args) {
		IDUtil.ID();
	}
}
