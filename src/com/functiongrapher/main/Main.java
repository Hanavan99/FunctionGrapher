package com.functiongrapher.main;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

import com.functiongrapher.ui.GraphWindow;
import com.functiongrapher.ui.VarsWindow;
import com.functiongrapher.ui.WindowManager;

public class Main {

	public static void main(String[] args) {

		WindowManager.showSplashScreen(3000);
		WindowManager.addWindow(new VarsWindow());

		Timer fpsmeter = new Timer(1000, (ActionEvent e) -> {
			System.out.println(GraphWindow.getFPS());
			GraphWindow.resetFPS();
		});
		//fpsmeter.start();

		Thread graphwindowthread = new Thread(() -> GraphWindow.init());
		graphwindowthread.start();

	}

}
