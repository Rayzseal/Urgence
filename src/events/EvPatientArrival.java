package events;

import java.util.ArrayList;

import controller.Scheduler;
import model.Patient;
import model.Receptionist;
import model.Ressource;
import model.State;
import utils.Data;
import utils.Utils;

public class EvPatientArrival implements Runnable {
	private Patient patient;
	private Data data;
	
	public EvPatientArrival(Data d, Patient p) {
		data = d;
		patient = p;
	}
	
	public void arrival() {
		int receptionistAvailable  = -1;
		synchronized (data.getReceptionists()) {
			receptionistAvailable = Utils.objectAvailable(data.getReceptionists());
			if(receptionistAvailable >= 0) { 
				
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
			new Thread(new EvEndPatientArrival(data, patient, receptionistAvailable)).start();
		}
			
		
		
	}
	@Override
	public void run() {
		arrival();
		
	}

}
