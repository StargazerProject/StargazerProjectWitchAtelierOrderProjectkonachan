package com.pisual.witchatelier.job;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.pisual.witchatelier.order.analytical.AnalyticalAndPost;
import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.model.factory.OrderFactory;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

/**
 * 在指定时间 下载100个远程数据 共发送10条指令
 * @author felixsion
 * **/
public class DownLoad100NumPic {
	/**日志初始化**/
	static Logger logger = Logger.getLogger(DownLoad100NumPic.class.getName());
	public void doJob()
	{
		try {
				OrderFactory orderFactory = new OrderFactory();
				Order order = orderFactory.SampleMessgeOrder();
				order.getOrderMessage().setOrderSentIP(PropertiesUtil.getValue("BigcgSystemCellsIP"));
				order.getOrderMessage().setOrderSentPort(10751);
				order.getOrderMessage().setOrderGetIP(InetAddress.getLocalHost().getHostAddress());
				order.getOrderMessage().setOrderGetPort(10742);
				order.getOrderMessage().setOrderLevel("TransactionLevelCommand");
				order.getOrderMessage().setTargetCellsName("BigcgSystemCellsName");
				order.getOrderMessage().getOrderMethod().getMethod().get(0).setMethodName("downLoadTenRadomNumFile");
				order.getOrderMessage().getOrderMethod().getMethod().get(0).getParameter().get(0).setParameter("0");
				order.getOrderMessage().getOrderMethod().getMethod().get(0).getParameter().get(1).setParameter("1000");
				order.getOrderMessage().getOrderMethod().getMethod().get(0).getParameter().get(2).setParameter("5");
				AnalyticalAndPost.postOrder(order);
				logger.info("系统定时任务指令系统同已经发出指令 ID:"+order.getOrderID().getID());
				Thread.sleep(500);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	public static void main(String[] args) {
		DownLoad100NumPic s = new DownLoad100NumPic();
		s.doJob();
	}
}