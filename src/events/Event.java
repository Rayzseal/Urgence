package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import model.Patient;
import model.Ressource;
import model.State;
import model.WaitingList;
import utils.Data;

/**
 * Abstract method to every event
 */
public abstract class Event {
	private Patient patient;
	private Data data;
	private State stateEvent;
	private Ressource objectAvailable;
	private List<?> ressources;
	private int timeRessource;
	private WaitingList waitingList;

	/**
	 * constructor of an event
	 * 
	 * @param d Data
	 * @param p Patient
	 */
	public Event(Data d) {
		data = d;
		patient = null;
		objectAvailable = null;

	}

	/**
	 * constructor of an event
	 * 
	 * @param d Data
	 * @param p Patient
	 */
	public Event(Data d, Patient p) {
		data = d;
		patient = p;
		objectAvailable = null;

	}

	/**
	 * constructor of an event
	 * 
	 * @param d               Data
	 * @param p               Patient
	 * @param objectAvailable index in the list of ressources used
	 */
	public Event(Data d, Patient p, Ressource objectAvailable) {
		data = d;
		patient = p;
		this.objectAvailable = objectAvailable;

	}

	/**
	 * The method verifies if required ressources are available, patient continues
	 * his path if it is or he continue waiting in the waiting list
	 */
	@SuppressWarnings("unchecked")
	public void startEvent() {
		ArrayList<Ressource> arRessource;

		arRessource = (ArrayList<Ressource>) getRessourceAvailable();

		if (!arRessource.isEmpty()) {
			for (int i = 0; i < arRessource.size(); i++) {
				Patient p;
				setObjectAvailable(arRessource.get(i));
				p = getNextPatient();

				if (p != null) {
					setPatient(p);
					nextEvent();
				}
			}
		}
	}

	/**
	 * End of an event 
	 */
	public void endEvent() {
		// Remove patient from waiting list
		removeFromWaitingList();

		// Set States
		setStateEndEvent();

		// Waiting time
		setWaitingTime();

		// Next event
		nextEvent();
	}

	/**
	 * method to be override so patient continue on the next event
	 */
	public void nextEvent() {

	}

	/**
	 * method to be override so events can get the list of ressources available
	 */
	public ArrayList<?> getRessourceAvailable() {
		return WaitingList.verifyRessourceAvailable(ressources);
	}

	/**
	 * get the next Patient with the method attributeNextPatient
	 * 
	 * @return nextPatient Patient
	 */
	public Patient getNextPatient() {
		return getWaitingList().attributeNextPatient(getObjectAvailable(), getData());
	}

	/**
	 * remove from the waitingList
	 */
	public void removeFromWaitingList() {
		getWaitingList().remove(getPatient());
	}

	/**
	 * set patient to State he should be and set Occupied the ressource used
	 */
	public void setStateEndEvent() {
		getPatient().setState(stateEvent);
		getPatient().getListState().put(stateEvent, (Data.getTimeValue() + getTimeRessource()));
		getObjectAvailable().setState(State.OCCUPIED);
		getObjectAvailable().getListState().put(State.AVAILABLE, (Data.getTimeValue() + getTimeRessource()));
	}
	/**
	 * set the waiting time to a patient
	 */
	public void setWaitingTime() {
		// Get last state of patient
		List<Entry<State, Integer>> entryList = new ArrayList<>(getPatient().getListState().entrySet());
		Entry<State, Integer> lastEntry = entryList.get(entryList.size() - 2);

		// Waiting time
		int lastTime = getPatient().getListState().get(lastEntry.getKey());
		int waitingTime = Data.getTimeValue() - lastTime;
		getPatient().getListWaitTime().put(getStateEvent(), waitingTime);
		getPatient().addWaitingTime(waitingTime);

		int val = Data.getTimeValue() + waitingTime;

		// Add time
		getPatient().setTimePatient(val);
		getObjectAvailable().setActualTime(getPatient().getTimePatient());
	}

	/**
	 * @return the patient
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * @param patient the patient to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * @return the data
	 */
	public Data getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Data data) {
		this.data = data;
	}

	/**
	 * @return the stateEvent
	 */
	public State getStateEvent() {
		return stateEvent;
	}

	/**
	 * @param stateEvent the stateEvent to set
	 */
	public void setStateEvent(State stateEvent) {
		this.stateEvent = stateEvent;
	}

	/**
	 * @return the objectAvailable
	 */
	public Ressource getObjectAvailable() {
		return objectAvailable;
	}

	/**
	 * @param objectAvailable the objectAvailable to set
	 */
	public void setObjectAvailable(Ressource objectAvailable) {
		this.objectAvailable = objectAvailable;
	}

	/**
	 * @return the ressources
	 */
	public List<?> getRessources() {
		return ressources;
	}

	/**
	 * @param ressources the ressources to set
	 */
	public void setRessources(List<?> ressources) {
		this.ressources = ressources;
	}

	/**
	 * @return the timeRessource
	 */
	public int getTimeRessource() {
		return timeRessource;
	}

	/**
	 * @param timeRessource the timeRessource to set
	 */
	public void setTimeRessource(int timeRessource) {
		this.timeRessource = timeRessource;
	}

	/**
	 * @return the waitingList
	 */
	public WaitingList getWaitingList() {
		return waitingList;
	}

	/**
	 * @param waitingList the waitingList to set
	 */
	public void setWaitingList(WaitingList waitingList) {
		this.waitingList = waitingList;
	}
}
