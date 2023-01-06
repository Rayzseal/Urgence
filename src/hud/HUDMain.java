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
		setPanel();
		setFrame();
	}

	public void setPanel() {
	    JMenuBar menubar = new JMenuBar();
	    String name = "Temps moyen d'attente par gravité";
	    statistics.put(setMenuItem(name),setNameChart(name, "Gravité d'un patient", "temps en seconde", Statistics.getAverageWaitingTimeByGravity(data))) ;
	    
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

	public HUDStatistiques setNameChart(String nameChart, String nameChartx, String nameCharty, Map<?, Integer> map) {
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
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
