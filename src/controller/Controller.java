package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.JFrameMain;

public class Controller implements ActionListener {

	private static Controller controller = null;
	private JFrameMain view;

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
		}
	}
	
	

	private void showPlots() {
		new Thread(() -> {
			
			int aux11 = 0;
			int aux12 = 10;
			int aux21 = 10;
			int aux22 = 10;
			int aux31 = 10;
			int aux32 = 10;
			int aux41 = 20;
			int aux42 = 10;
			
			int count = 0;
			Object[] data = null;
			while (count++ != 300) {
				
				if (aux11==50) {
					data = new Object[] { new Object[] { 
							new double[][] { { aux11 }, { aux12++ } },
							new double[][] { { aux21++ }, { aux22} },
							new double[][] { { aux31 }, { aux32++ } },
							new double[][] { { aux41++ }, { aux42 } } }, new Object[] {
									testExp(count),
									testExp(count+1),
									testExp(count+2),
									testExp(count+3)
							}};
					
				}else {
					data = new Object[] { new Object[] { 
							new double[][] { { aux11++ }, { aux12 } },
							new double[][] { { aux21 }, { aux22++ } },
							new double[][] { { aux31++ }, { aux32 } },
							new double[][] { { aux41 }, { aux42++ } } }, new Object[] {
									testExp(count),
									testExp(count+1),
									testExp(count+2),
									testExp(count+3)
							} };
				}
				if (count == 1) {
					view.changeView(1, data);
				} else {
					view.updateChart(data);
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}
			}
		}).start();
	}
	
	private double[][] testExp(int index) {
		double[][] aux = new double[2][index];
		int x = 100;
		if (index > 100) {
			for (int i = 99; i >= 0; x++, i--) {
				aux[0][i] = x;
				aux[1][i] = i;
			}
		}else {
			for (int i = 0; i < index; i++) {
				aux[0][i] = i;
				aux[1][i] = i;
			}
		}
		return aux;
	}

	public void startApp() {
		view = JFrameMain.getInstance();
		view.init();
	}

	public static void main(String[] args) {
		Controller.getInstance().startApp();
	}

}
