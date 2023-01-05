package events;

import model.Patient;
import model.State;
import utils.Data;
/**
 * Event of end in Prescription, it inherits the class Event
 */
public class EvEndPrescription extends Event implements Runnable{
	/**
	 * constructor of the event
	 * @param d Data
	 * @param p Patient
	 * @param objectAvailable index in the list of ressources used
	 */
	public EvEndPrescription(Data d, Patient p, int objectAvailable) {
		super(d, p, objectAvailable);
		setState();

	}
	/**
	 * set ressources, times and waitingList to use in this event
	 */
	public void setState() {
		setStateEvent(State.PRESCRIPTION);
		setRessources(getData().getDoctors());
		setTimeRessource(getData().getTimePrescription());
		setWaitingList(getData().getWaitListPrescription());
	}
	/**
	 * set the event for the nextPatient to continue when the patient finished this event
	 */
	@Override
	public void sameEvent(Patient nextPatient) {
		EvEndPrescription endPrescription = new EvEndPrescription(getData(), nextPatient, getObjectAvailable());
		endPrescription.run();
	}
	/**
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		new Thread(new EvPatientLeave(getData(), getPatient())).start();
	}
	
	/**
	 * runnable method which call endEvent() from Event
	 */
	@Override
	public void run() {
		endEvent();
	}
	

}
