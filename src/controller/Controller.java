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
		
		case "back" -> showStart();
		}
	}
	
	//corregir
	private void showStart() {
		view.changeView(0, null);
	}

	private void showPlots() {
		double[][] randwalk = new double[4][3];
		double[][] analistics = new double[4][3];
		
		new Thread(() -> {
			try {
			int count = 0;
			Object[] data = null;
			
			while (count++ != 300) {
				
//					data = new Object[] { new Object[] { 
//							new double[][] { { aux11 }, { aux12++ } },
//							new double[][] { { aux21++ }, { aux22} },
//							new double[][] { { aux31 }, { aux32++ } },
//							new double[][] { { aux41++ }, { aux42 } } }, 
//							new Object[] {
//									testExp(count),
//									testExp(count+1),
//									testExp(count+2),
//									testExp(count+3)
//					}};
				
					for (int i = 0; i < randwalk.length; i++) {
						
					}
					
					//0 enfermo con mascara
					//1 enfermo sin mascara
					//2 normal con mascara
					//3 normal sin mascara
					//4 recuperado
					if (count == 1) {
						view.changeView(1, data);
					} else {
						view.updateChart(data);
					}
					Thread.sleep(300);
				}
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
