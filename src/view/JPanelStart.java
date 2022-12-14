package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controller.Controller;

public class JPanelStart extends JPanel {

	private GridBagConstraints gbc;
	private JLabel jLabelTitle;
	private JLabel jLabelOption1;
	private JLabel jLabelOption2;
	private JLabel jLabelOption3;
	private JLabel jLabelOption4;
	private JLabel jLabelOption5;
	private JLabel jLabelOption6;

	private JTextField jTextField1;
	private JTextField jTextField2;
	private JTextField jTextField3;
	private JTextField jTextField4;
	private JTextField jTextField5;
	private JTextField jTextField6;

	private JButton jButtonStart;

	public JPanelStart() {
		super(new GridBagLayout());
		this.gbc = new GridBagConstraints();
		this.jLabelTitle = new JLabel("Simulaci?n de contagios de COVID 19", JLabel.CENTER);
		this.jLabelOption1 = new JLabel("N?mero de personas enfermas con tapabocas", JLabel.CENTER);
		this.jLabelOption2 = new JLabel("N?mero de personas enfermas sin tapabocas", JLabel.CENTER);
		this.jLabelOption3 = new JLabel("N?mero de personas sanas con tapabocas", JLabel.CENTER);
		this.jLabelOption4 = new JLabel("N?mero de personas sanas sin tapabocas", JLabel.CENTER);
		this.jLabelOption5 = new JLabel("N?mero K Par menor a 32.000", JLabel.CENTER);
		this.jLabelOption6 = new JLabel("N?mero C Impar menor a 32.000 ", JLabel.CENTER);
		this.jTextField1 = new JTextField();
		this.jTextField2 = new JTextField();
		this.jTextField3 = new JTextField();
		this.jTextField4 = new JTextField();
		this.jTextField5 = new JTextField();
		this.jTextField6 = new JTextField();
		this.jButtonStart = new JButton("       iniciar        ");
		init();
	}

	private void init() {
		this.setOpaque(false);
		configureLabels(jLabelTitle, JFrameMain.FONT, 60, Font.BOLD);
		configureLabels(jLabelOption1, JFrameMain.FONT, 30, Font.PLAIN);
		configureLabels(jLabelOption2, JFrameMain.FONT, 30, Font.PLAIN);
		configureLabels(jLabelOption3, JFrameMain.FONT, 30, Font.PLAIN);
		configureLabels(jLabelOption4, JFrameMain.FONT, 30, Font.PLAIN);
		configureLabels(jLabelOption5, JFrameMain.FONT, 30, Font.PLAIN);
		configureLabels(jLabelOption6, JFrameMain.FONT, 30, Font.PLAIN);

		configureText(jTextField1, JFrameMain.FONT, 30, Font.PLAIN);
		configureText(jTextField2, JFrameMain.FONT, 30, Font.PLAIN);
		configureText(jTextField3, JFrameMain.FONT, 30, Font.PLAIN);
		configureText(jTextField4, JFrameMain.FONT, 30, Font.PLAIN);
		configureText(jTextField5, JFrameMain.FONT, 30, Font.PLAIN);
		configureText(jTextField6, JFrameMain.FONT, 30, Font.PLAIN);

		configureB(jButtonStart, JFrameMain.FONT, 20, Font.PLAIN);

		addComponents();
	}

	private void addComponents() {
		gbc.fill = 1;
		gbc.weightx = 1;
		int margin = 40;
		int marginr = 300;
		gbc.insets.top = 10;
		this.add(jLabelTitle, gbc);
		gbc.gridy = 1;
		gbc.insets.top = 90;
		gbc.insets.left = marginr;
		gbc.insets.right = marginr;
		this.add(jLabelOption1, gbc);
		gbc.gridy = 2;
		gbc.insets.top = 0;
		this.add(jTextField1, gbc);
		gbc.gridy = 3;
		gbc.insets.top = margin;
		this.add(jLabelOption2, gbc);
		gbc.gridy = 4;
		gbc.insets.top = 0;
		this.add(jTextField2, gbc);
		gbc.gridy = 5;
		gbc.insets.top = margin;
		this.add(jLabelOption3, gbc);
		gbc.gridy = 6;
		gbc.insets.top = 0;
		this.add(jTextField3, gbc);
		gbc.gridy = 7;
		gbc.insets.top = margin;
		this.add(jLabelOption4, gbc);
		gbc.gridy = 8;
		gbc.insets.top = 0;
		this.add(jTextField4, gbc);
		gbc.gridy = 9;
		gbc.insets.top = margin;
		this.add(jLabelOption5, gbc);
		gbc.gridy = 10;
		gbc.insets.top = 0;
		this.add(jTextField5, gbc);
		gbc.gridy = 11;
		gbc.insets.top = margin;
		this.add(jLabelOption6, gbc);
		gbc.gridy = 12;
		gbc.insets.top = 0;
		this.add(jTextField6, gbc);
		gbc.insets.top = margin;
		gbc.gridy = 13;
		gbc.fill = 0;
		this.add(jButtonStart, gbc);
		gbc.gridy = 14;
		gbc.weighty = 1;
		this.add(Box.createRigidArea(new Dimension(0, 0)), gbc);
	}

	private void configureLabels(JLabel jLabel, String font, int size, int style) {
		jLabel.setFont(new Font(font, style, size));
		jLabel.setForeground(Color.WHITE);
	}

	private void configureText(JTextField jTextField, String font, int size, int style) {
		jTextField.setFont(new Font(font, style, size));
		jTextField.setForeground(Color.WHITE);
		jTextField.setBorder(new LineBorder(Color.WHITE));
		jTextField.setOpaque(false);
	}

	private void configureB(JButton jButton, String font, int size, int style) {
		jButton.setFont(new Font(font, style, size));
		jButton.setForeground(Color.WHITE);
		jButton.setBorder(new LineBorder(Color.WHITE));
		jButton.setContentAreaFilled(false);
		jButton.setFocusPainted(false);
		jButton.addActionListener(Controller.getInstance());
		jButton.setActionCommand("start");
	}
	
	public String getText1() {
		return jTextField1.getText();
	}
	public String getText2() {
		return jTextField2.getText();
	}
	public String getText3() {
		return jTextField3.getText();
	}
	public String getText4() {
		return jTextField4.getText();
	}
	public String getText5() {
		return jTextField5.getText();
	}
	public String getText6() {
		return jTextField6.getText();
	}
	
}
