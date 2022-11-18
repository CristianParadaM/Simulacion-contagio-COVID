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
			this.manager = new Manager(
					Integer.parseInt(view.getText1()), 
					Integer.parseInt(view.getText2()), 
					Integer.parseInt(view.getText3()), 
					Integer.parseInt(view.getText4()), 
					Integer.parseInt(view.getText5()), 
					Integer.parseInt(view.getText6()));
			
			try {
			int count = 0;
			Object[] data = null;
			
			for (int i = 0; i < 365; i++) {
//				Object[] arrays = separateArrays();
				
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
