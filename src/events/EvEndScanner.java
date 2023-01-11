package events;

import model.Gravity;
import model.Patient;
import model.Ressource;
import model.State;
import utils.Data;

/**
 * Event of end in Scanner, it inherits the class Event
 */
public class EvEndScanner extends Event implements Runnable {

	public EvEndScanner(Data d, Patient p, Ressource objectAvailable) {
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
		setWaitingList(getData().getWaitListScanner());
	}

	/**
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		switch (getPatient().getGravity()) {
		case B:
			getData().getWaitListBloc().add(getPatient());
			break;
		case C:
			getPatient().setPathCScanner(true);
			new EvEndPathC(getData(), getPatient()).run();
			break;
		default:
			break;
		}
	}

	/**
	 * runnable method which call endEvent() from Event
	 */
	@Override
	public void run() {
		// While we do the scanner, the patient is not available for an analysis
		if (getPatient().getGravity() == Gravity.C) {
			getData().getWaitListAnalysis().getListC().remove(getPatient());
			getWaitingList().getListC().remove(getPatient());
		}
		endEvent();

	}

}
