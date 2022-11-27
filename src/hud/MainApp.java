package hud;

import java.util.Map.Entry;

import back.Patient;
import back.Scheduler;
import back.State;
import utils.EventsUtils;
import utils.Utils;

public class MainApp {

	public static void main(String[] args) {
		Scheduler s = new Scheduler(1000);
		s.run();
		for(Patient p : s.getData().getPatientsOver()) {
			if(p.getListWaitTime().size()>=1) {
				System.out.println(p);
				
			}
			
		}
		
	}

}
