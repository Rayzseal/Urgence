package events;

import java.util.ArrayList;

import back.Data;
import back.Patient;
import back.Receptionist;
import back.State;

public class PatientArrival implements Runnable {
	private Patient patient;
	private Data data;
	
	public PatientArrival() {
		patient = null;
	}
	
	public PatientArrival(Data d, Patient p) {
		data = d;
		patient = p;
	}

	@Override
	public void run() {
		if(data.getNbOfAvailableReceptionists() > 0) {
			synchronized (this) {
				data.setNbOfAvailableReceptionists(data.getNbOfAvailableReceptionists()-1);
		    }
			EndPatientArrival endPatientArrival = new EndPatientArrival(data, patient);
			endPatientArrival.run();
		}
		else {
			synchronized (this) {
				data.getWaitListArrival().add(patient);
		    }
			System.out.println(" Wating list : "+data.getWaitListArrival());
		}
		
	}

}
