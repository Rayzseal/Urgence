package events;

import back.Patient;
import back.State;
import utils.Data;
import utils.Utils;

public class EvScanner implements Runnable{
	Patient patient;
	Data data;

	public EvScanner() {
		// TODO Auto-generated constructor stub
	}
	
	public EvScanner(Data d, Patient p) {
		patient = p;
		data = d;
	}

	@Override
	public void run() {
		int scannerAvailable = Utils.objectAvailable(data.getScanners());
		if(scannerAvailable >= 0) { 
			synchronized (data.getScanners()) {
				data.getScanners().get(scannerAvailable).setState(State.OCCUPIED);
		    }
			EndScanner e = new EndScanner(data, patient, scannerAvailable);
			e.run();
		}
		else {
			synchronized (data.getWaitListScanner()) {
				data.getWaitListScanner().add(patient, data.getTime());

		    }
		}
		
	}

}
