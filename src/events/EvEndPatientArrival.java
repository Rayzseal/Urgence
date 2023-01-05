package events;

import model.Patient;
import model.Ressource;
import model.State;
import utils.Data;
import utils.EventsUtils;

public class EvEndPatientArrival extends Event implements Runnable {
	public EvEndPatientArrival(Data d, Patient p, int objectAvailable) {
		super(d, p, objectAvailable);
		setState();

	}
	public void setState() {
		setStateEvent(State.RECEPTION);
		setRessources(getData().getReceptionists());
		setTimeRessource(getData().getTimeReception());
		setWaitingList(null);
	}
	public void sameEvent(Patient nextPatient) {
		EvEndPatientArrival e = new EvEndPatientArrival(getData(), nextPatient, getObjectAvailable());
		e.run();
	}
	public void nextEvent() {
		//to prevent modification of the data from Simulation file
		if(getPatient().getGravity() == null)
			getPatient().setGravity(EventsUtils.setGravity());
		new Thread(new EvBedroomResearch(getData(), getPatient())).start();
	}
	
	
	public Patient getNextPatient() {
		Patient nextPatient = null;
		synchronized (getData().getWaitListArrival()) {
			if (getData().getWaitListArrival().size() > 0) {

				nextPatient = getData().getWaitListArrival().get(0);
				getData().getWaitListArrival().remove(nextPatient);
				int time = nextPatient.getListWaitTime().get(getStateEvent());
				time = getData().getTime() - time;
				nextPatient.getListWaitTime().put(getStateEvent(), time);
				nextPatient.addWaitingTime(time);

			} else {
				synchronized (getRessources()) {
					((Ressource) getRessources().get(getObjectAvailable())).setState(State.AVAILABLE);
				}
			}
		}
		return nextPatient;
	}

	@Override
	public void run() {
		endEvent();

	}

}
