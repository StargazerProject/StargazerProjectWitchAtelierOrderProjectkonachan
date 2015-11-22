package com.pisual.witchatelier.job;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.pisual.witchatelier.order.analytical.AnalyticalAndPost;
import com.stargazer.witchatelier.model.event.Order;
import com.stargazer.witchatelier.model.factory.OrderFactory;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

/**
 * 系统Cells启动
 * @author felixsion
 * **/
public class StartCells {
	/**日志初始化**/
	static Logger logger = Logger.getLogger(StartCells.class.getName());
	public void doJob()
	{
		try {
				OrderFactory orderFactory = new OrderFactory();
				Order order = orderFactory.SampleDateOrder();
				order.getOrderMessage().setOrderSentIP(PropertiesUtil.getValue("BigcgSystemCellsIP"));
				order.getOrderMessage().setOrderSentPort(Integer.parseInt(PropertiesUtil.getValue("BigcgSystemCellsPort")));
				order.getOrderMessage().setOrderGetIP(InetAddress.getLocalHost().getHostAddress());
				order.getOrderMessage().setOrderGetPort(10740);
				order.getOrderMessage().setOrderLevel("SystemLevelCommand");
				order.getOrderMessage().setTargetCellsName("CellsName");
				order.getOrderMessage().getOrderMethod().getMethod().get(0).setMethodName("systemStart");
				order.getOrderMessage().getOrderMethod().getMethod().get(0).getParameter().get(0).setParameter("StartBigCGCellsServerkonachanTest.jar");
				order.getOrderMessage().getOrderMethod().getMethod().get(0).getParameter().get(1).setParameter("100");
				AnalyticalAndPost.postOrder(order);
				logger.info("系统定时任务指令系统同已经发出指令 ID:"+order.getOrderID().getID());
				Thread.sleep(500);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	public static void main(String[] args) {
		StartCells s = new StartCells();
		s.doJob();
	}
}