package events;

import java.util.List;

import back.Patient;
import back.Ressource;
import back.State;
import back.WaitingList;
import utils.Data;
import utils.Utils;

public abstract class Event {
	private Patient patient;
	private Data data;
	private State stateEvent;
	private int objectAvailable;
	private List<?> ressources;
	private int timeRessource;
	private WaitingList waitingList;

	public Event(Data d, Patient p) {
		data = d;
		patient = p;
		objectAvailable = -1;

	}

	public Event(Data d, Patient p, int objectAvailable) {
		data = d;
		patient = p;
		this.objectAvailable = objectAvailable;

	}

	public void startEvent() {
		synchronized (ressources) {
			objectAvailable = Utils.objectAvailable(ressources);
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
	
	public void addToWaitingList() {
		waitingList.add(patient, data.getTime());
	}
	public void sameEvent(Patient nextPatient) {
		
	}
	public void nextEvent() {
		
	}
	
	public void otherAction() {
		
	}
	
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

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public State getStateEvent() {
		return stateEvent;
	}

	public void setStateEvent(State stateEvent) {
		this.stateEvent = stateEvent;
	}

	public int getObjectAvailable() {
		return objectAvailable;
	}

	public void setObjectAvailable(int objectAvailable) {
		this.objectAvailable = objectAvailable;
	}

	public List<?> getRessources() {
		return ressources;
	}

	public void setRessources(List<?> ressources) {
		this.ressources = ressources;
	}

	public int getTimeRessource() {
		return timeRessource;
	}

	public void setTimeRessource(int timeRessource) {
		this.timeRessource = timeRessource;
	}

	public WaitingList getWaitingList() {
		return waitingList;
	}

	public void setWaitingList(WaitingList waitingList) {
		this.waitingList = waitingList;
	}
	
	

}
