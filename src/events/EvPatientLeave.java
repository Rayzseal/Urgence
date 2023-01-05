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

		synchronized (getRessources()) {
			if (getPatient().getBedroom() != null) {
				getPatient().getBedroom().setState(State.AVAILABLE);
				getPatient().setBedroom(null);
			}
			getPatient().getListState().put(State.OUT, getData().getTime());
			
			synchronized (getData().getPatientsActive()) {
				getData().getPatientsActive().remove(getPatient());
			}
			synchronized (getData().getPatientsOver()) {
				getData().getPatientsOver().add(getPatient());
			}

			if (getWaitingList().size() > 0) {
				Patient nextPatient = getWaitingList().selectPatientFromArrayList();
				synchronized (getWaitingList()) {					
					getWaitingList().remove(nextPatient, getData().getTime());
				}
				new Thread(new EvBedroomResearch(getData(), nextPatient)).start();
			}
		}

	}

}
