package hud;

import back.Patient;
import back.Scheduler;
import utils.EventsUtils;

public class MainApp {

	public static void main(String[] args) {
		Scheduler s = new Scheduler(10);
		s.run();
		for(Patient p : s.getData().getPatientsOver()) {
			System.out.println(p);
		}
		
	}

}
