package events;

import model.Patient;
import model.State;
import utils.Data;

/**
 * Event of the end in the path C, it inherits the class Event
 */
public class EvEndPathC extends Event implements Runnable{
	/**
	 * constructor of EvEndPathC
	 * @param d Data
	 * @param p Patient
	 */
	public EvEndPathC(Data d, Patient p) {
		super(d,p);
	}
	/**
	 * runnable method, restart the event EvPathC if patient didn't do both required activity
	 * else start the event Prescription
	 */
	@Override
	public void run() {

		if(!getPatient().getListState().containsKey(State.SCANNER) || !getPatient().getListState().containsKey(State.ANALYSIS)) {
			EvPathC e = new EvPathC(getData(), getPatient());
			e.run();
			
		}
		else {
			EvPrescription e = new EvPrescription(getData(), getPatient());
			e.run();
		}
		
	}

}
