package com.functiongrapher.main;

import com.functiongrapher.ui.GraphWindow;
import com.functiongrapher.ui.VarsWindow;
import com.functiongrapher.ui.WindowManager;

public class Main {

	public static void main(String[] args) {

		WindowManager.showSplashScreen(3000);
		WindowManager.addWindow(new VarsWindow());

		// TODO remember the FPS timer...

		Thread graphwindowthread = new Thread(() -> GraphWindow.init());
		graphwindowthread.start();

	}

}