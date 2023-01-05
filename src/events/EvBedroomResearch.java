package events;

import model.Patient;
import model.State;
import utils.Data;
import utils.EventsUtils;
/**
 * Event of bedroom research , it inherits the class Event
 */
public class EvBedroomResearch extends Event implements Runnable {
	/**
	 * constructor of the event
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
	 * override the method otherAction to set a bedroom to a patient
	 */
	@Override
	public void otherAction() {
		getPatient().setBedroom(getData().getBedrooms().get(getObjectAvailable()));
		getPatient().setState(State.BEDROOM, getData().getTime());
	}
	/**
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		//start of the path
		EventsUtils.pathChoice(getData(), getPatient());
	}
	/**
	 * runnable method which call startEvent()
	 */
	@Override
	public void run() {
		startEvent();
	}


}
