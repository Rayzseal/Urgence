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
	 * @param d Data
	 * @param p Patient
	 * @param objectAvailable index in the list of ressources used
	 */
	public EvEndPatientArrival(Data d, Patient p, int objectAvailable) {
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
	 * set the event for the nextPatient to continue when the patient finished this event
	 */
	@Override
	public void sameEvent(Patient nextPatient) {
		EvEndPatientArrival e = new EvEndPatientArrival(getData(), nextPatient, getObjectAvailable());
		e.run();
	}
	/**
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		//test to prevent modification of the gravity data from Simulation file
		if(getPatient().getGravity() == null)
			getPatient().setGravity(EventsUtils.setGravity());
		new Thread(new EvBedroomResearch(getData(), getPatient())).start();
	}
	
	/**
	 * override of the methods getNextPatient to add particularities due the waiting list
	 * The method verifies if a patient is waiting in the waitingList
	 * @return nextPatient or null if no patient is waiting
	 */
	@Override
	public Patient getNextPatient() {
		Patient nextPatient = null;
		synchronized (getData().getWaitListArrival()) {
			if (getData().getWaitListArrival().size() > 0) {

				nextPatient = getData().getWaitListArrival().get(0);
				getData().getWaitListArrival().remove(nextPatient);
				int time = nextPatient.getListWaitTime().get(getStateEvent());
				time = getData().getTime() - time;
				nextPatient.getListWaitTime().put(getStateEvent(), time);
				nextPatient.addWaitingTime(time);

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
