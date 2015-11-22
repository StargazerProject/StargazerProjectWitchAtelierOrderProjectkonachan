package com.pisual.www.cells.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class PisualCellsUI {
	public PisualCellsUI() {
		JFrame f = new JFrame("Pisual Message Server Cells"){
	    public void paint(Graphics g) {
        super.paint(g);
	    Rectangle rect = this.getBounds();
        int width = (int) rect.getWidth() - 1;
		int height = (int) rect.getHeight() - 1;
		g.setColor(Color.black);
		g.drawRect(0, 0, width, height);
		}
		};
		f.setLocationRelativeTo(null);
		f.setForeground(Color.black);
		f.setSize(new Dimension(1280, 720));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/pisualcells"+"/ui/"+"ban.png"));
		BackgroundPanel bgp;
		bgp=new BackgroundPanel((new ImageIcon(System.getProperty("user.dir")+"/pisualcells"+"/ui/pisualcellssystemcells.png")).getImage());
		bgp.setBounds(0,0,1280,720);
		f.add(bgp);
		f.setVisible(true);
	}
}
