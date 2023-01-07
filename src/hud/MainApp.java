package hud;

import controller.Scheduler;
import model.Patient;
import utils.DataFile;

public class MainApp {

	public static void main(String[] args) {

		//Scheduler s = new Scheduler("PatientExemple2.csv");
		//Scheduler s = new Scheduler(DataFile.readDataFile("SimulationExemple.csv"));
		//Scheduler s = new Scheduler(50);
		//Scheduler s = new Scheduler(10);

		//s.run();
		/*for (Patient p : s.getData().getPatientsOver()) {
			if (p.getListWaitTime().size() >= 1) {
				//System.out.println(p);

			}

		}*/
		HUDMain hud = new HUDMain(DataFile.readDataFile("SimulationEnd.csv"));
		hud.setFrame();

	}


}
