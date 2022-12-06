package events;

import back.Patient;
import utils.Data;

public class PatientCriticArrival implements Runnable{
	private Patient patient;
	private Data data;

	public PatientCriticArrival(Data d, Patient p) {
		data = d;
		patient = p;
	}
	
	@Override
	public void run() {
		
	}

}
