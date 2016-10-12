package com.functiongrapher.ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import com.functiongrapher.function.FunctionManager;

public class WindowPanel extends JPanel {

	private static final long serialVersionUID = 3290825267237263413L;
	
	private JLabel lXmin;
	private JLabel lXmax;
	private JLabel lYmin;
	private JLabel lYmax;
	private JLabel lGridStepX2D;
	private JLabel lGridStepY2D;
	
	private JSpinner sXmin;
	private JSpinner sXmax;
	private JSpinner sYmin;
	private JSpinner sYmax;
	
	private JSpinner sGridStepX2D;
	private JSpinner sGridStepY2D;
	
	private JComboBox<String> dDelta2D;
	private JComboBox<String> dDelta3D;
	
	public WindowPanel() {
		setLayout(null);
		
		lXmin = new JLabel("XMin:");
		lXmin.setBounds(10, 10, 50, 20);
		add(lXmin);
		
		lXmax = new JLabel("XMax:");
		lXmax.setBounds(10, 40, 50, 20);
		add(lXmax);
		
		lYmin = new JLabel("YMin:");
		lYmin.setBounds(10, 70, 50, 20);
		add(lYmin);
		
		lYmax = new JLabel("YMax:");
		lYmax.setBounds(10, 100, 50, 20);
		add(lYmax);
		
		sXmin = new JSpinner(new SpinnerNumberModel(-10.0d, -100.0d, 100.0d, 1.0d));
		sXmin.setBounds(60, 10, 50, 20);
		add(sXmin);
		sXmin.addChangeListener((ChangeEvent e) -> FunctionManager.setXmin((double) sXmin.getValue()));
		
		sXmax = new JSpinner(new SpinnerNumberModel(10.0d, -100.0d, 100.0d, 1.0d));
		sXmax.setBounds(60, 40, 50, 20);
		add(sXmax);
		sXmax.addChangeListener((ChangeEvent e) -> FunctionManager.setXmax((double) sXmax.getValue()));
		
		sYmin = new JSpinner(new SpinnerNumberModel(-10.0d, -100.0d, 100.0d, 1.0d));
		sYmin.setBounds(60, 70, 50, 20);
		add(sYmin);
		sYmin.addChangeListener((ChangeEvent e) -> FunctionManager.setYmin((double) sYmin.getValue()));
		
		sYmax = new JSpinner(new SpinnerNumberModel(10.0d, -100.0d, 100.0d, 1.0d));
		sYmax.setBounds(60, 100, 50, 20);
		add(sYmax);
		sYmax.addChangeListener((ChangeEvent e) -> FunctionManager.setYmax((double) sYmax.getValue()));
		
		lGridStepX2D = new JLabel("Grid step X:");
		lGridStepX2D.setBounds(120, 10, 60, 20);
		add(lGridStepX2D);
		
		lGridStepY2D = new JLabel("Grid step Y:");
		lGridStepY2D.setBounds(120, 40, 60, 20);
		add(lGridStepY2D);
		
		sGridStepX2D = new JSpinner(new SpinnerNumberModel(1.0d, 0.125d, 1000.0d, 0.25d));
		sGridStepX2D.setBounds(180, 10, 50, 20);
		add(sGridStepX2D);
		sGridStepX2D.addChangeListener((ChangeEvent e) -> FunctionManager.setGridStepX2D((double) sGridStepX2D.getValue()));
		
		sGridStepY2D = new JSpinner(new SpinnerNumberModel(1.0d, 0.125d, 1000.0d, 0.25d));
		sGridStepY2D.setBounds(180, 40, 50, 20);
		add(sGridStepY2D);
		sGridStepY2D.addChangeListener((ChangeEvent e) -> FunctionManager.setGridStepY2D((double) sGridStepY2D.getValue()));
		
	}

}
