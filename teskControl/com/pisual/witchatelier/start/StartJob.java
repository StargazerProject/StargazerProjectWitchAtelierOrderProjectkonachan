package com.pisual.witchatelier.start;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartJob {
	public StartJob() {
		 ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml"); 
	}
	public static void main(String[] args) {
		StartJob startJob = new StartJob();
	}
}
