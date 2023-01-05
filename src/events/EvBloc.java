package events;

import model.Patient;
import model.State;
import utils.Data;
/**
 * Event of start in a bloc , it inherits the class Event
 */
public class EvBloc extends Event implements Runnable{
	/**
	 * constructor of EvBloc
	 * @param d Data
	 * @param p Patient
	 */
	public EvBloc(Data d, Patient p) {
		super(d, p);
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
		EvEndBloc e = new EvEndBloc(getData(), getPatient(), getObjectAvailable());
		e.run();
	}
	/**
	 * runnable method which call startEvent() from Event
	 */
	@Override
	public void run() {
		startEvent();
		
	}
	

}
