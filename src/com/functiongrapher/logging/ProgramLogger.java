package com.functiongrapher.logging;

import java.util.logging.Logger;

import com.functiongrapher.ui.SplashScreen;
import com.functiongrapher.ui.WindowManager;

public class ProgramLogger {

	public static final Logger LOGGER = Logger.getLogger(ProgramLogger.class.getName());
	
	public static void setSplashScreenSubtext(String text) {
		((SplashScreen) WindowManager.getWindow("splashscreen")).setSubtext(text);
	}

}
