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
			//System.out.println(patient.toString() + " en acceuil.");
			patient.setState(State.RECEPTION, data.getTime());
			Thread.sleep(data.getTimeReception()/data.getReduceTime()); 
			
			//TODO assigne gravité
			patient.setGravity(Gravity.D); // TODEL for now
			//System.out.println(patient.toString() + "fin d'arrivée");
			
			patient.setState(State.AVAILABLE, data.getTime());
			new Thread(new BedroomResearch(data, patient)).start();
			//BedroomResearch bedroomResearch = new BedroomResearch(data, patient);
			//bedroomResearch.run();
			
			if(data.getWaitListArrival().size() > 0) {
				//System.out.println("test thread : " + Thread.activeCount());
				patient = data.getWaitListArrival().get(0);
				data.getWaitListArrival().remove(patient);
				//new Thread(new EndPatientArrival(data, patient, receptionistAvailable)).start();
				EndPatientArrival arrival = new EndPatientArrival(data, patient, receptionistAvailable);
				arrival.run();
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
