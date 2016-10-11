package com.functiongrapher.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.functiongrapher.function.Function;

public class EquationEditor extends JPanel {

	private static final long serialVersionUID = -3965856223096455581L;
	
	private JLabel nameLabel;
	private JTextField nameText;
	private JButton editColor;
	private JLabel bodyLabel;
	private JTextArea bodyText;
	
	private Color equationColor;
	private Function function;
	
	public EquationEditor(String defaultName) {
		
		nameLabel = new JLabel("Name:");
		nameLabel.setBounds(10, 10, 50, 20);
		add(nameLabel);
		
		nameText = new JTextField(defaultName);
		nameText.setBounds(60, 10, 200, 20);
		add(nameText);
		
		editColor = new JButton("Change Graph Color...");
		editColor.setBounds(10, 40, 150, 20);
		add(editColor);
		editColor.addActionListener((ActionEvent e) -> {
			equationColor = JColorChooser.showDialog(this, "Graph Color", equationColor);
			if (equationColor == null) {
				equationColor = Color.BLACK;
			}
		});
		
		bodyLabel = new JLabel("Equation:");
		bodyLabel.setBounds(10, 70, 100, 20);
		add(bodyLabel);
		
	}

}
