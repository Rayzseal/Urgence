package hud;

import controller.Scheduler;

/**
 * 
 * Class MainApp, run this class to launch the project. 
 *
 */
public class MainApp {

	public static void main(String[] args) {
		//read a file of patients of 50 patients
		Scheduler s = new Scheduler("PatientExemple.csv");
		//generate 50 patients randomly
		//Scheduler s = new Scheduler(50);

		s.run();
		
		HUDMain hud = new HUDMain(s.getData());
		hud.setFrame();

	}


}
