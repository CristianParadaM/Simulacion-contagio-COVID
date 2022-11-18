package view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class JFrameMain extends JFrame implements ActionListener {

	private JPanelMain jPanelMain;
	private static JFrameMain frameMain = null;
	public static final String FONT = "Arial";
	private static JDialog dialog;
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
		dialog = new JDialog(JFrameMain.getInstance(), "Cargando...");
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setSize(new Dimension(300, 120));
		dialog.setLocationRelativeTo(null);
		dialog.setLayout(null);
		dialog.add(new JLabel(title, JLabel.CENTER)).setBounds(5, 10, 275, 30);
		dialog.add(bar).setBounds(5, 40, 275, 20);
		dialog.setVisible(true);
	}
	
	public static void setProgressBar(int value) {
		bar.setValue(value);
	}

	public static void disposeDialog() {
		dialog.setVisible(false);
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

}
