package events;

import back.Patient;
import back.State;
import utils.Data;

public class EvPathC implements Runnable{
	private Data data;
	private Patient patient;

	public EvPathC(Data d, Patient p) {
		data = d;
		patient = p;
	}

	@Override
	public void run() {
		System.out.println("start path C"+patient.getName());
		data.getWaitListPathC().add(patient);
		
		if(!patient.getListState().containsKey(State.SCANNER)) {
			new Thread(new EvScanner(data, patient)).start();;			
		}
		if(!patient.getListState().containsKey(State.ANALYSIS)) {
			new Thread(new EvAnalysis(data, patient)).start();
		}
		
		
	}

}
