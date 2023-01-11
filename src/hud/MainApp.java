package hud;

import controller.Scheduler;

public class MainApp {

	public static void main(String[] args) {

		//Scheduler s = new Scheduler("PatientExemple.csv");
		//Scheduler s = new Scheduler(DataFile.readDataFile("SimulationExemple.csv"));
		Scheduler s = new Scheduler(50);

		s.run();
		
		//HUDMain hud = new HUDMain(DataFile.readDataFile("SimulationEnd.csv"));
		HUDMain hud = new HUDMain(s.getData());
		hud.setFrame();

	}


}
