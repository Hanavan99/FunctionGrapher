package com.functiongrapher.ui;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

public class EquationPanel extends JPanel {

	private static final long serialVersionUID = 5645891227167691083L;
	
	private JList<Object> equationlist;
	private JButton addEquation;
	private JButton delEquation;
	
	public EquationPanel() {
		setLayout(null);
		
		equationlist = new JList<Object>(new Object[] {});
		equationlist.setBounds(10, 10, 150, 390);
		add(equationlist);
		
		addEquation = new JButton("Add");
		addEquation.setBounds(10, 410, 70, 20);
		add(addEquation);
		
		delEquation = new JButton("Delete");
		delEquation.setBounds(90, 410, 70, 20);
		add(delEquation);
		
	}

}
