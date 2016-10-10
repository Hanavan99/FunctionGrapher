package com.functiongrapher.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class SplashScreen extends JFrame {

	private static final long serialVersionUID = 7768312899969173371L;

	private static final int width = 400;
	private static final int height = 250;
	
	private static Image bgimage;
	
	public SplashScreen() {
		try {
			bgimage = ImageIO.read(SplashScreen.class.getResourceAsStream("/assets/images/newsin.bmp"));
		} catch (Exception e) {
			bgimage = null;
		}
		int xpos = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (width / 2);
		int ypos = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (height / 2);
		setBounds(xpos, ypos, width, height);
		setUndecorated(true);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g2d.drawImage(bgimage, 0, 0, width - 1, height - 1, null);
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawRect(0, 0, width - 1, height - 1);
		g2d.setColor(Color.GREEN);
		g2d.setFont(new Font("Arial", 24, Font.PLAIN));
		g2d.drawString("FunctionGrapher v2.0.0", 200, 50);
	}
	
	
	
}
