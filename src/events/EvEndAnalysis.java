package events;

import model.Gravity;
import model.Patient;
import model.Ressource;
import model.State;
import utils.Data;
import utils.EventsUtils;
/**
 * Event of end in Analysis, it inherits the class Event
 */
public class EvEndAnalysis extends Event implements Runnable{	
	/**
	 * constructor of the event
	 * @param d Data
	 * @param p Patient
	 * @param objectAvailable index in the list of ressources used
	 */
	public EvEndAnalysis(Data d, Patient p, int objectAvailable) {
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
		setWaitingList(null);
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
					if (EventsUtils.patientAvailable(getData(), nextPatient, State.SCANNER)) {
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
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		new Thread(new EvEndPathC(getData(), getPatient())).start();
	}
	/**
	 * set the event for the nextPatient to continue when the patient finished this event
	 */
	@Override
	public void sameEvent(Patient nextPatient) {
		EvEndAnalysis e = new EvEndAnalysis(getData(), nextPatient, getObjectAvailable());
		e.run();
	}
	/**
	 * runnable method which call endEvent() from Event
	 */
	@Override
	public void run() {
		endEvent();
	}
		

}
