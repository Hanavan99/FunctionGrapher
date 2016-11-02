package com.functiongrapher.ui.windows;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.functiongrapher.main.ProgramInfo;

public class TableWindow extends JFrame {

	private static final long serialVersionUID = 1324911346245780626L;
	
	private JTable table;
	private DefaultTableModel model;
	
	public TableWindow() {
		setTitle(ProgramInfo.WINDOW_TABLE_NAME);
		setBounds(150, 150, 300, 600);
		setResizable(false);
		setVisible(true);
		
		model = new DefaultTableModel(new Object[][] {{"asdf"}}, new Object[] {"col1", "col2"});
		table = new JTable(model);
		add(table);
	}

}
