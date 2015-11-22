package com.pisual.witchatelier.start;

import com.pisual.liliaui.ui.LiliaUIStart;
import com.pisual.www.cells.ui.PisualCellsSystemBigcgCellsUI;
import com.stargazer.witchatelier.summusdensitatesegmentation.SummusDensitateSegmentationService;

public class SummusDensitateSegmentationServiceStart {
	public static void main(String[] args) {
		LiliaUIStart liliaUIStart = new LiliaUIStart();
		SummusDensitateSegmentationService s = new SummusDensitateSegmentationService(10751);
		s.startMessageService();
	}
}
