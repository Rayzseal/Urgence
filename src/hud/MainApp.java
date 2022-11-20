package hud;

import back.Patient;
import back.Scheduler;

public class MainApp {

	public static void main(String[] args) {
		Scheduler s = new Scheduler();
		s.run();
		for(Patient p : s.getData().getPatientsOver()) {
			System.out.println(p);
		}
	}

}
