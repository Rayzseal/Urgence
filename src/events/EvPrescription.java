package events;

import model.Patient;
import model.State;
import utils.Data;

/**
 * Event of start in Prescription, it inherits the class Event
 */
public class EvPrescription extends Event implements Runnable {
	/**
	 * constructor of EvAnalysis
	 * 
	 * @param d Data
	 * @param p Patient
	 */
	public EvPrescription(Data d, Patient p) {
		super(d, p);
		setState();

	}

	/**
	 * set ressources, times and waitingList to use in this event
	 */
	public void setState() {
		setStateEvent(State.PRESCRIPTION);
		setRessources(getData().getDoctors());
		setTimeRessource(getData().getTimePrescription());
		setWaitingList(getData().getWaitListPrescription());
	}

	/**
	 * set the next event when the patient finished this event
	 */
	public void nextEvent() {
		new EvEndPrescription(getData(), getPatient(), getObjectAvailable()).run();;
	}

	/**
	 * runnable method which call startEvent() from Event
	 */
	@Override
	public void run() {
		startEvent();
	}

}
