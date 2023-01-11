package events;

import java.util.ArrayList;

import model.Bedroom;
import model.Patient;
import model.State;
import model.WaitingList;
import utils.Data;
import utils.EventsUtils;

/**
 * Event of bedroom research , it inherits the class Event
 */
public class EvBedroomResearch extends Event implements Runnable {
	/**
	 * constructor of the event
	 * 
	 * @param d Data
	 * @param p Patient
	 */
	public EvBedroomResearch(Data d, Patient p) {
		super(d, p);
		setState();

	}

	/**
	 * set ressources, times and waitingList to use in this event
	 */
	public void setState() {
		setStateEvent(State.BEDROOM);
		setRessources(getData().getBedrooms());
		setTimeRessource(0);
		setWaitingList(getData().getWaitListBedroom());
	}

	/**
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		removeFromWaitingList();
		
		setStateEndEvent();
		
		setWaitingTime();
		
		// start of the path
		EventsUtils.pathChoice(getData(), getPatient());
	}

	/**
	 * set patient to State he should be and his bedroom, and set Occupied to the ressource used
	 */
	public void setStateEndEvent() {
		getPatient().setState(State.BEDROOM);
		getPatient().setBedroom((Bedroom) getObjectAvailable());
		getPatient().getListState().put(State.BEDROOM, Data.getTimeValue());
		getObjectAvailable().setState(State.OCCUPIED);
		getObjectAvailable().getListState().put(State.AVAILABLE, Data.getTimeValue());
	}

	/**
	 * override so events can get the list of bedrooms available
	 */
	public ArrayList<?> getRessourceAvailable() {
		return WaitingList.verifyRessourceAvailableBedroom(getData().getBedrooms());
	}

	/**
	 * runnable method which call startEvent()
	 */
	@Override
	public void run() {
		startEvent();

	}

}
