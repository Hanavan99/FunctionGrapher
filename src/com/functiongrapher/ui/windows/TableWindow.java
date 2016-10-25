package com.functiongrapher.ui.windows;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import com.functiongrapher.main.ProgramInfo;
import com.functiongrapher.ui.EquationEditor;

public class TableWindow extends JFrame {

	private static final long serialVersionUID = 1324911346245780626L;
	
	private JComboBox<EquationEditor> equations = new JComboBox<EquationEditor>();
	
	public TableWindow() {
		setTitle(ProgramInfo.WINDOW_TABLE_NAME);
		setBounds(150, 150, 300, 600);
		setResizable(false);
		setVisible(true);
	}

}
