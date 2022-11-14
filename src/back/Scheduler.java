package back;

import java.util.ArrayList;
import java.util.Random;

import events.PatientArrival;
import utils.ReadFile;
import utils.SortPatientArrival;
import utils.Utils;

public class Scheduler {
	private Data data;
	
	public Scheduler(){
		data = new Data();
	}
	
	public void scheduler() {
		Utils.showList(data.getPatients());
		System.out.println("------------------");
		System.out.println("BEGIN");
		System.out.println("------------------");
		
		
		//While we still have patients to arrive or patients in treatment we wait, when every patients has been treated we stop.  
		while(data.getPatients().size()!=0 || data.getPatientsActive().size()!=0) {
			try {
				// 1000 == 1 seconde / reduceTime 
				// with reduceTime = an accelerator 
				Thread.sleep(1000/data.getReduceTime());
				data.setTime(data.getTime()+1);
				
				System.out.println("Time : "+ data.getTime());
				
				if (data.getPatients().get(0).getArrivalDate()== data.getTime()) {
					//Start patient 
					System.out.println("Arrivée patient : "+ data.getPatients().get(0));
					
					//Add to "over" list & remove patients from waiting list
					data.getPatientsActive().add(data.getPatients().get(0));
					data.getPatients().remove(data.getPatients().get(0));
					
					
					//arrival(data.getPatients().get(0));
					//If patient active = terminate state then add to patientOver & remove from patientActive
					
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
			
	public void arrival(Patient p) {
		System.out.println(data.getNbOfAvailableReceptionists());

		PatientArrival patientArrival = new PatientArrival(data, p);
		patientArrival.run();
		
			
			
			
		System.out.println(data.getNbOfAvailableReceptionists());
	}
	
	public void run() {
		scheduler();
		//arrival(data.getPatients().get(0));
	}
}
