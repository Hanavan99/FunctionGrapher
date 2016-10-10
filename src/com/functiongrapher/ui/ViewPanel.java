package com.functiongrapher.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ViewPanel extends JPanel {

	private static final long serialVersionUID = -5999370491437275102L;

	private JCheckBox enableControl;
	private JSpinner yawSpeed;
	
	private JCheckBox runFullscreen;

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
		
		yawSpeed = new JSpinner(new SpinnerNumberModel(0.25d, -5.0d, 5.0d, 0.25d));
		yawSpeed.setBounds(210, 10, 60, 20);
		add(yawSpeed);

		runFullscreen = new JCheckBox("Run graph window in fullscreen");
		runFullscreen.setBounds(10, 40, 210, 20);
		add(runFullscreen);
		runFullscreen.addActionListener((ActionEvent e) -> GraphWindow.setIsFullscreen(runFullscreen.isSelected()));
		
	}

}
