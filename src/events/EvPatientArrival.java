package events;

import model.Patient;
import model.Ressource;
import model.State;
import utils.Data;
import utils.EventsUtils;
/**
 * Event of start when the patient arrives, it inherits the class Event
 */
public class EvPatientArrival extends Event implements Runnable {
	/**
	 * constructor of EvPatientArrival
	 * @param d Data
	 * @param p Patient
	 */
	public EvPatientArrival(Data d, Patient p) {
		super(d,p);
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
	public void nextEvent() {
		new Thread(new EvEndPatientArrival(getData(), getPatient(), getObjectAvailable())).start();
		/*EvEndPatientArrival e = new EvEndPatientArrival(getData(), getPatient(), getObjectAvailable());
		e.run();*/
	}
	/**
	 * override the method and add to the list arrival
	 */
	@Override
	public void addToWaitingList() {
		getData().getWaitListArrival().add(getPatient());
		getPatient().setState(State.WAITING);
		getPatient().getListWaitTime().put(State.RECEPTION, getData().getTime());
	}
	
	/**
	 * override of the methods startEvent to add particularities due the waiting list of arrival
	 * The method verifies if required ressources are available, patient continues his path if it is
	 * or he starts waiting in the waiting list
	 */
	@Override
	public void startEvent() {
		setObjectAvailable(-1);
		synchronized (getRessources()) {
			setObjectAvailable(EventsUtils.objectAvailable(getRessources()));
			if(getObjectAvailable() >= 0) { 
				
				((Ressource) getRessources().get(getObjectAvailable())).setState(State.OCCUPIED);
			    }
			else {
				synchronized (getData().getWaitListArrival()) {
					addToWaitingList();
			    }
			}
		}
		if(getObjectAvailable()>=0) {
			nextEvent();
		}
	}
	
	/**
	 * runnable method which call startEvent() from Event
	 */
	@Override
	public void run() {
		startEvent();
		
	}

}
