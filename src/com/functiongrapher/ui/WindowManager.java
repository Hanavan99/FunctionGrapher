package com.functiongrapher.ui;

import java.util.ArrayList;

import javax.swing.JFrame;

public class WindowManager {

	private static ArrayList<JFrame> windows = new ArrayList<JFrame>();
	
	public static void showSplashScreen(int idletime) {
		SplashScreen ss = new SplashScreen();
		windows.add(ss);
		try {
			Thread.sleep(idletime);
		} catch (Exception e) {
			
		}
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
