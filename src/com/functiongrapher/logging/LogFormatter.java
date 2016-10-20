package com.functiongrapher.logging;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		String date = (new SimpleDateFormat("yyyy-mm-dd hh:mm:ss")).format(Date.from(Instant.ofEpochSecond(record.getMillis() / 1000)));
		return "[" + date + " - " + record.getLevel().getName() + "]: " + record.getMessage() + "\r\n";
	}
}
