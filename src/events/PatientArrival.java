package events;

import java.util.ArrayList;

import back.Patient;
import back.Receptionist;
import back.Ressource;
import back.Scheduler;
import back.State;
import utils.Data;
import utils.Utils;

public class PatientArrival implements Runnable {
	private Patient patient;
	private Data data;
	
	public PatientArrival(Data d, Patient p) {
		data = d;
		patient = p;
	}
	
	public void arrival() {
		int receptionistAvailable  = -1;
		synchronized (data.getReceptionists()) {
			receptionistAvailable = Utils.objectAvailable(data.getReceptionists());
			if(receptionistAvailable >= 0) {  //TODO
				
					data.getReceptionists().get(receptionistAvailable).setState(State.OCCUPIED);
			    }
			else {
				synchronized (data.getWaitListArrival()) {
					data.getWaitListArrival().add(patient);
					patient.setState(State.WAITING);
					patient.getListWaitTime().put(State.RECEPTION, data.getTime());
			    }
			}
		}
		if(receptionistAvailable>=0) {
			new Thread(new EndPatientArrival(data, patient, receptionistAvailable)).start();
		}
			
		
		
	}
	@Override
	public void run() {
		arrival();
		
	}

}
