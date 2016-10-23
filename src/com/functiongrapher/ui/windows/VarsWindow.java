package com.functiongrapher.ui.windows;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.functiongrapher.main.ProgramInfo;
import com.functiongrapher.ui.panels.EquationPanel;
import com.functiongrapher.ui.panels.GraphicsPanel;
import com.functiongrapher.ui.panels.IOPanel;
import com.functiongrapher.ui.panels.MainPanel;
import com.functiongrapher.ui.panels.ViewPanel;
import com.functiongrapher.ui.panels.WindowPanel;
import com.functiongrapher.util.ScreenResolution;

public class VarsWindow extends JFrame {

	private static final long serialVersionUID = 651724490657554571L;

	private JTabbedPane pane;
	private MainPanel mainPanel;
	private EquationPanel equationPanel;
	private WindowPanel windowPanel;
	private ViewPanel viewPanel;
	private GraphicsPanel graphicsPanel;
	private IOPanel ioPanel;
	
	public VarsWindow() {
		setTitle(ProgramInfo.WINDOW_VARS_NAME);
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
		
		ioPanel = new IOPanel();
		pane.addTab("Save/Load", null, ioPanel, "Save and load images and preset files");

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
	
	public EquationPanel getEquationPanel() {
		return equationPanel;
	}
	
}
