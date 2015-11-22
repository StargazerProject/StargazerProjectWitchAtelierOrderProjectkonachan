package com.pisual.liliaui.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class LiliaForm extends JFrame{
	private static final long serialVersionUID = 3454204717434232316L;
	
	public LiliaForm(String title) {
		this.setTitle(title);
		this.setLocationRelativeTo(null);
		this.setForeground(Color.black);
		this.setSize(new Dimension(1500, 743));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Rectangle rect = this.getBounds();
		int width = (int) rect.getWidth() - 1;
		int height = (int) rect.getHeight() - 1;
		g.setColor(Color.black);
		g.drawRect(0, 0, width, height);
	}
}
