package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import model.Gravity;
import model.Patient;
import model.Ressource;
import model.State;
import utils.Data;

/**
 * Event of end in Prescription, it inherits the class Event
 */
public class EvEndPrescription extends Event implements Runnable {
	/**
	 * constructor of the event
	 * 
	 * @param d               Data
	 * @param p               Patient
	 * @param objectAvailable Ressource in the list of ressources used
	 */
	public EvEndPrescription(Data d, Patient p, Ressource objectAvailable) {
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
	 * set the next event when the patient finished this event
	 */
	@Override
	public void nextEvent() {
		new EvPatientLeave(getData(), getPatient()).run();
	}

	/**
	 * set the waiting time to a patient
	 */
	public void setWaitingTime() {
		// Get last state of patient
		List<Entry<State, Integer>> entryList = new ArrayList<>(getPatient().getListState().entrySet());
		Entry<State, Integer> beforeLastEntry = entryList.get(entryList.size() - 2);
		Entry<State, Integer> lastEntry = entryList.get(entryList.size() - 1);

		// Waiting time
		int beforeLastTime = getPatient().getListState().get(beforeLastEntry.getKey());
		int lastTime = getPatient().getListState().get(lastEntry.getKey());
		int waitingTime = Data.getTimeValue() - beforeLastTime;
		getPatient().getListWaitTime().put(getStateEvent(), waitingTime);
		getPatient().addWaitingTime(waitingTime);

		int val = Data.getTimeValue() + waitingTime;

		// Add time
		getPatient().setTimePatient(val);
		getObjectAvailable().setActualTime(getPatient().getTimePatient());
	}

	/**
	 * runnable method which call endEvent()
	 */
	@Override
	public void run() {

		if (getPatient().getGravity() == Gravity.C)
			getData().getWaitListPrescription().getListC().remove(getPatient());
		endEvent();

	}

}
