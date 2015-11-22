package com.stargazer.witchatelier.model.Method.impl;

import org.apache.log4j.Logger;

import com.pisual.www.cells.yande.util.CellsUtil;
import com.stargazer.witchatelier.messageservice.SentMessageService;
import com.stargazer.witchatelier.model.event.Order;


public class SystemLevelCommandMrthod {
	/** 日志初始化 **/
	static Logger logger = Logger.getLogger(SystemLevelCommandMrthod.class.getName());

	/**Cells System 启动**/
	public void systemStart(Order order) {
		String cmd = "java -jar "+System.getProperty("user.dir")+"/"+order.getOrderMessage().getOrderMethod().getMethod().get(0).getParameter().get(0).getParameter();
		logger.warn(cmd);
		CellsUtil.systemCMD(cmd);
		SentMessageService sentMessageService = new SentMessageService("PISUAL CELLS SYSTEM BigCG CELLS "+System.getProperty("webStatus")+" Model 已经启动"," 系统正常启动,将为您承担第一区全虚拟高能数据交换中心(Plena virtualis industria commutationem notitia centrum)的主要任务,全局跟踪ID: "+order.getOrderID().getID());
	}
	
	/**Cells System 销毁**/
	public void systemStop(Order order) {
		logger.warn("系统收到顶级控制命令 即将终止此Cells 指令 ID: "+order.getOrderID().getID());
		for(int i=0;i<100;i++)
		{
			System.out.print("#");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		SentMessageService sentMessageService = new SentMessageService("PISUAL CELLS SYSTEM BigCG CELLS "+System.getProperty("webStatus")+" Model 已经销毁","系统暂时缩减进入整备时间 稍后将正常启动CELLS 全局跟踪ID: "+order.getOrderID().getID());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(-1);
	}
	
	/** 立即停止指令端口监听，停止任何指令的接受，请慎重使用，一般用于单线程服务端终止服务 **/
	public void stopControlMessageListenImmediately(Order order) {
		logger.warn("停止系统对外命令监听 系统将处于游离无监管状态");
		System.setProperty("SummusDensitateSegmentationServiceListenStatus", "false");
	}
	
	/**紧急终止指令列表 将终止本条正在执行的指令外的其他所有指令 一般用于紧急挽救错误指令的方法**/
	public void stopOrderListImmediately(Order order) {
		logger.warn("停止系统指令监听队列 Cells将作废");
		System.setProperty("SummusDensitateSegmentationServiceDisruptorStatus", "false");
	}
}
