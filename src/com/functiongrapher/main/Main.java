package com.functiongrapher.main;

import javax.swing.UIManager;

import com.functiongrapher.logging.ProgramLogger;
import com.functiongrapher.ui.gfx.GraphicsController;
import com.functiongrapher.ui.windows.GraphWindow;
import com.functiongrapher.ui.windows.TableWindow;
import com.functiongrapher.ui.windows.VarsWindow;
import com.functiongrapher.ui.windows.WindowManager;

public class Main {

	public static void main(String[] args) {

		if (System.getProperty("os.name").equals("linux")) {
			System.setProperty("org.lwjgl.librarypath", ".");
		}

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}

		ProgramLogger.init();
		ProgramLogger.info("Starting program...");
		WindowManager.showSplashScreen(3000, 1000);
		ProgramLogger.setSplashScreenSubtext("Opening vars window...");
		WindowManager.addWindow(new VarsWindow(), ProgramInfo.WINDOW_VARS_NAME_INTERNAL);
		WindowManager.addWindow(new TableWindow(), ProgramInfo.WINDOW_TABLE_NAME_INTERNAL);

		// TODO remember the FPS timer...

		Thread graphwindowthread = new Thread(() -> {
			GraphicsController.initGLFW();
			GraphWindow.setWindowID(GraphicsController.getWindowID());
			GraphicsController.attachCallbacks();
			GraphicsController.initGL();
			GraphWindow.loop();
			ProgramLogger.info("Beginning shutdown...");
			GraphicsController.shutdown();
			ProgramLogger.info("Shutdown successful. Goodbye!");
			ProgramLogger.shutdown();
		});
		graphwindowthread.start();

	}

}