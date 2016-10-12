package com.functiongrapher.ui;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class VarsWindow extends JFrame {

	private static final long serialVersionUID = 651724490657554571L;

	private JTabbedPane pane;
	private MainPanel mainPanel;
	private EquationPanel equationPanel;
	private ViewPanel viewPanel;

	public VarsWindow() {
		setTitle("Vars");
		setBounds(100, 100, 500, 500);
		setResizable(false);
		setVisible(true);

		try {
			InputStream s = GraphWindow.class.getResourceAsStream("/assets/images/fgicon64.png");
			BufferedImage i = ImageIO.read(s);
			setIconImage(i);
		} catch (Exception e) {

		}

		pane = new JTabbedPane();
		add(pane);

		mainPanel = new MainPanel();
		pane.addTab("General", null, mainPanel, "General options for FunctionGrapher");

		equationPanel = new EquationPanel();
		pane.addTab("Equations", null, equationPanel, "Adding and removing equations and options for them");

		viewPanel = new ViewPanel();
		pane.addTab("View", null, viewPanel, "Changing how the view looks");

	}

}
