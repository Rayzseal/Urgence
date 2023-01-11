package events;

import model.Bedroom;
import model.Gravity;
import model.Patient;
import model.State;
import utils.Data;
import utils.EventsUtils;
/**
 * Event of the critic arrival, it inherits the class Event
 */
public class EvPatientCriticArrival extends Event implements Runnable{
	/**
	 * constructor of EvAnalysis
	 * @param d Data
	 * @param p Patient
	 */
	public EvPatientCriticArrival(Data d, Patient p) {
		super(d, p);
	}
	
	/**
	 * runnable method, set the bedroom and gravity
	 * The patient doesn't wait for a bedroom
	 */
	@Override
	public void run() {
		getPatient().setBedroom(new Bedroom());
		getPatient().setGravity(Gravity.A);
		getPatient().setState(State.BEDROOM, getPatient().getArrivalDate());
		EventsUtils.pathChoice(getData(), getPatient());
	}

}
