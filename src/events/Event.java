package events;

import java.util.List;

import model.Patient;
import model.Ressource;
import model.State;
import model.WaitingList;
import utils.Data;
import utils.EventsUtils;
/**
 * Abstract method to every event
 */
public abstract class Event {
	private Patient patient;
	private Data data;
	private State stateEvent;
	private int objectAvailable;
	private List<?> ressources;
	private int timeRessource;
	private WaitingList waitingList;
	/**
	 * constructor of an event
	 * @param d Data
	 * @param p Patient
	 */
	public Event(Data d, Patient p) {
		data = d;
		patient = p;
		objectAvailable = -1;

	}
	/**
	 * constructor of an event
	 * @param d Data
	 * @param p Patient
	 * @param objectAvailable index in the list of ressources used
	 */
	public Event(Data d, Patient p, int objectAvailable) {
		data = d;
		patient = p;
		this.objectAvailable = objectAvailable;

	}
	/**
	 * The method verifies if required ressources are available, patient continues his path if it is
	 * or he starts waiting in the waiting list
	 */
	public void startEvent() {
		synchronized (ressources) {
			objectAvailable = EventsUtils.objectAvailable(ressources);
			if (objectAvailable >= 0) {				
				((Ressource) ressources.get(objectAvailable)).setState(State.OCCUPIED);
				otherAction();
			}

			else {
				synchronized (waitingList) {
					addToWaitingList();

				}
			}
		}
		if (objectAvailable >= 0) {
			
			nextEvent();
		}

	}
	/**
	 * The method pause for the time timeRessource then search the nextPatient
	 * patient continue is path with the method nextEvent() 
	 */
	public void endEvent() {
		try {
			patient.setState(stateEvent, data.getTime());

			Thread.sleep(timeRessource/data.getReduceTime()); 
			
			patient.setState(State.AVAILABLE);
			nextEvent();
			Patient nextPatient = getNextPatient();
			if(nextPatient!=null) {
				sameEvent(nextPatient);
			}
				
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * add to the waiting list defined
	 */
	public void addToWaitingList() {
		waitingList.add(patient, data.getTime());
	}
	/**
	 * method to be override so nextPatient continue on the same event
	 * @param nextPatient Patient
	 */
	public void sameEvent(Patient nextPatient) {
		
	}
	/**
	 * method to be override so patient continue on the next event
	 */
	public void nextEvent() {
		
	}
	/**
	 * method to be override so events can do other action when they set a ressources to State.OCCUPIED
	 */
	public void otherAction() {
		
	}
	/**
	 * The method verifies if a patient is waiting in the waitingList
	 * @return nextPatient or null if no patient is waiting
	 */
	public Patient getNextPatient() {
		Patient nextPatient = null;
		synchronized (waitingList) {
			if (waitingList.size() > 0) {

				nextPatient = waitingList.selectPatientFromArrayList();
				waitingList.remove(nextPatient, data.getTime());
				

			} else {
				synchronized (ressources) {
					((Ressource) ressources.get(objectAvailable)).setState(State.AVAILABLE);
				}
			}
		}
		return nextPatient;
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
	public int getObjectAvailable() {
		return objectAvailable;
	}
	/**
	 * @param objectAvailable the objectAvailable to set
	 */
	public void setObjectAvailable(int objectAvailable) {
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
