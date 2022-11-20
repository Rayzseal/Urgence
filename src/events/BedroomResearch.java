package events;

import java.util.ArrayList;

import back.Patient;
import back.State;
import utils.Data;
import utils.EventsUtils;
import utils.Utils;

public class BedroomResearch implements Runnable{
	
	private Data data;
	private Patient patient;

	public BedroomResearch(Data d, Patient p) {
		data = d;
		patient = p;
	}
	
	@Override
	public void run() {
		int bedAvailable = Utils.objectAvailable(data.getBedrooms());
		if(bedAvailable >= 0) { 
			synchronized (data.getReceptionists()) {
				data.getBedrooms().get(bedAvailable).setState(State.OCCUPIED);
		    }
			patient.setBedroom(data.getBedrooms().get(bedAvailable));
			//patient.setState(State.BEDROOM, data.getTime());
			patient.getListState().put(State.BEDROOM, data.getTime());
			
			//start of the path
			EventsUtils.pathChoice(data, patient);
		}
		else {
			synchronized (data.getWaitListArrival()) {
				data.getWaitListBedroom().add(patient, data.getTime());
		    }
		}
		
	}

}
