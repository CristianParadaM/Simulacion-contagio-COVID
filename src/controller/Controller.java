package controller;

import model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.JFrameMain;

public class Controller implements ActionListener {

	private static Controller controller = null;
	private JFrameMain view;
	private Manager manager;

	public static Controller getInstance() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}

	private Controller() {
		super();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "start" -> showPlots();

		case "back" -> showStart();
		}
	}

	private void showStart() {
		view.changeView(0, null);
	}

	private void showPlots() {
		new Thread(() -> {

			int n1 = Integer.parseInt(view.getText1());
			int n2 = Integer.parseInt(view.getText2());
			int n3 = Integer.parseInt(view.getText3());
			int n4 = Integer.parseInt(view.getText4());
			int k = Integer.parseInt(view.getText5());
			int c = Integer.parseInt(view.getText6());

			this.manager = new Manager(n1, n2, n3, n4, k, c);

			try {
				Object[] data = null;
				int i = 0, x = 0;
				boolean start = true;
				do {
					Object[] arrays = manager.separateArrays(x);
					Object[] analitics = manager.separateAnalitics(i);
					int[] numberpersons = manager.calculateNumberPeopleInState(x);
					data = new Object[] {new Object[] {
						(double[][])arrays[0],
						(double[][])arrays[1],
						(double[][])arrays[2],
						(double[][])arrays[3],
						(double[][])arrays[4]
					}, new Object[] {
							(double[][])analitics[0],
							(double[][])analitics[1],
							(double[][])analitics[2]
					}, numberpersons};
					if (i == 0 && start) {
						view.changeView(1, data);
						i++;
					} else {
						view.updateChart(data);
						i++;
					}
					if ((i+1) == manager.getDays() && numberpersons[1] != 0) {
						manager.addMorePositions();
						view.addmoresteps(data);
						manager.setRange(manager.getRange()+364);
						start = false;
						x=0;
					}
					Thread.sleep(10);
				} while (manager.calculateNumberPeopleInState(x++)[1] != 0);
				JFrameMain.createPercentages("Resultados Finales", manager.getPercentages(x));
			} catch (InterruptedException e) {
			}
		}).start();
	}

	public void startApp() {
		view = JFrameMain.getInstance();
		view.init();
	}

	public static void main(String[] args) {
		Controller.getInstance().startApp();
	}

}
