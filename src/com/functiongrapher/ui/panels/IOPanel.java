package com.functiongrapher.ui.panels;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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
			JFileChooser chooser = new JFileChooser();
			if (chooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION) return;
			HashMap<GraphProperty, Object> properties = new HashMap<GraphProperty, Object>();
			properties.put(GraphProperty.WINDOW_XMIN, FunctionManager.getXmin());
			properties.put(GraphProperty.WINDOW_XMAX, FunctionManager.getXmax());
			properties.put(GraphProperty.WINDOW_YMIN, FunctionManager.getYmin());
			properties.put(GraphProperty.WINDOW_YMAX, FunctionManager.getYmax());
			//for (EquationPanel.)
			PresetFileIO.savePresetToFile(chooser.getSelectedFile(), properties);
		});
		
		loadPreset = new JButton("Load Preset File");
		loadPreset.setBounds(10, 40, 150, 20);
		add(loadPreset);
		loadPreset.addActionListener((ActionEvent e) -> {
			JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION) return;
			HashMap<GraphProperty, Object> properties = PresetFileIO.loadPresetFromFile(chooser.getSelectedFile());
			FunctionManager.setXmin(Double.valueOf(properties.get(GraphProperty.WINDOW_XMIN).toString()));
			FunctionManager.setXmax(Double.valueOf(properties.get(GraphProperty.WINDOW_XMAX).toString()));
			FunctionManager.setYmin(Double.valueOf(properties.get(GraphProperty.WINDOW_YMIN).toString()));
			FunctionManager.setYmax(Double.valueOf(properties.get(GraphProperty.WINDOW_YMAX).toString()));
		});
		
	}

}
