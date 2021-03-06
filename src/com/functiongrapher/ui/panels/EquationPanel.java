package com.functiongrapher.ui.panels;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.functiongrapher.function.FunctionManager;
import com.functiongrapher.main.ProgramInfo;
import com.functiongrapher.ui.EquationEditor;

public class EquationPanel extends JPanel {

	private static final long serialVersionUID = 5645891227167691083L;

	private JList<EquationEditor> equationList;
	private JButton addEquation;
	private JButton delEquation;

	private JScrollPane listScroller;

	private DefaultListModel<EquationEditor> elist = new DefaultListModel<EquationEditor>();

	public EquationPanel() {
		setLayout(null);

		equationList = new JList<EquationEditor>(elist);
		equationList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				for (int i = 0; i < elist.size(); i++) {
					if (elist.get(i).equals(equationList.getSelectedValue())) {
						elist.get(i).setVisible(true);
					} else {
						elist.get(i).setVisible(false);
					}
				}
			}
		});
		listScroller = new JScrollPane(equationList);
		listScroller.setBounds(10, 10, 150, 390);
		add(listScroller);

		addEquation = new JButton("Add");
		addEquation.setBounds(10, 410, 70, ProgramInfo.DEFAULTCOMPONENTHEIGHT);
		add(addEquation);
		addEquation.addActionListener((ActionEvent e) -> {
			EquationEditor ee = new EquationEditor("New Equation");
			ee.setVisible(false);
			ee.setBounds(170, 10, 300, 440);
			ee.setRenameListener(new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent arg0) {
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					equationList.repaint();
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					equationList.repaint();
				}
			});
			FunctionManager.addFunction(ee);
		});

		delEquation = new JButton("Delete");
		delEquation.setBounds(89, 410, 70, ProgramInfo.DEFAULTCOMPONENTHEIGHT);
		add(delEquation);
		delEquation.addActionListener((ActionEvent e) -> {
			if (equationList.getSelectedValue() == null) {
				return;
			}
			FunctionManager.removeFunction(equationList.getSelectedValue());
			EquationPanel.this.repaint();
		});

	}
	
	public void updateList() {
		for (Component c : getComponents()) {
			if (c instanceof EquationEditor) {
				remove(c);
				((EquationEditor) c).removeRenameListener();
			}
		}
		elist.clear();
		
		for (EquationEditor ee : FunctionManager.getFunctions()) {
			elist.addElement(ee);
			ee.setVisible(false);
			ee.setBounds(170, 10, 300, 440);
			ee.setRenameListener(new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent arg0) {
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					equationList.repaint();
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					equationList.repaint();
				}
			});
			add(ee);
		}
	}

}
