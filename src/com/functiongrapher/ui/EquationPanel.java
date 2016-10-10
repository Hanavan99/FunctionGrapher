package com.functiongrapher.ui;

import javax.swing.JList;
import javax.swing.JPanel;

public class EquationPanel extends JPanel {

	private static final long serialVersionUID = 5645891227167691083L;
	
	private JList<String> equationlist;
	
	public EquationPanel() {
		setLayout(null);
		
		equationlist = new JList<String>();
		equationlist.setBounds(10, 10, 150, getHeight() - 20);
		add(equationlist);
		
	}

}
