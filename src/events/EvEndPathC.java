package events;

import model.Patient;
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
	 * runnable method, restart the events not done if patient didn't do it
	 * else start the event Prescription
	 */
	@Override
	public void run() {
		if (getPatient().isPathCAnalysis() && getPatient().isPathCScanner())
			getData().getWaitListPrescription().getListC().add(getPatient());
		else new EvPathC(getData(), getPatient()).run();
		
	}

}
