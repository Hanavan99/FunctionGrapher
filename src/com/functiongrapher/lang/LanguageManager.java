package com.functiongrapher.lang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.functiongrapher.logging.ProgramLogger;

public class LanguageManager {

	public enum Language {
		
		EN_US("en-US");
		
		private String name;
		
		private Language(String name) {
			this.name = name;
		}
		
	}
	
	private static Language lang;
	
	private static HashMap<String, String> map = new HashMap<String, String>();
	
	public static void setLanguage(Language lang) {
		LanguageManager.lang = lang;
	}
	
	public static void loadLangFile() {
		try {
			map.clear();
			BufferedReader reader = new BufferedReader(new InputStreamReader(LanguageManager.class.getResourceAsStream("/assets/lang/" + lang.name + ".lang")));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split("=", 2);
				map.put(data[0], data[1]);
			}
			reader.close();
			ProgramLogger.info("Loaded " + map.size() + " entries");
		} catch (Exception e) {
			ProgramLogger.warning("Could not load lang file!");
			e.printStackTrace();
		}
	}
	
	public static String getField(String name) {
		return map.get(name);
	}
	
}
