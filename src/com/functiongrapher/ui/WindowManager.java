package com.functiongrapher.ui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class WindowManager {

	private static ArrayList<JFrame> windows = new ArrayList<JFrame>();
	
	public static void showSplashScreen(int idletime) {
		SplashScreen ss = new SplashScreen();
		windows.add(ss);
		try {
			Thread.sleep(idletime);
		} catch (Exception e) {
			
		}
		Timer t = new Timer(500, (ActionEvent e) -> {
			//if (GLFW.glfw)
		});
		ss.setVisible(false);
	}
	
	public static void addWindow(JFrame window) {
		windows.add(window);
	}
	
	public static void killWindows() {
		for (JFrame j : windows) {
			j.setVisible(false);
			j.dispose();
		}
	}
	
}
