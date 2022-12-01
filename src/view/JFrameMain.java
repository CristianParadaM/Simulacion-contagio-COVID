package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class JFrameMain extends JFrame implements ActionListener {

	private JPanelMain jPanelMain;
	private static JFrameMain frameMain = null;
	public static final String FONT = "Arial";
	private static JDialog dialogBarCharge;
	private static JDialog dialogPercentages;
	private static JProgressBar bar;

	public static JFrameMain getInstance() {
		if (frameMain == null) {
			frameMain = new JFrameMain();
		}
		return frameMain;
	}

	private JFrameMain() {
		super();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "start":
			jPanelMain.changeView(1);
			break;
		case "back":
			jPanelMain.changeView(0);
		}
	}

	public void init() {
		this.jPanelMain = new JPanelMain();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1500, 950);
		this.setLocationRelativeTo(null);
		this.setContentPane(jPanelMain);
		this.setVisible(true);
	}

	public static void createProgress(int min, int max, String title) {
		bar = new JProgressBar(min, max);
		dialogBarCharge = new JDialog(JFrameMain.getInstance(), "Cargando...");
		dialogBarCharge.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialogBarCharge.setSize(new Dimension(300, 120));
		dialogBarCharge.setLocationRelativeTo(null);
		dialogBarCharge.setLayout(null);
		dialogBarCharge.add(new JLabel(title, JLabel.CENTER)).setBounds(5, 10, 275, 30);
		dialogBarCharge.add(bar).setBounds(5, 40, 275, 20);
		dialogBarCharge.setVisible(true);
	}

	public static void createPercentages(String title, double[] percentages) {
		DecimalFormat format = new DecimalFormat("#.##");
		dialogPercentages = new JDialog(JFrameMain.getInstance(), "Porcentajes");
		dialogPercentages.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialogPercentages.setSize(new Dimension(300, 200));
		dialogPercentages.setLocationRelativeTo(null);
		dialogPercentages.setLayout(null);
		JLabel titlel = new JLabel(title, JLabel.CENTER);
		titlel.setForeground(Color.RED);
		dialogPercentages.add(titlel).setBounds(5, 10, 275, 30);
		dialogPercentages.add(new JLabel("Personas sanas " + format.format(percentages[0]) + "%", JLabel.CENTER))
				.setBounds(5, 50, 275, 30);
		dialogPercentages.add(new JLabel("Personas enfermas " + format.format(percentages[1]) + "%", JLabel.CENTER))
				.setBounds(5, 90, 275, 30);
		dialogPercentages.add(new JLabel("Personas recuperadas " + format.format(percentages[2]) + "%", JLabel.CENTER))
				.setBounds(5, 130, 275, 30);
		dialogPercentages.setVisible(true);
	}

	public static void setProgressBar(int value) {
		bar.setValue(value);
	}

	public static void disposeDialog() {
		dialogBarCharge.setVisible(false);
	}

	public String getText1() {
		return jPanelMain.getText1();
	}

	public String getText2() {
		return jPanelMain.getText2();
	}

	public String getText3() {
		return jPanelMain.getText3();
	}

	public String getText4() {
		return jPanelMain.getText4();
	}

	public String getText5() {
		return jPanelMain.getText5();
	}

	public String getText6() {
		return jPanelMain.getText6();
	}

	public void updateChart(Object... data) {
		jPanelMain.updateChart(data);
	}

	public void changeView(int i, Object[] data) {
		jPanelMain.changeView(i, data);
	}

	public void addmoresteps(Object[] data) {
		jPanelMain.addmoresteps(data);
	}

}
