package com.functiongrapher.logging;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;

public class LogFormatter {

	public String format(long t, Level level, String message) {
		String date = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(Date.from(Instant.ofEpochSecond(t / 1000)));
		return "[" + date + " - " + level.getName() + "]: " + message + "\r\n";
	}
}
