package events;

import model.Gravity;
import model.Patient;
import model.Ressource;
import model.State;
import utils.Data;
import utils.EventsUtils;
/**
 * Event of start in Analysis, it inherits the class Event
 */
public class EvAnalysis extends Event implements Runnable {
	/**
	 * constructor of EvAnalysis
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
		EvEndAnalysis e = new EvEndAnalysis(getData(), getPatient(), getObjectAvailable());
		e.run();
	}
	/**
	 * override of the methods startEvent to add particularities due to the path C
	 * The method verifies if required ressources are available, patient continues his path if it is
	 * or he starts waiting in the waiting list
	 */
	@Override
	public void startEvent() {
		setObjectAvailable(-1);
		synchronized (getRessources()) {
			setObjectAvailable(EventsUtils.objectAvailable(getRessources()));
			if (getObjectAvailable() >= 0) {
				if (getPatient().getGravity() == Gravity.C) {
					//if patients has gravity C, the patients has to be available
					if (EventsUtils.patientAvailable(getData(), getPatient(), State.SCANNER)) {
						//the patient is available, the ressources is going to be used by him
						((Ressource) getRessources().get(getObjectAvailable())).setState(State.OCCUPIED);
					}
					else {
						//the patient isn't available, because he is the event Scanner so the ressource is set to -1
						setObjectAvailable(-1);
					}
				} else {
					((Ressource) getRessources().get(getObjectAvailable())).setState(State.OCCUPIED);
				}
			}
			else {
				synchronized (getData().getWaitListPathC()) {
					if (getPatient().getGravity() == Gravity.C) {
						//if the patient is available, he is add to the WaitingList Scanner
						if (getData().getWaitListPathC().get(State.WAITING).contains(getPatient())) {
							getData().getWaitListPathC().get(State.ANALYSIS).add(getPatient(), getData().getTime());
						}
					}
					else {
						getData().getWaitListPathC().get(State.ANALYSIS).add(getPatient(), getData().getTime());
					}
				}
			}
		}
		if (getObjectAvailable() >= 0) {
			nextEvent();
		}

	}
	/**
	 * runnable method which call startEvent()
	 */
	@Override
	public void run() {
		startEvent();
	}
		

}
