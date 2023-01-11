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
	 * runnable method, restart the event EvPathC if patient didn't do both required activity
	 * else start the event Prescription
	 */
	@Override
	public void run() {
		if (getPatient().isPathCAnalysis() && getPatient().isPathCScanner())
			getData().getWaitListPrescription().getListC().add(getPatient());
		else if (!getPatient().isPathCScanner())
			// If the patient did not do yet is scanner, he will be added to the waiting
			// list now
			getData().getWaitListScanner().getListC().add(getPatient());
		else if (!getPatient().isPathCAnalysis())
			// If the patient did not do yet is analysis, he will be added to the waiting
			// list now
			getData().getWaitListAnalysis().getListC().add(getPatient());
		
	}

}
