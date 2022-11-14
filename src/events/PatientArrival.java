package events;

import java.util.ArrayList;

import back.Data;
import back.Patient;
import back.Receptionist;

public class PatientArrival implements Runnable {
	private Patient patient;
	private Receptionist receptionist;
	private Data data;
	
	public PatientArrival() {
		patient = null;
		receptionist = null;
	}
	
	public PatientArrival(Data d, Patient p) {
		data = d;
		patient = p;
		receptionist = null;
	}

	@Override
	public void run() {
		if(data.getNbOfAvailableReceptionists() > 0) {
			data.setNbOfAvailableReceptionists(data.getNbOfAvailableReceptionists()-1);
			System.out.println(patient.toString() + " en acceuil.");
		}
		else
			data.getWaitListArrival().addList(patient);
		
	}

}
