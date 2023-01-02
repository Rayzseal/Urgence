package events;

import model.Patient;
import model.State;
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
		synchronized (data.getWaitListPathC()) {
			data.getWaitListPathC().get(State.WAITING).add(patient);
		}
		
		if(!patient.getListState().containsKey(State.ANALYSIS)) {
			System.out.println("start path C analysis "+patient);
			new Thread(new EvAnalysis(data, patient)).start();
		}
		if(!patient.getListState().containsKey(State.SCANNER)) {
			System.out.println("start path C scanner "+patient);
			new Thread(new EvScanner(data, patient)).start();		
		}
		
		
		
	}

}
