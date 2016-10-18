package com.functiongrapher.ui;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.functiongrapher.main.ProgramInfo;
import com.functiongrapher.util.ScreenResolution;

public class VarsWindow extends JFrame {

	private static final long serialVersionUID = 651724490657554571L;

	private JTabbedPane pane;
	private MainPanel mainPanel;
	private EquationPanel equationPanel;
	private WindowPanel windowPanel;
	private ViewPanel viewPanel;
	private GraphicsPanel graphicsPanel;
	
	public VarsWindow() {
		setTitle("Vars");
		setBounds(100, 100, 500, 500);
		setResizable(false);
		setVisible(true);

		try {
			InputStream s = GraphWindow.class.getResourceAsStream(ProgramInfo.ICON64_PATH);
			BufferedImage i = ImageIO.read(s);
			setIconImage(i);
		} catch (Exception e) {

		}

		pane = new JTabbedPane();
		add(pane);

		mainPanel = new MainPanel();
		pane.addTab("General", null, mainPanel, "General settings for FunctionGrapher");

		equationPanel = new EquationPanel();
		pane.addTab("Equations", null, equationPanel, "Edit equations and equation options");

		windowPanel = new WindowPanel();
		pane.addTab("Window", null, windowPanel, "Edit grid bounds and detail level");

		viewPanel = new ViewPanel();
		pane.addTab("View", null, viewPanel, "Change how the view looks");
		
		graphicsPanel = new GraphicsPanel();
		pane.addTab("Graphics", null, graphicsPanel, "Change settings about the graph window");

	}
	
	public void setMonitorPointers(long[] monitors) {
		graphicsPanel.setMonitorPointers(monitors);
	}
	
	public long[] getMonitorPointers() {
		return graphicsPanel.getMonitorPointers();
	}
	
	public long getSelectedMonitor() {
		return graphicsPanel.getSelectedMonitor();
	}
	
	public ScreenResolution getSelectedResolution() {
		return graphicsPanel.getSelectedResolution();
	}
	
}
