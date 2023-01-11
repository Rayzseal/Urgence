package events;

import model.Gravity;
import model.Patient;
import model.Ressource;
import model.State;
import utils.Data;

/**
 * Event of end in Analysis, it inherits the class Event
 */
public class EvEndAnalysis extends Event implements Runnable {
	/**
	 * constructor of the event
	 * 
	 * @param d               Data
	 * @param p               Patient
	 * @param objectAvailable Ressource in the list of ressources used
	 */
	public EvEndAnalysis(Data d, Patient p, Ressource objectAvailable) {
		super(d, p, objectAvailable);
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
		getPatient().setPathCAnalysis(true);
		new EvEndPathC(getData(), getPatient()).run();
	}

	/**
	 * runnable method which call endEvent() from Event
	 * and remove from the list from the path C
	 */
	@Override
	public void run() {
		// While we do the analysis, the patient is not available for a scanner
		if (getPatient().getGravity() == Gravity.C) {
			getData().getWaitListAnalysis().getListC().remove(getPatient());
			getData().getWaitListScanner().getListC().remove(getPatient());
		}
		endEvent();

	}

}
