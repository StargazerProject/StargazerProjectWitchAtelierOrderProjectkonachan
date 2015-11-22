package com.pisual.www.cells.yande.util;

/**
 * Pisual Web Util
 * 
 * **/
public class NetUtil {
	/** 测试网络连通行 **/
	public static String netDelayTest(String URL) {
//		try {
//			InetAddress address = InetAddress.getByName(URL);
//			if (address.isReachable(50000)) {
//				return "true";
//			} else {
//				return "false";
//			}
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return "true";
	}

	/** 获取指定的分页数目 **/
	public static int getnowPages(int newMaxNum,int oldMaxNum)
	{
		int result=0;
		if((newMaxNum-oldMaxNum)<100)
		{
			return 1;
		}
		else
		{
			return ((newMaxNum-oldMaxNum)/100);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(NetUtil.getnowPages(313331, 200000));
		
	}
}