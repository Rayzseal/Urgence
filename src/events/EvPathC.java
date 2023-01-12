package events;

import model.Patient;
import utils.Data;

/**
 * Event of the start in the path C, it inherits the class Event
 */
public class EvPathC extends Event implements Runnable{
	/**
	 * constructor of EvPathC
	 * @param d Data
	 * @param p Patient
	 */
	public EvPathC(Data d, Patient p) {
		super(d,p);
	}
	/**
	 * runnable method, add the patient in the list waiting of waitListPathC
	 * then start analysis and scanner event
	 */
	@Override
	public void run() {
		// The patient can either do his analysis or his scanner 
		//(at the end, he will have to do both)
		if (!getPatient().isPathCScanner())
			// If the patient did not do yet is scanner, he will be added to the waiting
			// list now
			getData().getWaitListScanner().getListC().add(getPatient());
		if (!getPatient().isPathCAnalysis())
			// If the patient did not do yet is analysis, he will be added to the waiting
			// list now
			getData().getWaitListAnalysis().getListC().add(getPatient());
	}

}
