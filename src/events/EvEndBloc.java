package events;

import model.Patient;
import model.Ressource;
import model.State;
import utils.Data;
/**
 * Event of end in Bloc, it inherits the class Event
 */
public class EvEndBloc extends Event implements Runnable {
	/**
	 * constructor of the event
	 * @param d Data
	 * @param p Patient
	 * @param objectAvailable index in the list of ressources used
	 */
	public EvEndBloc(Data d, Patient p, Ressource objectAvailable) {
		super(d, p, objectAvailable);
		setState();

	}
	/**
	 * set ressources, times and waitingList to use in this event
	 */
	public void setState() {
		setStateEvent(State.BLOC);
		setRessources(getData().getBlocs());
		setTimeRessource(getData().getTimeBloc());
		setWaitingList(getData().getWaitListBloc());
	}
	/**
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		getData().getWaitListPrescription().add(getPatient());
	}
	
	/**
	 * runnable method which call endEvent() from Event
	 */
	@Override
	public void run() {
		endEvent();
		
	}

}
