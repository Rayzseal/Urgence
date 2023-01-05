package events;

import model.Patient;
import model.State;
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
	 * then start analysis and/or scanner event if he didn't already realised it
	 */
	@Override
	public void run() {
		//waitListPathC prevent synchronized issue between Analysis and Scanner events
		synchronized (getData().getWaitListPathC()) {
			getData().getWaitListPathC().get(State.WAITING).add(getPatient());
		}
		
		if(!getPatient().getListState().containsKey(State.ANALYSIS)) {
			new Thread(new EvAnalysis(getData(), getPatient())).start();
		}
		if(!getPatient().getListState().containsKey(State.SCANNER)) {
			new Thread(new EvScanner(getData(), getPatient())).start();		
		}
	}

}
