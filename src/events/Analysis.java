package events;

import java.util.ArrayList;

import back.Patient;
import back.State;
import utils.Data;
import utils.Utils;

public class Analysis implements Runnable{
	Patient patient;
	Data data;
	
	public Analysis(Data d, Patient p) {
		patient = p;
		data = d;
	}

	@Override
	public void run() {
		int nurseAvailable = -1;
		synchronized (data.getNurses()) {
			nurseAvailable = Utils.objectAvailable(data.getNurses());
			if(nurseAvailable >= 0) { 
				
					data.getNurses().get(nurseAvailable).setState(State.OCCUPIED);
			    }
			else {
				synchronized (data.getWaitListAnalysis()) {
					data.getWaitListAnalysis().add(patient, data.getTime());
	
			    }
			}
			
		}
		
		if(nurseAvailable >= 0) {
			EndAnalysis e = new EndAnalysis(data, patient, nurseAvailable);
			e.run();
		}
		
	}

}
