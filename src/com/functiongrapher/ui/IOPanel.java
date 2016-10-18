package com.functiongrapher.ui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.functiongrapher.function.FunctionManager;
import com.functiongrapher.io.PresetFileIO;
import com.functiongrapher.util.GraphProperty;

public class IOPanel extends JPanel {

	private static final long serialVersionUID = -8616150106824748424L;
	
	private JButton savePreset;
	private JButton loadPreset;
	
	public IOPanel() {
		setLayout(null);
		
		savePreset = new JButton("Save Preset File");
		savePreset.setBounds(10, 10, 150, 20);
		add(savePreset);
		savePreset.addActionListener((ActionEvent e) -> {
			File f = new File("C:/Users/kuhnhan000/Desktop/test.gdb");
			HashMap<GraphProperty, Object> properties = new HashMap<GraphProperty, Object>();
			properties.put(GraphProperty.WINDOW_XMIN, FunctionManager.getXmin());
			PresetFileIO.savePresetToFile(f, properties);
		});
		
		loadPreset = new JButton("Load Preset File");
		loadPreset.setBounds(10, 10, 150, 20);
		add(loadPreset);
		loadPreset.addActionListener((ActionEvent e) -> {
			File f = new File("C:/Users/kuhnhan000/Desktop/test.gdb");
			HashMap<GraphProperty, Object> properties = PresetFileIO.loadPresetFromFile(f);
			FunctionManager.setXmin((double) properties.get(GraphProperty.WINDOW_XMIN)); 
		});
		
	}

}
