package hud;

import controller.Scheduler;
import model.Patient;

public class MainApp {

	public static void main(String[] args) {

		Scheduler s = new Scheduler();
		s.run();
		for (Patient p : s.getData().getPatientsOver()) {
			if (p.getListWaitTime().size() >= 0) {
				System.out.println(p);

			}

		}

		// Statistics.statistics(s.getData());

	}


}
