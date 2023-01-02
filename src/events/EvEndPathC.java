package events;

import back.Patient;
import back.State;
import utils.Data;

public class EvEndPathC implements Runnable{
	private Data data;
	private Patient patient;

	public EvEndPathC(Data d, Patient p) {
		data = d;
		patient = p;
	}

	@Override
	public void run() {

		if(!patient.getListState().containsKey(State.SCANNER) || !patient.getListState().containsKey(State.ANALYSIS)) {
			EvPathC e = new EvPathC(data, patient);
			e.run();
			
		}
		else {
			System.out.println("fin path C"+patient.getName());
			EvPrescription e = new EvPrescription(data, patient);
			e.run();
		}
		
	}

}
