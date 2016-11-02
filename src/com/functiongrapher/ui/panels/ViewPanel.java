package com.functiongrapher.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.functiongrapher.service.ServiceManager;
import com.functiongrapher.ui.windows.GraphWindow;

public class ViewPanel extends JPanel {

	private static final long serialVersionUID = -5999370491437275102L;

	private JCheckBox enableControl;
	private JSpinner yawSpeed;

	private JCheckBox runFullscreen;
	
	private JComboBox<String> graphicsMode;

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

	}

}
