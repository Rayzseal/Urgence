package events;

import model.Patient;
import model.Receptionist;
import model.State;
import model.WaitingList;
import utils.Data;

/**
 * Event of start when the patient arrives, it inherits the class Event
 */
public class EvPatientArrival extends Event implements Runnable {
	/**
	 * constructor of EvPatientArrival
	 * 
	 * @param d Data
	 * @param p Patient
	 */
	public EvPatientArrival(Data d, Patient p) {
		super(d, p);
		setState();
	}

	/**
	 * set ressources, times and waitingList to use in this event
	 */
	public void setState() {
		setStateEvent(State.RECEPTION);
		setRessources(getData().getReceptionists());
		setTimeRessource(getData().getTimeReception());
		setWaitingList(new WaitingList());
	}

	/**
	 * set the next event when the patient finished this event
	 */
	public void nextEvent() {
		new EvEndPatientArrival(getData(), getPatient(), getObjectAvailable()).run();
	}

	
	/**
	 * override of the methods getNextPatient
	 */
	@Override
	public Patient getNextPatient() {
		return getWaitingList().attributeNextPatientReception((Receptionist)getObjectAvailable(), getData());
	}
	

	/**
	 * runnable method which call startEvent() from Event
	 */
	@Override
	public void run() {
		startEvent();
	}

}
