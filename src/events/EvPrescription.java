package events;

import model.Patient;
import model.State;
import utils.Data;

public class EvPrescription extends Event implements Runnable{
	
	public EvPrescription(Data d, Patient p) {
		super(d, p);
		setState();

	}
	public void setState() {
		setStateEvent(State.PRESCRIPTION);
		setRessources(getData().getDoctors());
		setTimeRessource(getData().getTimePrescription());
		setWaitingList(getData().getWaitListPrescription());
	}
	public void nextEvent() {
		EvEndPrescription endPrescription = new EvEndPrescription(getData(), getPatient(), getObjectAvailable());
		endPrescription.run();
	}
	
	@Override
	public void run() {
		startEvent();
	}


}
