package com.functiongrapher.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.lwjgl.opengl.GL11;

import com.functiongrapher.function.Function;

public class EquationEditor extends JPanel {

	private static final long serialVersionUID = -3965856223096455581L;

	private JLabel nameLabel;
	private JTextField nameText;
	private JButton editColor;
	private JLabel bodyLabel;
	private JTextArea bodyText;

	private Color equationColor = Color.BLACK;
	private Function function;
	private ScriptEngine engine;

	public EquationEditor(String defaultName) {
		setLayout(null);

		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("JavaScript");

		function = new Function() {
			@Override
			public double evalPoint(double x, double y, double t) {
				try {
					return Double.valueOf(((Invocable) engine).invokeFunction("sum", new Object[] { x, y, t }).toString()).doubleValue();
				} catch (NumberFormatException | NoSuchMethodException | javax.script.ScriptException | NullPointerException e) {
					return 0.0d;
				}
			}

			@Override
			public int getDrawMode() {
				return GL11.GL_LINES;
			}

			@Override
			public double[] getGraphColor() {
				return new double[] { (double) equationColor.getRed() / 255, (double) equationColor.getGreen() / 255, (double) equationColor.getBlue() / 255 };
			}
		};

		nameLabel = new JLabel("Name:");
		nameLabel.setBounds(10, 10, 50, 20);
		add(nameLabel);

		nameText = new JTextField(defaultName);
		nameText.setBounds(60, 10, 240, 20);
		add(nameText);

		editColor = new JButton("Change Graph Color...");
		editColor.setBounds(10, 40, 175, 20);
		add(editColor);
		editColor.addActionListener((ActionEvent e) -> {
			equationColor = JColorChooser.showDialog(this, "Graph Color", equationColor);
			if (equationColor == null) {
				equationColor = Color.BLACK;
			}
			System.out.println(equationColor.getRed());
			System.out.println(equationColor.getGreen());
			System.out.println(equationColor.getBlue());
		});

		bodyLabel = new JLabel("Equation:");
		bodyLabel.setBounds(10, 70, 100, 20);
		add(bodyLabel);

		bodyText = new JTextArea();
		bodyText.setBounds(10, 100, 330, 310);
		add(bodyText);
		bodyText.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				updateEvalStatement();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				updateEvalStatement();
			}

		});
	}

	public void updateEvalStatement() {
		try {
			engine.eval("function sum(x, y, t) { return " + bodyText.getText() + ";}");
		} catch (javax.script.ScriptException e) {
			e.printStackTrace();
		}
	}

	public Function getFunction() {
		return function;
	}

	@Override
	public String toString() {
		return nameText.getText();
	}

}
