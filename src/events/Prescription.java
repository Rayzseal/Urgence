package events;

import java.util.ArrayList;

import back.Patient;
import back.State;
import utils.Data;
import utils.Utils;

public class Prescription implements Runnable{
	private Patient patient;
	private Data data;

	public Prescription() {
		// TODO Auto-generated constructor stub
	}
	
	public Prescription(Data d, Patient p) {
			data = d;
			patient = p;
		
	}
	@Override
	public void run() {
		int prescriptionAvailable = Utils.objectAvailable(data.getDoctors());
		if(prescriptionAvailable >= 0) { 
			synchronized (data.getDoctors()) {
				data.getDoctors().get(prescriptionAvailable).setState(State.OCCUPIED);
		    }
			EndPrescription endPrescription = new EndPrescription(data, patient, prescriptionAvailable);
			endPrescription.run();
		}
		else {
			synchronized (data.getWaitListPrescription()) {
				data.getWaitListPrescription().add(patient, data.getTime());

		    }
		}
		
	}


}
