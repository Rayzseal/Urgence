package hud;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class HUDStatistiques {
	private JPanel panel;
	private ChartPanel chart;

	public HUDStatistiques(String nameChart, String nameChartx, String nameCharty, Map<?,Integer> map) {
		panel = new JPanel();
		getChartPanel(setName(nameChart, nameChartx, nameCharty, chartMatiere(map)));
		setPanel();
	}
	
	private DefaultCategoryDataset chartMatiere(Map<?,Integer> map) {
		 final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		 for(Entry<?, Integer> m : map.entrySet()) {
			 dataset.addValue(m.getValue()/60, m.getKey().toString(), new Integer(0));
		 }		 
		 return dataset;               
	}
	
	private JFreeChart setName(String nameChart, String nameChartx, String nameCharty, DefaultCategoryDataset dataset) {
		JFreeChart freeChart = ChartFactory.createBarChart(nameChart, nameChartx, nameCharty, 
                dataset, PlotOrientation.VERTICAL, true, true, false);
		return freeChart;
	}
	private void getChartPanel(JFreeChart barChart) {
		chart = new ChartPanel(barChart);
	}
	
	private void setPanel() {
		
		panel.setBackground(Color.darkGray);
		panel.add(chart, BorderLayout.CENTER);

	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	

}
