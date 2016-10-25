package com.functiongrapher.ui.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import com.functiongrapher.function.FunctionManager;
import com.functiongrapher.io.PresetFileIO;
import com.functiongrapher.logging.ProgramLogger;
import com.functiongrapher.main.ProgramInfo;
import com.functiongrapher.ui.EquationEditor;
import com.functiongrapher.ui.windows.GraphWindow;
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
			for (EquationEditor ee : FunctionManager.getFunctions()) {
				properties.add(new GraphParameter<String>(GraphProperty.FUNCTION, ee.getSaveData()));
			}
			PresetFileIO.savePresetToFile(chooser.getSelectedFile(), properties);
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
			for (GraphParameter<?> param : properties) {
				switch (param.getType()) {
				case FUNCTION:
					String data = param.getData().toString();
					String[] subdata = data.split("\\$", 6);
					ProgramLogger.info(String.valueOf(subdata.length));
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
					break;
				case WINDOW_XMIN:
					break;
				case WINDOW_YMAX:
					break;
				case WINDOW_YMIN:
					break;
				default:
					break;
				}
			}
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

		});

	}

}
