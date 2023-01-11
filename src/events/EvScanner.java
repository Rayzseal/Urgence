package events;

import model.Patient;
import model.State;
import utils.Data;
/**
 * Event of start of Scanner, it inherits the class Event
 */
public class EvScanner extends Event implements Runnable {
	/**
	 * constructor of EvAnalysis
	 * @param d Data
	 * @param p Patient
	 */
	public EvScanner(Data d, Patient p) {
		super(d, p);
		setState();
	}
	/**
	 * set ressources, times and waitingList to use in this event
	 */
	public void setState() {
		setStateEvent(State.SCANNER);
		setRessources(getData().getScanners());
		setTimeRessource(getData().getTimeScanner());
		setWaitingList(getData().getWaitListScanner());
	}
	/**
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		new EvEndScanner(getData(), getPatient(), getObjectAvailable()).run();
	}
	
	/**
	 * runnable method which call startEvent() from Event
	 */
	@Override
	public void run() {
		startEvent();
	}

}
