package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import controller.Controller;

public class JPanelMain extends JPanel {

	private JScrollPane jScrollPane;
	private JPanelStart jPanelStart;
	private JPanel jPanelContainer;
	private JLabel jLabelChartPoints;
	private JLabel jLabelChartExp;
	private JLabel jLabel1;
	private JButton btnBack;

	public JPanelMain() {
		super(new GridLayout());
		this.jPanelContainer = new JPanel();
		this.jScrollPane = new JScrollPane();
		this.jPanelStart = new JPanelStart();
		this.jLabelChartPoints = new JLabel();
		this.jLabelChartExp = new JLabel();
		this.btnBack = new JButton("       regresar        ");
		this.jLabel1 = new JLabel("", JLabel.CENTER);
		init();
	}

	private void init() {
		this.setOpaque(false);
		this.jScrollPane.setOpaque(false);
		this.jScrollPane.getViewport().setOpaque(false);
		this.jPanelContainer.setOpaque(false);
		this.jPanelContainer.setLayout(null);
		this.jPanelContainer.setPreferredSize(new Dimension(1400, 1700));
		this.jScrollPane.setViewportView(jPanelContainer);
		this.jLabelChartPoints.setHorizontalAlignment(JLabel.CENTER);
		this.jLabelChartExp.setHorizontalAlignment(JLabel.CENTER);
		this.btnBack.setOpaque(false);
		configureB(btnBack, JFrameMain.FONT, 20, Font.PLAIN);
		this.jLabel1.setOpaque(false);
		configureLabels(jLabel1, JFrameMain.FONT, 20, Font.PLAIN, false);
		addComponentsPanel();
		changeView(0);
	}

	private void addComponentsPanel() {
		this.jPanelContainer.add(jLabelChartPoints).setBounds(30, 10, 1400, 800);
		this.jPanelContainer.add(jLabel1).setBounds(30, 600, 1400, 350);
		this.jPanelContainer.add(jLabelChartExp).setBounds(30, 800, 1400, 800);
	}

	public void changeView(int index, Object... data) {
		removeComponents();
		switch (index) {
		case 0:
			this.jScrollPane.setViewportView(jPanelStart);
			this.jScrollPane.setVisible(true);
			this.add(jScrollPane);
			break;
		case 1:
			this.jScrollPane.setVisible(true);
			this.jScrollPane.setViewportView(jPanelContainer);
			addCharts(data);
			this.add(jScrollPane);
			break;

		}
		this.updateUI();
	}

	public void updateChart(Object... data) {
		addCharts(data);
	}

	private void addCharts(Object[] data) {
		chartPoints(data);
		updateLabel(data);
		chartExp(data);
	}

	private void updateLabel(Object[] data) {
		this.jLabel1.setText(" Numero de Sanos = " + ((int[]) data[2])[0] + " " + " 	Numero de Contagiados = "
				+ ((int[]) data[2])[1] + " " + " 	Numero de recuperados = " + ((int[]) data[2])[2] + " ");
	}

	private void chartExp(Object[] data) {
		DefaultXYDataset dataset = new DefaultXYDataset();
		filldataSetExp(dataset, (Object[]) data[1]);
		JFreeChart chart = ChartFactory.createXYLineChart("", "", "", dataset);
		XYPlot plot = chart.getXYPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(0, ((int[]) data[2])[1] + ((int[]) data[2])[0] + ((int[]) data[2])[2] + 50);
		NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		domainAxis.setRange(0, 365);
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(new Color(200, 200, 200));
		plot.setDomainGridlinePaint(new Color(200, 200, 200));
		AbstractRenderer a = (AbstractRenderer) plot.getRenderer(0);
		a.setSeriesOutlineStroke(0, new BasicStroke(3));
		a.setSeriesOutlineStroke(1, new BasicStroke(3));
		a.setSeriesOutlineStroke(2, new BasicStroke(3));
		a.setSeriesOutlineStroke(3, new BasicStroke(3));
		a.setSeriesPaint(0, new Color(240, 149, 8));
		a.setSeriesPaint(1, Color.RED);
		a.setSeriesPaint(2, new Color(26, 133, 254));
		a.setSeriesPaint(3, new Color(23, 187, 4));
		jLabelChartExp.setIcon(new ImageIcon(chart.createBufferedImage(1300, 700)));
	}

	private void filldataSetExp(DefaultXYDataset dataset, Object[] object) {
		double[][] s1 = (double[][]) object[0];
		double[][] s2 = (double[][]) object[1];
		double[][] s3 = (double[][]) object[2];
		dataset.addSeries("Personas Sanas", s1);
		dataset.addSeries("Personas Enfermas", s2);
		dataset.addSeries("Personas Recuperadas", s3);
	}

	private void chartPoints(Object[] data) {
		DefaultXYDataset dataset = new DefaultXYDataset();
		filldataSet(dataset, (Object[]) data[0]);
		JFreeChart chart = ChartFactory.createScatterPlot("", "", "", dataset);
		XYPlot plot = chart.getXYPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(0, 100);
		NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		domainAxis.setRange(0, 100);
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(new Color(200, 200, 200));
		plot.setDomainGridlinePaint(new Color(200, 200, 200));
		AbstractRenderer a = (AbstractRenderer) plot.getRenderer(0);
		a.setSeriesOutlineStroke(0, new BasicStroke(3));
		a.setSeriesOutlineStroke(1, new BasicStroke(3));
		a.setSeriesOutlineStroke(2, new BasicStroke(3));
		a.setSeriesOutlineStroke(3, new BasicStroke(3));
		a.setSeriesPaint(0, new Color(240, 149, 8));
		a.setSeriesPaint(1, Color.RED);
		a.setSeriesPaint(2, new Color(26, 133, 254));
		a.setSeriesPaint(3, new Color(23, 187, 4));
		jLabelChartPoints.setIcon(new ImageIcon(chart.createBufferedImage(1300, 700)));
	}

	private void filldataSet(DefaultXYDataset dataset, Object[] object) {
		double[][] s1 = (double[][]) object[0];
		double[][] s2 = (double[][]) object[1];
		double[][] s3 = (double[][]) object[2];
		double[][] s4 = (double[][]) object[3];
		double[][] s5 = (double[][]) object[4];
		dataset.addSeries("Enfermos con Tapabocas", s1);
		dataset.addSeries("Enfermos Sin Tapabocas", s2);
		dataset.addSeries("Sanos con Tapabocas", s3);
		dataset.addSeries("Sanos Sin Tapabocas", s4);
		dataset.addSeries("Recuperados", s5);
	}

	private void removeComponents() {
		for (int i = 0; i < this.getComponentCount(); i++) {
			this.getComponent(i).setVisible(false);
		}
		this.removeAll();
	}

	private void configureB(JButton jButton, String font, int size, int style) {
		jButton.setFont(new Font(font, style, size));
		jButton.setForeground(Color.WHITE);
		jButton.setBorder(new LineBorder(Color.WHITE));
		jButton.setContentAreaFilled(false);
		jButton.setFocusPainted(false);
		jButton.addActionListener(Controller.getInstance());
		jButton.setActionCommand("back");
	}

	private void configureLabels(JLabel jLabel, String fontApp, int fontSize, int style, boolean border) {
		jLabel.setFont(new Font(fontApp, style, fontSize));
		jLabel.setForeground(Color.WHITE);
		jLabel.setPreferredSize(new Dimension(0, 60));
		if (border) {
			jLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.WHITE));
		}

	}

	public String getText1() {
		return jPanelStart.getText1();
	}

	public String getText2() {
		return jPanelStart.getText2();
	}

	public String getText3() {
		return jPanelStart.getText3();
	}

	public String getText4() {
		return jPanelStart.getText4();
	}

	public String getText5() {
		return jPanelStart.getText4();
	}

	public String getText6() {
		return jPanelStart.getText4();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paint(g);
	}
}
