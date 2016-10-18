package com.functiongrapher.ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lwjgl.glfw.GLFW;

import com.functiongrapher.main.ProgramInfo;
import com.functiongrapher.util.ScreenResolution;

public class GraphicsPanel extends JPanel {

	private static final long serialVersionUID = -9180122295382998215L;

	private JLabel monitorLabel;
	private JLabel resolutionLabel;

	private JComboBox<String> monitorSelector;
	private JComboBox<ScreenResolution> resolutionSelector;

	private long[] monitors;

	public GraphicsPanel() {
		setLayout(null);

		monitorLabel = new JLabel("Monitor:");
		monitorLabel.setBounds(10, 10, 100, 20);
		add(monitorLabel);
		
		resolutionLabel = new JLabel("Resolution:");
		resolutionLabel.setBounds(10, 40, 100, 20);
		add(resolutionLabel);

		monitorSelector = new JComboBox<String>();
		monitorSelector.setBounds(120, 10, 150, 20);
		add(monitorSelector);
		
		resolutionSelector = new JComboBox<ScreenResolution>(ProgramInfo.SUPPORTED_RESOLUTIONS);
		resolutionSelector.setBounds(120, 40, 150, 20);
		add(resolutionSelector);

	}

	public void setMonitorPointers(long[] monitors) {
		this.monitors = monitors;
		for (long l : monitors) {
			monitorSelector.addItem(GLFW.glfwGetMonitorName(l));
		}
		repaint();
	}

	public long[] getMonitorPointers() {
		return monitors;
	}

	public long getSelectedMonitor() {
		return monitors[monitorSelector.getSelectedIndex()];
	}
	
	public ScreenResolution getSelectedResolution() {
		return (ScreenResolution) resolutionSelector.getSelectedItem();
	}

}
