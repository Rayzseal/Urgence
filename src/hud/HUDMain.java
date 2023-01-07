package hud;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import controller.Statistics;
import utils.Data;
/**
 * Class which create a JFrame to display statistics
 */
public class HUDMain {
	private Data data;
	private JFrame frame;
	private JPanel panel;
	private Map<JMenuItem, HUDStatistiques> statistics;
	/**
	 * constructor of HUDMain, create a JFrame, JPanel
	 * @param data
	 */
	public HUDMain(Data data) {
		this.data = data;
		frame = new JFrame();
		panel = new JPanel();
		statistics = new LinkedHashMap<JMenuItem, HUDStatistiques>();
	}
	/**
	 * set the menu bar with every statistics available
	 */
	public void setMenu() {
		JMenuBar menubar = new JMenuBar();
		statistics.put(setMenuItem("Information", "Autre"), new HUDStatistiques(data));
		JMenu menuGravity = new JMenu("Tri par gravité");
		setPreferedSize(menuGravity);
		JMenu menuRessource = new JMenu("Tri par ressource");
		setPreferedSize(menuRessource);
		String name = "Pourcentage de patient par gravité";
	    statistics.put(setMenuItem(name, "Gravity"),setHUDStatistics(name, "Gravité d'un patient", "en %", Statistics.getPercentagePatientByGravity(data)));
	    
	    name = "Temps moyen d'attente par gravité";
	    statistics.put(setMenuItem(name, "Gravity"),setHUDStatistics(name, "Gravité d'un patient", "temps en minute", Statistics.getAverageWaitingTimeByGravity(data)));
	    
	    name = "Temps moyen passé aux urgences par gravité";
	    statistics.put(setMenuItem(name, "Gravity"),setHUDStatistics(name, "Gravité d'un patient", "temps en minute", Statistics.getAverageTimeSpentInEmergencyByGravity(data)));
	    
	    name = "Temps moyen d'attente par ressource";//TODO replace method
	    statistics.put(setMenuItem(name, "Ressource"),setHUDStatistics(name, "Ressource", "temps en minute", Statistics.getAverageWaitingTimeState(data)));
	    
	    name = "Taux d'utilisation des ressources";//TODO replace method

	    statistics.put(setMenuItem(name, "Ressource"),setHUDStatistics(name, "Ressource", "en %", Statistics.getPercentageUtilizationStates(data)));
	    
	    for (Entry<JMenuItem, HUDStatistiques> i : statistics.entrySet()) {
	    	if(i.getKey().getName().equalsIgnoreCase("Gravity")) {
	    		menuGravity.add(i.getKey());
	    	}	
	    	else if(i.getKey().getName().equalsIgnoreCase("Ressource")) 
	    		menuRessource.add(i.getKey());
	    	else {
	    		setPreferedSize(i.getKey());
	    		menubar.add(i.getKey());
	    	}
	    		
		}
	    menubar.add(menuGravity);
	    menubar.add(menuRessource);
	    frame.setJMenuBar(menubar);
	}
	/**
	 * set the action and name a the JMenuItem
	 * @param textItem String
	 * @param nameItem String
	 * @return item JMenuItem
	 */
	public JMenuItem setMenuItem(String textItem, String nameItem) {
		JMenuItem item = new JMenuItem(textItem);
		item.setName(nameItem);
		setAction(item);
		return item;
	}
	/**
	 * set the maximum size of a component
	 * @param item Component
	 */
	public void setPreferedSize(Component item) {
		item.setMaximumSize((new JMenuItem("Temps par xxxxxxxxx")).getPreferredSize());
	}
	/**
	 * create a new HUDStatistics
	 * @param nameChart String
	 * @param nameChartx String
	 * @param nameCharty String
	 * @param map Map<?,Intger>
	 * @return stats new HUDStatistics
	 */
	public HUDStatistiques setHUDStatistics(String nameChart, String nameChartx, String nameCharty, Map<?, ?> map) {
		HUDStatistiques stats = new HUDStatistiques(nameChart, nameChartx, nameCharty, map);
		return stats;

	}
	
	/**
	 * set action of a JMenuItem with the HUDStatistics associated in the map statistics
	 * @param item JMenuItem
	 */
	public void setAction(JMenuItem item) {
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel = statistics.get(item).getPanel();
				frame.setContentPane(panel);
	    		frame.validate();
			}
		});
	}
	/**
	 * set the JFrame, Menu and the first panel to be display
	 */
	public void setFrame() {
		setMenu();
		panel = new HUDStatistiques(data).getPanel();
		frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}

}
