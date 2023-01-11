package events;

import model.Patient;
import model.State;
import utils.Data;
/**
 * Event when the patient leave the emergency department, it inherits the class Event
 */
public class EvPatientLeave extends Event implements Runnable {
	/**
	 * constructor of EvAnalysis
	 * @param d Data
	 * @param p Patient
	 */
	public EvPatientLeave(Data d, Patient p) {
		super(d, p);
		setState();
	}
	/**
	 * set ressources, times and waitingList to use in this event
	 */
	public void setState() {
		setStateEvent(State.OUT);
		setRessources(getData().getBedrooms());
		setTimeRessource(0);
		setWaitingList(getData().getWaitListBedroom());
	}
	/**
	 * runnable method to reset the bedroom of the patient and gave to an other patient
	 * the method also put the patient in the list patientsOver
	 */
	@Override
	public void run() {
		getPatient().setState(State.OUT);
		if (!getPatient().isTypeArrival())
			getPatient().getBedroom().setState(State.AVAILABLE);
		
		getPatient().getListState().put(State.OUT, (Data.getTimeValue() + getData().getTimePrescription()));
		
		getData().getPatientsActive().remove(getPatient());
		getData().getPatientsOver().add(getPatient());

		// TODO remove this to not have any display
		System.out.println(getPatient());
		System.out.println();

	}

}
