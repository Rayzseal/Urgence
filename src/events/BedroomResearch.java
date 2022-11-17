package events;

import java.util.ArrayList;

import back.Data;
import back.Patient;
import back.State;
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
		if(bedAvailable >= 0) {  //TODO
			synchronized (data.getReceptionists()) {
				data.getBedrooms().get(bedAvailable).setState(State.OCCUPIED);
		    }
			patient.setBedroom(data.getBedrooms().get(bedAvailable));
			System.out.println(patient+ " a trouvé une chambre");
			Utils.pathChoice(data, patient);
		}
		else {
			synchronized (data.getWaitListArrival()) {
				data.getWaitListBedroom().add(patient);
		    }
		}
		
	}

}
