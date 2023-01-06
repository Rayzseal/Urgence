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

import utils.Data;
import utils.Utils;


public class HUDStatistiques {
	private JPanel panel;
	private ChartPanel chart;

	public HUDStatistiques(Data data) {
		panel = new JPanel();
		setResumeStatistics(data);
	}

	public HUDStatistiques(String nameChart, String nameChartx, String nameCharty, Map<?, Integer> map) {
		panel = new JPanel();
		getChartPanel(setName(nameChart, nameChartx, nameCharty, chartMatiere(map)));
	}
	
	private void setResumeStatistics(Data data) {
		JLabel title = new JLabel("Résumé des ressources disponibles");
		title.setFont(new Font("italic", Font.ITALIC, 17));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel.add(title, BorderLayout.BEFORE_FIRST_LINE);
		
		JPanel panelLabels = new JPanel();
		List<JLabel> labels = new ArrayList<JLabel>();
		labels.add(new JLabel("Nombre de patient : " + data.getNbOfPatients()));
		labels.add(new JLabel("Nombre de chambre : " + data.getBedrooms().size()));
		
		labels.add(new JLabel("Nombre de receptionist : " + data.getReceptionists().size()));
		labels.add(new JLabel("Temps en reception : " + Utils.timeIntToString(data.getTimeReception())));
		
		labels.add(new JLabel("Nombre de scanner : " + data.getScanners().size()));
		labels.add(new JLabel("Temps au scanner : " + Utils.timeIntToString(data.getTimeScanner())));
		
		labels.add(new JLabel("Nombre de infirmière : " + data.getNurses().size()));
		labels.add(new JLabel("Temps pour une analyse : " + Utils.timeIntToString(data.getTimeAnalysis())));
		
		labels.add(new JLabel("Nombre de bloc : " + data.getBlocs().size()));
		labels.add(new JLabel("Temps au bloc : " + Utils.timeIntToString(data.getTimeBloc())));
		
		labels.add(new JLabel("Nombre de docteur : " + data.getDoctors().size()));
		labels.add(new JLabel("Temps pour une prescription : " + Utils.timeIntToString(data.getTimePrescription())));
		
		GridLayout gridRessource = new GridLayout(labels.size()/2, 2, 5, 5);

		panelLabels.setLayout(gridRessource);
		for(JLabel l : labels) {
			l.setFont(new Font("italic", Font.ITALIC, 17));
			l.setHorizontalAlignment(SwingConstants.CENTER);
			//labelUUID.setForeground(Color.white);
			panelLabels.add(l);
		}
		panel.add(panelLabels,  BorderLayout.AFTER_LAST_LINE);
	}

	private DefaultCategoryDataset chartMatiere(Map<?, Integer> map) {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Entry<?, Integer> m : map.entrySet()) {
			dataset.addValue(m.getValue() / 60, m.getKey().toString(), new Integer(0));
		}
		return dataset;
	}

	private JFreeChart setName(String nameChart, String nameChartx, String nameCharty, DefaultCategoryDataset dataset) {
		JFreeChart freeChart = ChartFactory.createBarChart(nameChart, nameChartx, nameCharty, dataset,
				PlotOrientation.VERTICAL, true, true, false);
		return freeChart;
	}

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
