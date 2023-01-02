package events;

import back.Patient;
import back.State;
import utils.Data;

public class EvEndPrescription extends Event implements Runnable{
	
	public EvEndPrescription(Data d, Patient p, int objectAvailable) {
		super(d, p, objectAvailable);
		setState();

	}
	public void setState() {
		setStateEvent(State.PRESCRIPTION);
		setRessources(getData().getDoctors());
		setTimeRessource(getData().getTimePrescription());
		setWaitingList(getData().getWaitListPrescription());
	}
	public void sameEvent(Patient nextPatient) {
		EvEndPrescription endPrescription = new EvEndPrescription(getData(), nextPatient, getObjectAvailable());
		endPrescription.run();
	}
	public void nextEvent() {
		new Thread(new EvPatientLeave(getData(), getPatient())).start();
	}
	
	@Override
	public void run() {
		endEvent();
	}
	

}
