package com.functiongrapher.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.functiongrapher.function.Function;
import com.functiongrapher.logging.ProgramLogger;
import com.functiongrapher.main.ProgramInfo;

public class EquationEditor extends JPanel {

	private static final long serialVersionUID = -3965856223096455581L;

	private JLabel nameLabel;
	private JTextField nameText;
	private JButton editColor;
	private JLabel equationLabel;
	private JComboBox<String> equationType;
	private JLabel bodyLabel;
	private JTextArea bodyText;
	private JScrollPane bodyScroller;
	private JCheckBox evalColor;

	private Color equationColor = Color.BLACK;
	private Function function;
	private ScriptEngine engine;
	private DocumentListener dl;

	public EquationEditor(String defaultName) {
		setLayout(null);

		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("JavaScript");

		function = new Function() {
			@Override
			public double evalPoint(double x, double y, double t) {
				try {
					return Double.valueOf(((Invocable) engine).invokeFunction("evalNumber", new Object[] { x, y, t }).toString()).doubleValue();
				} catch (NumberFormatException | NoSuchMethodException | javax.script.ScriptException | NullPointerException e) {
					return 0.0d;
				}
			}

			@Override
			public double[] getGraphColor() {
				return new double[] { (double) equationColor.getRed() / 255, (double) equationColor.getGreen() / 255, (double) equationColor.getBlue() / 255 };
			}
			
			@Override
			public double getEvaluatedHue(double x, double y, double t) {
				try {
					return Double.valueOf(((Invocable) engine).invokeFunction("evalColor", new Object[] { x, y, t }).toString()).doubleValue();
				} catch (NumberFormatException | NoSuchMethodException | javax.script.ScriptException | NullPointerException e) {
					return 0.0d;
				}
			}
			
			@Override
			public boolean useMethodForColor() {
				return evalColor.isSelected();
			}
		};

		nameLabel = new JLabel("Name:");
		nameLabel.setBounds(10, 10, 50, ProgramInfo.DEFAULTCOMPONENTHEIGHT);
		add(nameLabel);

		nameText = new JTextField(defaultName);
		nameText.setBounds(60, 10, 240, ProgramInfo.DEFAULTCOMPONENTHEIGHT);
		add(nameText);

		editColor = new JButton("Change Color...");
		editColor.setBounds(10, 40, 125, ProgramInfo.DEFAULTCOMPONENTHEIGHT);
		add(editColor);
		editColor.addActionListener((ActionEvent e) -> {
			equationColor = JColorChooser.showDialog(this, "Graph Color", equationColor);
			if (equationColor == null) {
				equationColor = Color.BLACK;
			}
		});

		equationLabel = new JLabel("Mode:");
		equationLabel.setBounds(145, 40, 35, ProgramInfo.DEFAULTCOMPONENTHEIGHT);

		equationType = new JComboBox<String>(new String[] { "Single Statement (Simple)", "Function (Advanced)", "Empty (Insane)" });
		equationType.setBounds(145, 40, 154, ProgramInfo.DEFAULTCOMPONENTHEIGHT);
		add(equationType);

		bodyLabel = new JLabel("Equation: f(x, y, t)=");
		bodyLabel.setBounds(10, 70, 150, ProgramInfo.DEFAULTCOMPONENTHEIGHT);
		add(bodyLabel);

		bodyText = new JTextArea("0");
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
		bodyScroller = new JScrollPane(bodyText);
		bodyScroller.setBounds(10, 100, 290, 290);
		add(bodyScroller);
		
		evalColor = new JCheckBox("Evaluate color with getColor() method");
		evalColor.setBounds(10, 400, 300, ProgramInfo.DEFAULTCOMPONENTHEIGHT);
		add(evalColor);
	}

	public void updateEvalStatement() {
		try {
			if (equationType.getSelectedIndex() == 0) {
				engine.eval("function evalNumber(x, y, t) { return " + bodyText.getText() + ";}");
			} else if (equationType.getSelectedIndex() == 1) {
				engine.eval("function evalNumber(x, y, t) {" + bodyText.getText() + "}");
			} else {
				engine.eval(bodyText.getText());
			}
		} catch (javax.script.ScriptException e) {
			ProgramLogger.fine(e.toString());
		}
	}

	public Function getFunction() {
		return function;
	}

	public void setRenameListener(DocumentListener l) {
		dl = l;
		nameText.getDocument().addDocumentListener(l);
	}
	
	public void removeRenameListener() {
		nameText.getDocument().removeDocumentListener(dl);
	}
	
	public String getSaveData() {
		String colorname = String.valueOf(equationColor.getRed()) + "$" + String.valueOf(equationColor.getGreen()) + "$" + String.valueOf(equationColor.getBlue());
		return toString() + "$" + String.valueOf(equationType.getSelectedIndex()) + "$" + colorname + "$" + bodyText.getText().replace("\r", "%return%").replace("\n", "%newline%");
	}
	
	public void setEquationText(String text) {
		bodyText.setText(text);
	}
	
	public void setEquationType(int index) {
		equationType.setSelectedIndex(index);
	}
	
	public void setEquationColor(Color c) {
		equationColor = c;
	}

	@Override
	public String toString() {
		if (!nameText.getText().equals("")) {
			return nameText.getText().replace("$", "").replace("#", "");
		}
		return "Unnamed Equation";
	}

}
