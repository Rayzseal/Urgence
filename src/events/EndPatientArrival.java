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
	private Receptionist receptionist;
	private int receptionistAvailable;
	
	public EndPatientArrival() {
		patient = null;
		data = null;
		receptionist = null;
	}
	
	public EndPatientArrival(Data d, Patient p, int rAvailable) {
		data = d;
		patient = p;
		receptionistAvailable = rAvailable;
		receptionist = data.getReceptionists().get(receptionistAvailable);
	}
	
	@Override
	public void run() {
		try {
			System.out.println(patient.toString() + " en acceuil.");
			patient.setState(State.OCCUPIED);
			Thread.sleep(data.getTimeReception()/data.getReduceTime()); 
			//TODO assigne gravit�
			patient.setGravity(Gravity.D); // TODEL for now
			System.out.println(patient.toString() + "fin d'arriv�e");
			
			patient.setState(State.AVAILABLE);
			Utils.pathChoice(data, patient); //TODO mettre dans une chambre
			
			if(data.getWaitListArrival().size() > 0) {
				//System.out.println("test thread : " + Thread.activeCount());
				patient = (Patient) data.getWaitListArrival().get(0);
				data.getWaitListArrival().remove(patient);
				new Thread(new EndPatientArrival(data, patient, receptionistAvailable)).start();
			}else {
				synchronized (data.getReceptionists()) {
					//data.setNbOfAvailableReceptionists(data.getNbOfAvailableReceptionists()+1);
					data.getReceptionists().get(receptionistAvailable).setState(State.AVAILABLE);;
			    }
			}
				
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
