package events;

import java.util.ArrayList;

import back.Patient;
import back.Receptionist;
import back.Room;
import back.Scheduler;
import back.State;
import utils.Data;
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
		//patient.setState(State.ARRIVAL, data.getTime());
		synchronized (patient) {
			patient.setState(State.ARRIVAL, data.getTime());
		}
		
		int receptionistAvailable = Utils.objectAvailable(data.getReceptionists());
		if(receptionistAvailable >= 0) {  //TODO
			synchronized (data.getReceptionists()) {
				data.getReceptionists().get(receptionistAvailable).setState(State.OCCUPIED);
		    }
			new Thread(new EndPatientArrival(data, patient, receptionistAvailable)).start();;
			//EndPatientArrival endPatientArrival = new EndPatientArrival(data, patient, receptionistAvailable);
			//endPatientArrival.run();
		}
		else {
			synchronized (data.getWaitListArrival()) {
				data.getWaitListArrival().add(patient);
				patient.setState(State.WAITING, data.getTime());
		    }
		}
		
	}

}
