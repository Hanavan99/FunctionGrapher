package com.functiongrapher.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

import com.functiongrapher.logging.ProgramLogger;
import com.functiongrapher.util.GraphProperty;

public class PresetFileIO {

	public static HashMap<GraphProperty, Object> loadPresetFromFile(File f) {
		try {
			HashMap<GraphProperty, Object> properties = new HashMap<GraphProperty, Object>();
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split("=", 2);
				properties.put(GraphProperty.valueOf(data[0]), (Object) data[1]);
				
			}
			reader.close();
			return properties;
		} catch (Exception e) {
			ProgramLogger.warning(e.toString());
			return null;
		}
		
	}

	public static void savePresetToFile(File f, HashMap<GraphProperty, Object> properties) {
		try {
			PrintWriter writer = new PrintWriter(f);
			for (GraphProperty p : properties.keySet()) {
				writer.println(p.name() + "=" + properties.get(p).toString());
			}
			writer.close();
		} catch (Exception e) {
			ProgramLogger.warning(e.toString());
		}
	}

}
