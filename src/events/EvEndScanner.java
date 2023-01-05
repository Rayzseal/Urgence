package events;

import model.Gravity;
import model.Patient;
import model.Ressource;
import model.State;
import utils.Data;
import utils.EventsUtils;
/**
 * Event of end in Scanner, it inherits the class Event
 */
public class EvEndScanner extends Event implements Runnable {

	public EvEndScanner(Data d, Patient p, int objectAvailable) {
		super(d, p, objectAvailable);
		setState();
	}
	/**
	 * set ressources, times and waitingList to use in this event
	 */
	public void setState() {
		setStateEvent(State.SCANNER);
		setRessources(getData().getScanners());
		setTimeRessource(getData().getTimeScanner());
		setWaitingList(null);
	}
	/**
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		switch (getPatient().getGravity()) {
		case B:
			new Thread(new EvBloc(getData(), getPatient())).start();
			break;
		case C:
			new Thread(new EvEndPathC(getData(), getPatient())).start();
			break;
		default:
			throw new IllegalArgumentException("The patient shouldn't be here");
		}

	}
	/**
	 * set the event for the nextPatient to continue when the patient finished this event
	 */
	@Override
	public void sameEvent(Patient nextPatient) {
		EvEndScanner e = new EvEndScanner(getData(), nextPatient, getObjectAvailable());
		e.run();
	}
	/**
	 * override of the methods getNextPatient to add particularities due to the path C
	 * The method verifies if a patient is waiting in the waitingList
	 * @return nextPatient or null if no patient is waiting
	 */
	@Override
	public Patient getNextPatient() {
		Patient nextPatient = null;
		synchronized (getData().getWaitListPathC().get(getStateEvent())) {
			if (getData().getWaitListPathC().get(getStateEvent()).size() > 0) {

				nextPatient = getData().getWaitListPathC().get(getStateEvent()).selectPatientFromArrayList();
				if (nextPatient.getGravity() == Gravity.C) {
					if (EventsUtils.patientAvailable(getData(), nextPatient, State.ANALYSIS)) {
						getData().getWaitListPathC().get(getStateEvent()).remove(nextPatient, getData().getTime());
					}else {
						getData().getWaitListPathC().get(getStateEvent()).remove(nextPatient);
						nextPatient = getNextPatient();
					}
				} else {
					getData().getWaitListPathC().get(getStateEvent()).remove(nextPatient, getData().getTime());
				}

			} else {
				synchronized (getRessources()) {
					((Ressource) getRessources().get(getObjectAvailable())).setState(State.AVAILABLE);
				}
			}
		}
		return nextPatient;
		
	}
	/**
	 * runnable method which call endEvent() from Event
	 */
	@Override
	public void run() {
		endEvent();

	}

}
