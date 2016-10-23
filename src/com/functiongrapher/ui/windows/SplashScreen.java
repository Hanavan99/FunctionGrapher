package com.functiongrapher.ui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.functiongrapher.main.ProgramInfo;

public class SplashScreen extends JFrame {

	private static final long serialVersionUID = 7768312899969173371L;

	private static final int width = 400;
	private static final int height = 250;
	
	private String subtext = "";
	
	public SplashScreen() {
		int xpos = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (width / 2);
		int ypos = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (height / 2);
		setBounds(xpos, ypos, width, height);
		setUndecorated(true);
		setAlwaysOnTop(true);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width - 1, height - 1);
		g2d.setColor(Color.GREEN);
		g2d.drawRect(0, 0, width - 1, height - 1);
		g2d.setFont(new Font("Arial", Font.PLAIN, 28));
		g2d.drawString(ProgramInfo.PROGRAM_NAME, width - 250, height - 120);
		g2d.setFont(new Font("Arial", Font.PLAIN, 14));
		g2d.drawString(ProgramInfo.PROGRAM_VERSION, width - 250, height - 100);
		g2d.drawString(subtext, width - 250, height - 80);
		g2d.drawString(ProgramInfo.COPYRIGHT_INFO, width - 250, height - 10);
	}
	
	public void setSubtext(String subtext) {
		this.subtext = subtext;
		repaint();
	}
	
	
	
}
