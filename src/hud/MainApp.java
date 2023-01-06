package hud;

import controller.Scheduler;
import controller.Statistics;
import model.Patient;

public class MainApp {

	public static void main(String[] args) {

		Scheduler s = new Scheduler("SimulationExemple2.csv");
		//Scheduler s = new Scheduler(100);
		//Scheduler s = new Scheduler(10);

		s.run();
		for (Patient p : s.getData().getPatientsOver()) {
			if (p.getListWaitTime().size() >= 1) {
				System.out.println(p);

			}

		}
		HUDMain hud = new HUDMain(s.getData());

	}


}
