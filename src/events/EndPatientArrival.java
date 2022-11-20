package events;

import back.Gravity;
import back.Patient;
import back.Receptionist;
import back.State;
import utils.Data;
import utils.Utils;

public class EndPatientArrival implements Runnable{
	private Patient patient;
	private Data data;
	private int receptionistAvailable;
	
	public EndPatientArrival() {
		patient = null;
		data = null;
	}
	
	public EndPatientArrival(Data d, Patient p, int rAvailable) {
		data = d;
		patient = p;
		receptionistAvailable = rAvailable;
	}
	
	@Override
	public void run() {
		try {
			patient.setState(State.OCCUPIED, data.getTime());
			patient.getListState().put(State.RECEPTION, data.getTime());			
			Thread.sleep(data.getTimeReception()/data.getReduceTime()); 
			
			//TODO assigne gravité
			patient.setGravity(Gravity.D); // TODEL for now
			
			patient.setState(State.AVAILABLE, data.getTime());
			new Thread(new BedroomResearch(data, patient)).start();
			
			if(data.getWaitListArrival().size() > 0) {
				patient = data.getWaitListArrival().get(0);
				data.getWaitListArrival().remove(patient);
				new Thread(new EndPatientArrival(data, patient, receptionistAvailable)).start();
			}else {
				synchronized (data.getReceptionists()) {
					data.getReceptionists().get(receptionistAvailable).setState(State.AVAILABLE);
			    }
			}
				
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
