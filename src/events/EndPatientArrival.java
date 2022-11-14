package events;

import back.Data;
import back.Gravity;
import back.Patient;
import back.Receptionist;
import back.State;
import utils.Utils;

public class EndPatientArrival implements Runnable{
	private Patient patient;
	private Data data;
	
	public EndPatientArrival() {
		patient = null;
		data = null;
	}
	
	public EndPatientArrival(Data d, Patient p) {
		data = d;
		patient = p;
	}
	
	@Override
	public void run() {
		try {
			System.out.println(patient.toString() + " en acceuil.");
			patient.setState(State.OCCUPIED);
			Thread.sleep(data.getTimeReception()/data.getReduceTime()); 
			//TODO assigne gravité
			patient.setGravity(Gravity.D); // TODEL for now
			System.out.println(patient.toString() + "fin d'arrivée");
			
			patient.setState(State.AVAILABLE);
			Utils.pathChoice(data, patient); //TODO mettre dans une chambre
			
			if(data.getWaitListArrival().size() > 0) {
				//System.out.println("test thread : " + Thread.activeCount());
				patient = data.getWaitListArrival().get(0);
				data.getWaitListArrival().remove(patient);
				new Thread(new EndPatientArrival(data, patient)).start();
			}else {
				synchronized (this) {
					data.setNbOfAvailableReceptionists(data.getNbOfAvailableReceptionists()+1);
			    }
			}
				
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
