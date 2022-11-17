package events;

import java.util.ArrayList;

import back.Data;
import back.ExtensionList;
import back.Patient;
import back.Receptionist;
import back.Room;
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
		int receptionistAvailable = ExtensionList.objectAvailable(data.getReceptionists());
		if(receptionistAvailable >= 0) {  //TODO
			synchronized (data.getReceptionists()) {
				data.getReceptionists().get(receptionistAvailable).setState(State.OCCUPIED);;
				//data.setNbOfAvailableReceptionists(data.getReceptionists().size()-1);
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
