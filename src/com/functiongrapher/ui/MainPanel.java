package com.functiongrapher.ui;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1376004481637313198L;

	private static JButton takeShot;
	
	public MainPanel() {
		setLayout(null);
		
		takeShot = new JButton("Take Screenshot");
		takeShot.setBounds(10, 10, 150, 20);
		add(takeShot);
		takeShot.addActionListener((ActionEvent e) -> {
			GraphWindow.takeScreenshot();
			try {
				Thread.sleep(500);
				ImageIO.write(GraphWindow.getScreenshot(), "PNG", new File("C:/Users/Hanavan/Desktop/test.png"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		});
		
	}
	
}
