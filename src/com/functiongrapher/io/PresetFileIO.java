package com.functiongrapher.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.functiongrapher.logging.ProgramLogger;
import com.functiongrapher.util.GraphParameter;
import com.functiongrapher.util.GraphProperty;

public class PresetFileIO {

	public static ArrayList<GraphParameter<?>> loadPresetFromFile(File f) {
		try {
			ArrayList<GraphParameter<?>> properties = new ArrayList<GraphParameter<?>>();
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split("=", 2);
				properties.add(new GraphParameter<String>(GraphProperty.valueOf(data[0]), data[1]));	
			}
			reader.close();
			return properties;
		} catch (Exception e) {
			ProgramLogger.warning(e.toString());
			return null;
		}
		
	}

	public static void savePresetToFile(File f, ArrayList<GraphParameter<?>> properties) {
		try {
			PrintWriter writer = new PrintWriter(f);
			GraphProperty[] parray = GraphParameter.getPropertiesAsArray(properties);
			Object[] darray = GraphParameter.getDataAsArray(properties);
			for (int i = 0; i < properties.size(); i++) {
				writer.println(parray[i].name() + "=" + String.valueOf(darray[i].toString()));
			}
			writer.close();
		} catch (Exception e) {
			ProgramLogger.warning(e.toString());
		}
	}

}
