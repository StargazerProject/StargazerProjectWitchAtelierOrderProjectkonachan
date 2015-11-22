package com.pisual.witchatelier.job;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.pisual.witchatelier.order.analytical.AnalyticalAndPost;
import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.model.factory.OrderFactory;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

/**
 * 远程同步数据 每个一小时进行一次数据同步
 * @author felixsion
 * **/
public class SynchronousRemoteData {
	/**日志初始化**/
	static Logger logger = Logger.getLogger(SynchronousRemoteData.class.getName());
	public void doJob()
	{
		try {
			OrderFactory orderFactory = new OrderFactory();
			Order order = orderFactory.SampleDateOrder();
			order.getOrderMessage().setOrderSentIP(PropertiesUtil.getValue("BigcgSystemCellsIP"));
			order.getOrderMessage().setOrderSentPort(10751);
			order.getOrderMessage().setOrderGetIP(InetAddress.getLocalHost().getHostAddress());
			order.getOrderMessage().setOrderGetPort(10742);
			order.getOrderMessage().setOrderLevel("TransactionLevelCommand");
			order.getOrderMessage().setTargetCellsName("BigcgSystemCellsName");
			order.getOrderMessage().getOrderMethod().getMethod().get(0).setMethodName("receiveAndSaveMessage");
			order.getOrderMessage().getOrderMethod().getMethod().get(0).getParameter().get(0).setParameter("1");
			order.getOrderMessage().getOrderMethod().getMethod().get(0).getParameter().get(1).setParameter("1");
			AnalyticalAndPost.postOrder(order);
			logger.info("系统定时任务指令系同已经发出指令 ID:"+order.getOrderID().getID());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	public static void main(String[] args) {
		SynchronousRemoteData s = new SynchronousRemoteData();
		s.doJob();
	}
}
