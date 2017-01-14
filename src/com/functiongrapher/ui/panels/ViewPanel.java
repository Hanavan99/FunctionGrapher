package com.functiongrapher.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import com.functiongrapher.function.FunctionManager;
import com.functiongrapher.service.ServiceManager;
import com.functiongrapher.ui.gfx.DrawMode;
import com.functiongrapher.ui.windows.GraphWindow;

public class ViewPanel extends JPanel {

	private static final long serialVersionUID = -5999370491437275102L;

	private JCheckBox enableControl;
	private JSpinner yawSpeed;
	private JSpinner scale3d;

	private JCheckBox runFullscreen;
	private JCheckBox drawTiles;
	private JCheckBox drawLines;

	private JComboBox<String> graphicsMode;
	private JComboBox<DrawMode> drawMode;

	private ActionListener controlUpdater;

	public ViewPanel() {
		setLayout(null);

		controlUpdater = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphWindow.setControlState(!enableControl.isSelected());
				if (enableControl.isSelected()) {
					GraphWindow.setYawRotateSpeed(((Double) yawSpeed.getValue()).floatValue());
				} else {
					GraphWindow.setYawRotateSpeed(0.0f);
				}
			}
		};

		enableControl = new JCheckBox("Enable cinematic mode");
		enableControl.setBounds(10, 10, 200, 20);
		add(enableControl);
		enableControl.addActionListener(controlUpdater);

		yawSpeed = new JSpinner(new SpinnerNumberModel(0.25d, -5.0d, 5.0d, 0.125d));
		yawSpeed.setBounds(210, 10, 60, 20);
		add(yawSpeed);

		runFullscreen = new JCheckBox("Run graph window in fullscreen");
		runFullscreen.setBounds(10, 40, 210, 20);
		add(runFullscreen);
		runFullscreen.addActionListener((ActionEvent e) -> GraphWindow.setIsFullscreen(runFullscreen.isSelected()));
		
		graphicsMode = new JComboBox<String>(new String[] {"2D", "3D"});
		graphicsMode.setBounds(10, 70, 100, 20);
		add(graphicsMode);
		graphicsMode.addActionListener((ActionEvent e) -> ServiceManager.getService().setIs3D(graphicsMode.getSelectedIndex() == 1));
		
		drawTiles = new JCheckBox("Draw tiles");
		drawTiles.setSelected(true);
		drawTiles.setBounds(10, 100, 300, 20);
		add(drawTiles);
		drawTiles.addActionListener((ActionEvent e) -> ServiceManager.getService().setTilesEnabled(drawTiles.isSelected()));
		
		drawLines = new JCheckBox("Draw lines");
		drawLines.setSelected(true);
		drawLines.setBounds(10, 130, 300, 20);
		add(drawLines);
		drawLines.addActionListener((ActionEvent e) -> ServiceManager.getService().setLinesEnabled(drawLines.isSelected()));

		drawMode = new JComboBox<DrawMode>(DrawMode.values());
		drawMode.setSelectedItem(DrawMode.LINES_WITH_FILL);
		drawMode.setBounds(10, 160, 200, 20);
		add(drawMode);
		drawMode.addActionListener((ActionEvent e) -> ServiceManager.getService().setDrawMode((DrawMode) drawMode.getSelectedItem()));
		
		scale3d = new JSpinner(new SpinnerNumberModel(0.25, 0.0625, 2, 0.125));
		scale3d.setBounds(10, 190, 200, 20);
		add(scale3d);
		scale3d.getModel().addChangeListener((ChangeEvent e) -> {
			FunctionManager.setDelta3D(Double.parseDouble(scale3d.getValue().toString()));
		});
		
	}

}
