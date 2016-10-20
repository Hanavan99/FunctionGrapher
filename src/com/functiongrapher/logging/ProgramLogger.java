package com.functiongrapher.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import com.functiongrapher.ui.SplashScreen;
import com.functiongrapher.ui.WindowManager;

public class ProgramLogger {

	public static final Logger LOGGER = Logger.getLogger(ProgramLogger.class.getName());

	public static void init() {
		LOGGER.setUseParentHandlers(false);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new LogFormatter());
		LOGGER.addHandler(handler);
	}

	public static void setSplashScreenSubtext(String text) {
		((SplashScreen) WindowManager.getWindow("splashscreen")).setSubtext(text);
	}

}
