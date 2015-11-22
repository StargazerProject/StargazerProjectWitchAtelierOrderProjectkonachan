package com.pisual.witchatelier.start;

import com.pisual.liliaui.ui.CenterServerUIStart;
import com.stargazer.witchatelier.util.system.PropertiesUtil;

public class JobServerStart {
	public static void main(String[] args) {
		CenterServerUIStart centerServerUIStart = new CenterServerUIStart();
		PropertiesUtil.getValue("SystemVers");
		StartJob startJob = new StartJob();
	}
}
