package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class JFrameMain extends JFrame implements ActionListener{

	private JPanelMain jPanelMain;
	private static JFrameMain frameMain = null;
	public static final String FONT = "Arial";

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
		this.setSize(1500,900);
		this.setLocationRelativeTo(null);
		this.setContentPane(jPanelMain);
		this.setVisible(true);
	}
	
	public void updateChart(Object...data) {
		jPanelMain.updateChart(data);
	}
	
	public void changeView(int i, Object[] data) {
		jPanelMain.changeView(i, data);
	}
	
}
