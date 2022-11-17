package events;

import java.util.ArrayList;

import back.Data;
import back.Patient;
import back.Receptionist;
import back.Room;
import back.State;
import utils.Utils;

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
		int receptionistAvailable = Utils.objectAvailable(data.getReceptionists());
		if(receptionistAvailable >= 0) {  //TODO
			synchronized (data.getReceptionists()) {
				data.getReceptionists().get(receptionistAvailable).setState(State.OCCUPIED);
		    }
			EndPatientArrival endPatientArrival = new EndPatientArrival(data, patient, receptionistAvailable);
			endPatientArrival.run();
		}
		else {
			synchronized (data.getWaitListArrival()) {
				data.getWaitListArrival().add(patient);

				//System.out.println(" Wating list : "+data.getWaitListArrival());
		    }
		}
		
	}

}
