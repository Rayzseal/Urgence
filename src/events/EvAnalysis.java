package events;

import model.Patient;
import model.State;
import utils.Data;

/**
 * Event of start in Analysis, it inherits the class Event
 */
public class EvAnalysis extends Event implements Runnable {
	/**
	 * constructor of EvAnalysis
	 * 
	 * @param d Data
	 * @param p Patient
	 */
	public EvAnalysis(Data d, Patient p) {
		super(d, p);
		setState();

	}

	/**
	 * set ressources, times and waitingList to use in this event
	 */
	public void setState() {
		setStateEvent(State.ANALYSIS);
		setRessources(getData().getNurses());
		setTimeRessource(getData().getTimeAnalysis());
		setWaitingList(getData().getWaitListAnalysis());
	}

	/**
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		new EvEndAnalysis(getData(), getPatient(), getObjectAvailable()).run();
	}

	/**
	 * runnable method which call startEvent()
	 */
	@Override
	public void run() {
		startEvent();
	}

}
