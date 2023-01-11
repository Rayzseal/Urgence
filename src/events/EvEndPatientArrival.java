package events;

import model.Patient;
import model.Ressource;
import model.State;
import utils.Data;
import utils.EventsUtils;

/**
 * Event of end of the patient arrival, it inherits the class Event
 */
public class EvEndPatientArrival extends Event implements Runnable {
	/**
	 * constructor of the event
	 * 
	 * @param d               Data
	 * @param p               Patient
	 * @param objectAvailable Ressource in the list of ressources used
	 */
	public EvEndPatientArrival(Data d, Patient p, Ressource objectAvailable) {
		super(d, p, objectAvailable);
		setState();

	}

	/**
	 * set ressources, times and waitingList to use in this event
	 */
	public void setState() {
		setStateEvent(State.RECEPTION);
		setRessources(getData().getReceptionists());
		setTimeRessource(getData().getTimeReception());
		setWaitingList(null);
	}

	/**
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		// test to prevent modification of the gravity data from Simulation file
		if (getPatient().getGravity() == null)
			getPatient().setGravity(EventsUtils.setGravity());
		getData().getWaitListBedroom().add(getPatient());
	}

	/**
	 * remove from the list arrival
	 */
	@Override
	public void removeFromWaitingList() {
		getData().getWaitListArrival().remove(getPatient());
	}

	/**
	 * runnable method which call endEvent() from Event
	 */
	@Override
	public void run() {
		endEvent();

	}

}
