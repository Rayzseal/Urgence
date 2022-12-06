package events;

import back.Patient;
import back.State;
import utils.Data;

public class ParcoursC implements Runnable{
	private Data data;
	private Patient patient;

	public ParcoursC(Data d, Patient p) {
		data = d;
		patient = p;
	}

	@Override
	public void run() {
		if(!patient.getListState().containsKey(State.SCANNER)) {
			
		}
		if(!patient.getListState().containsKey(State.ANALYSIS)) {
			
		}
		if(patient.getListState().containsKey(State.SCANNER) && patient.getListState().containsKey(State.ANALYSIS)) {
			Prescription e = new Prescription(data, patient);
			e.run();
		}
		
	}

}
