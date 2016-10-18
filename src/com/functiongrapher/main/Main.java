package com.functiongrapher.main;

import com.functiongrapher.ui.GraphWindow;
import com.functiongrapher.ui.VarsWindow;
import com.functiongrapher.ui.WindowManager;
import com.functiongrapher.ui.gfx.GraphicsController;

public class Main {
	
	public static void main(String[] args) {
		
		WindowManager.showSplashScreen(3000);
		WindowManager.addWindow(new VarsWindow(), "vars");

		// TODO remember the FPS timer...

		Thread graphwindowthread = new Thread(() -> {
			GraphicsController.initGLFW();
			GraphWindow.setWindowID(GraphicsController.getWindowID());
			GraphicsController.attachCallbacks();
			GraphicsController.initGL();
			GraphWindow.loop();
			GraphicsController.shutdown();
		});
		graphwindowthread.start();
		
		
		
	}

}