package hud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import controller.Statistics;
import utils.Data;

public class HUDMain {
	private Data data;
	private JFrame frame;
	private JPanel panel;
	private Map<JMenuItem, HUDStatistiques> statistics;

	public HUDMain(Data data) {
		this.data = data;
		frame = new JFrame();
		panel = new JPanel();
		statistics = new HashMap<JMenuItem, HUDStatistiques>();
		setMenu();
		setFrame();
	}
	
	public void setMenu() {
		JMenuBar menubar = new JMenuBar();
		statistics.put(setMenuItem("Information"), new HUDStatistiques(data));
	    String name = "Temps moyen d'attente par gravité";
	    statistics.put(setMenuItem(name),setHUDStatistics(name, "Gravité d'un patient", "temps en seconde", Statistics.getAverageWaitingTimeByGravity(data))) ;
	    
	    for (Entry<JMenuItem, HUDStatistiques> i : statistics.entrySet()) {
	    	menubar.add(i.getKey());
		}
	    frame.setJMenuBar(menubar);
	}
	
	public JMenuItem setMenuItem(String nameItem) {
		JMenuItem item = new JMenuItem(nameItem);
		setAction(item);
		return item;
	}

	public HUDStatistiques setHUDStatistics(String nameChart, String nameChartx, String nameCharty, Map<?, Integer> map) {
		HUDStatistiques stats = new HUDStatistiques(nameChart, nameChartx, nameCharty, map);
		return stats;

	}

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

	public void setFrame() {
		panel = new HUDStatistiques(data).getPanel();
		frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}

}
