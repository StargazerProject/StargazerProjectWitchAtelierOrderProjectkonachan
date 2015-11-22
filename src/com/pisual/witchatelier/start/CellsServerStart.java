package com.pisual.witchatelier.start;

import com.pisual.www.cells.ui.PisualCellsUI;
import com.stargazer.witchatelier.summusdensitatesegmentation.SummusDensitateSegmentationService;

public class CellsServerStart {
	public static void main(String[] args) {
		PisualCellsUI pisualCellsUI = new PisualCellsUI();
		SummusDensitateSegmentationService s = new SummusDensitateSegmentationService(10740);
		s.startMessageService();
	}
}
