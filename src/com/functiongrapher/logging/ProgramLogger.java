package com.functiongrapher.logging;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.logging.Level;

import com.functiongrapher.ui.windows.SplashScreen;
import com.functiongrapher.ui.windows.WindowManager;

public class ProgramLogger {

	private static LogFormatter formatter;
	private static Level minlevel = Level.ALL;
	private static PrintWriter out;

	public static void init() {
		formatter = new LogFormatter();
		File f = new File("log-latest.txt");
		try {
			out = new PrintWriter(f);
		} catch (Exception e) {
			warning("Could not create log file!");
			out = null;
		}
	}
	
	public static void shutdown() {
		out.close();
	}

	public static void setLevel(Level level) {
		minlevel = level;
	}

	public static void log(String message, Level level) {
		if (level.intValue() >= minlevel.intValue()) {
			System.out.print(formatter.format(Calendar.getInstance().getTimeInMillis(), level, message));
			if (out != null) {
				out.print(formatter.format(Calendar.getInstance().getTimeInMillis(), level, message));
				out.flush();
			}
		}
	}

	public static void err(String message, Level level) {
		if (level.intValue() >= minlevel.intValue()) {
			System.err.print(formatter.format(Calendar.getInstance().getTimeInMillis(), level, message));
			if (out != null) {
				out.print(formatter.format(Calendar.getInstance().getTimeInMillis(), level, message));
				out.flush();
			}
		}
	}

	public static void finest(String message) {
		log(message, Level.FINEST);
	}

	public static void finer(String message) {
		log(message, Level.FINER);
	}

	public static void fine(String message) {
		log(message, Level.FINE);
	}

	public static void config(String message) {
		log(message, Level.CONFIG);
	}

	public static void info(String message) {
		log(message, Level.INFO);
	}

	public static void warning(String message) {
		log(message, Level.WARNING);
	}

	public static void severe(String message) {
		err(message, Level.SEVERE);
	}

	public static void setSplashScreenSubtext(String text) {
		info("SPLASHSCREEN: " + text);
		((SplashScreen) WindowManager.getWindow("splashscreen")).setSubtext(text);
	}

}
