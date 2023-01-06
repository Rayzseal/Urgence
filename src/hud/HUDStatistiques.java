package hud;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import controller.Statistics;
import utils.Data;
import utils.Utils;

/**
 * Class to create a panel with statistics in diagram or number
 */
public class HUDStatistiques {
	private JPanel panel;
	private ChartPanel chart;
	/**
	 * constructor which call the method setResumeStatistics
	 * this method create a panel with datas from de simulation like the number of ressources, ...
	 * @param data Data
	 */
	public HUDStatistiques(Data data) {
		panel = new JPanel();
		setResumeStatistics(data);
	}
	/**
	 * constructor which create a panel with a diagram 
	 * @param nameChart the name of the diagram
	 * @param nameChartx the name of the axis x
	 * @param nameCharty the name of the axis y
	 * @param map Map<?,Integer> data to put in the diagram
	 */
	public HUDStatistiques(String nameChart, String nameChartx, String nameCharty, Map<?, ?> map) {
		panel = new JPanel();
		getChartPanel(setName(nameChart, nameChartx, nameCharty, chartMatiere(map)));
	}
	/**
	 * create a panel with datas from de simulation like the number of ressources, ...
	 * @param data Data
	 */
	private void setResumeStatistics(Data data) {
		//create a panel for the title and globalWaitingTime
		JLabel title = new JLabel("Résumé des ressources disponibles");
		title.setFont(new Font("italic", Font.ITALIC, 21));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(title, BorderLayout.BEFORE_FIRST_LINE);
		
		//create a panels for the data
		JPanel panelLabels = new JPanel();
		List<JLabel> labels = new ArrayList<JLabel>();
		labels.add(new JLabel("Nombre de patient : " + data.getNbOfPatients()));
		labels.add(new JLabel("Nombre de chambre : " + data.getBedrooms().size()));
		
		labels.add(new JLabel("Nombre de receptionist : " + data.getReceptionists().size()));
		labels.add(new JLabel("Temps en reception : " + Utils.timeIntToString(data.getTimeReception())));
		
		labels.add(new JLabel("Nombre de scanner : " + data.getScanners().size()));
		labels.add(new JLabel("Temps au scanner : " + Utils.timeIntToString(data.getTimeScanner())));
		
		labels.add(new JLabel("Nombre d'infirmière : " + data.getNurses().size()));
		labels.add(new JLabel("Temps pour une analyse : " + Utils.timeIntToString(data.getTimeAnalysis())));
		
		labels.add(new JLabel("Nombre de bloc : " + data.getBlocs().size()));
		labels.add(new JLabel("Temps au bloc : " + Utils.timeIntToString(data.getTimeBloc())));
		
		labels.add(new JLabel("Nombre de docteur : " + data.getDoctors().size()));
		labels.add(new JLabel("Temps pour une prescription : " + Utils.timeIntToString(data.getTimePrescription())));
		
		GridLayout gridRessource = new GridLayout(labels.size()/2, 2, 7, 7);
		//add data to the panel
		panelLabels.setLayout(gridRessource);
		for(JLabel l : labels) {
			l.setFont(new Font("italic", Font.ITALIC, 17));
			l.setHorizontalAlignment(SwingConstants.CENTER);
			panelLabels.add(l);
		}
		panel.add(panelLabels,  BorderLayout.CENTER);
		
		
		JLabel titleStats = new JLabel("Résumé des temps globaux");
		titleStats.setFont(new Font("italic", Font.ITALIC, 21));
		titleStats.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel.add(titleStats);
		
		JPanel panelStats = new JPanel();
		JLabel waitTime = new JLabel("Temps d'attente moyen : "+Utils.timeIntToString(Statistics.getAverageWaitingTime(data)));
		waitTime.setFont(new Font("italic", Font.ITALIC, 17));
		waitTime.setHorizontalAlignment(SwingConstants.CENTER);
		//TODO
		JLabel spendTime = new JLabel("Temps passé aux urgences moyen : "+Utils.timeIntToString(Statistics.getAverageWaitingTime(data)));
		spendTime.setFont(new Font("italic", Font.ITALIC, 17));
		spendTime.setHorizontalAlignment(SwingConstants.CENTER);
		
		GridLayout gridStats = new GridLayout(3, 1, 7, 7);
		panelStats.setLayout(gridStats);
		panelStats.add(titleStats);
		panelStats.add(waitTime);
		panelStats.add(spendTime);
		panel.add(panelStats, BorderLayout.AFTER_LAST_LINE);
	}
	
	/**
	 * return an object DefaultCategoryDataset of data to use in a JFreeChart
	 * @param map Map<?,?>
	 * @return dataset
	 */
	private DefaultCategoryDataset chartMatiere(Map<?, ?> map) {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Entry<?, ?> m : map.entrySet()) {
			
			if(m.getValue().getClass() == Integer.class) {
				int x = ((Integer) m.getValue()).intValue();
				dataset.addValue(x, m.getKey().toString(), " ");
			}
			else if(m.getValue().getClass() == Double.class)
				dataset.addValue((Double)m.getValue(), m.getKey().toString(), " ");
		}
		return dataset;
	}
	/**
	 * it create a diagram with the names and data to use to create a ChartPanel
	 * @param nameChart name of the diagram
	 * @param nameChartx name of the axis x
	 * @param nameCharty name of the axis y
	 * @param dataset DefaultCategoryDataset
	 * @return freeChart a diagram
	 */
	private JFreeChart setName(String nameChart, String nameChartx, String nameCharty, DefaultCategoryDataset dataset) {
		JFreeChart freeChart = ChartFactory.createBarChart(nameChart, nameChartx, nameCharty, dataset,
				PlotOrientation.VERTICAL, true, true, false);
		return freeChart;
	}
	/**
	 * add to the panel a new ChartPanel created with barChart
	 * @param barChart JFreeChart
	 */
	private void getChartPanel(JFreeChart barChart) {
		chart = new ChartPanel(barChart);
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
