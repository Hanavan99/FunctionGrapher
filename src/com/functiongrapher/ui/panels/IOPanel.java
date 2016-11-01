package com.functiongrapher.ui.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import com.functiongrapher.function.FunctionManager;
import com.functiongrapher.io.PresetFileIO;
import com.functiongrapher.main.ProgramInfo;
import com.functiongrapher.ui.EquationEditor;
import com.functiongrapher.ui.windows.GraphWindow;
import com.functiongrapher.ui.windows.VarsWindow;
import com.functiongrapher.ui.windows.WindowManager;
import com.functiongrapher.util.GraphParameter;
import com.functiongrapher.util.GraphProperty;

public class IOPanel extends JPanel {

	private static final long serialVersionUID = -8616150106824748424L;

	private JButton savePreset;
	private JButton loadPreset;
	private JButton saveShot;

	public IOPanel() {
		setLayout(null);

		savePreset = new JButton("Save Preset File");
		savePreset.setBounds(10, 10, 150, ProgramInfo.DEFAULTCOMPONENTHEIGHT);
		add(savePreset);
		savePreset.addActionListener((ActionEvent e) -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setAcceptAllFileFilterUsed(false);
			for (FileFilter f : ProgramInfo.PRESETFILE_FILTERS) {
				chooser.addChoosableFileFilter(f);
			}
			if (chooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
				return;
			ArrayList<GraphParameter<?>> properties = new ArrayList<GraphParameter<?>>();
			properties.add(new GraphParameter<Double>(GraphProperty.WINDOW_XMIN, FunctionManager.getXmin()));
			properties.add(new GraphParameter<Double>(GraphProperty.WINDOW_XMAX, FunctionManager.getXmax()));
			properties.add(new GraphParameter<Double>(GraphProperty.WINDOW_YMIN, FunctionManager.getYmin()));
			properties.add(new GraphParameter<Double>(GraphProperty.WINDOW_YMAX, FunctionManager.getYmax()));
			properties.add(new GraphParameter<Boolean>(GraphProperty.VIEW_GRAPHMODE, GraphWindow.getGraphMode()));
			for (EquationEditor ee : FunctionManager.getFunctions()) {
				properties.add(new GraphParameter<String>(GraphProperty.FUNCTION, ee.getSaveData()));
			}
			PresetFileIO.savePresetToFile(chooser.getSelectedFile(), properties);
			JOptionPane.showMessageDialog(this, "Preset file saved!");
		});

		loadPreset = new JButton("Load Preset File");
		loadPreset.setBounds(10, 40, 150, ProgramInfo.DEFAULTCOMPONENTHEIGHT);
		add(loadPreset);
		loadPreset.addActionListener((ActionEvent e) -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setAcceptAllFileFilterUsed(false);
			for (FileFilter f : ProgramInfo.PRESETFILE_FILTERS) {
				chooser.addChoosableFileFilter(f);
			}
			if (chooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
				return;
			ArrayList<GraphParameter<?>> properties = PresetFileIO.loadPresetFromFile(chooser.getSelectedFile());
			boolean ask = false;
			for (GraphParameter<?> param : properties) {
				switch (param.getType()) {
				case FUNCTION:
					if (ask == false) {
						ask = true;
						if (JOptionPane.showConfirmDialog(this, "It seems that there are some functions defined in this file. Would you like to delete the ones that are currently on the equation panel?") == JOptionPane.OK_OPTION) {
							FunctionManager.clearFunctions();
						}
					}
					String data = param.getData().toString();
					String[] subdata = data.split("\\$", 6);
					EquationEditor ee = new EquationEditor(subdata[0]);
					ee.setEquationType(Integer.valueOf(subdata[1]));
					ee.setEquationColor(new Color(Integer.valueOf(subdata[2]), Integer.valueOf(subdata[3]), Integer.valueOf(subdata[4])));
					ee.setEquationText(subdata[5]);
					FunctionManager.addFunction(ee);
					break;
				case WINDOW_GRID_STEPX:
					break;
				case WINDOW_GRID_STEPY:
					break;
				case WINDOW_XMAX:
					FunctionManager.setXmax(Double.valueOf(param.getData().toString()));
					break;
				case WINDOW_XMIN:
					FunctionManager.setXmin(Double.valueOf(param.getData().toString()));
					break;
				case WINDOW_YMAX:
					FunctionManager.setYmax(Double.valueOf(param.getData().toString()));
					break;
				case WINDOW_YMIN:
					FunctionManager.setYmin(Double.valueOf(param.getData().toString()));
					break;
				case VIEW_GRAPHMODE:
					((VarsWindow) WindowManager.getWindow(ProgramInfo.WINDOW_VARS_NAME_INTERNAL)).getViewPanel().setGraphMode(Boolean.valueOf(param.getData().toString()));
					break;
				default:
					break;
				}
			}
			JOptionPane.showMessageDialog(this, "Preset file loaded!");
		});

		saveShot = new JButton("Take Screenshot");
		saveShot.setBounds(10, 70, 150, ProgramInfo.DEFAULTCOMPONENTHEIGHT);
		add(saveShot);
		saveShot.addActionListener((ActionEvent e) -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setAcceptAllFileFilterUsed(false);
			for (FileFilter f : ProgramInfo.SCREENSHOT_FILTERS) {
				chooser.addChoosableFileFilter(f);
			}
			if (chooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
				return;
			GraphWindow.takeScreenshot();
			try {
				Thread.sleep(500);
				ImageIO.write(GraphWindow.getScreenshot(), "PNG", chooser.getSelectedFile());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, "Graph image saved!");
		});

	}

}
