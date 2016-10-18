package com.functiongrapher.ui;

import java.util.ArrayList;

import javax.swing.JFrame;

public class WindowManager {

	private static ArrayList<JFrame> windows = new ArrayList<JFrame>();
	private static ArrayList<String> windownames = new ArrayList<String>();

	public static void showSplashScreen(int idletime, int posttime) {
		SplashScreen ss = new SplashScreen();
		addWindow(ss, "splashscreen");
		try {
			Thread.sleep(idletime);
		} catch (Exception e) {

		}
		Thread checker = new Thread(() -> {
			while (GraphWindow.isWindowVisible() == false) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					
				}
			}
			try {
				Thread.sleep(posttime);
			} catch (Exception e) {

			}
			ss.setVisible(false);
		});
		checker.start();
	}

	public static void addWindow(JFrame window, String name) {
		if (!windows.contains(window)) {
			windows.add(window);
			windownames.add(name);
		}
	}
	
	public static JFrame getWindow(String name) {
		return windows.get(windownames.indexOf(name));
	}
	
	public static void setWindowVisibility(String name, boolean flag) {
		if (windownames.contains(name)) {
			windows.get(windownames.indexOf(name)).setVisible(flag);
		}
	}

	public static void killWindows() {
		for (JFrame j : windows) {
			j.setVisible(false);
			j.dispose();
		}
	}

}
