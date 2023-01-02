package events;

import model.Gravity;
import model.Patient;
import model.Receptionist;
import model.Ressource;
import model.State;
import utils.Data;
import utils.EventsUtils;
import utils.Utils;

public class EvEndPatientArrival extends Event implements Runnable {
	/*private Patient patient;
	private Data data;
	private int receptionistAvailable;

	public EvEndPatientArrival() {
		patient = null;
		data = null;
	}

	public EvEndPatientArrival(Data d, Patient p, int rAvailable) {
		data = d;
		patient = p;
		receptionistAvailable = rAvailable;
	}*/
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
		/*try {
			patient.setState(State.RECEPTION, data.getTime());
			
			Thread.sleep(data.getTimeReception() / data.getReduceTime());

			patient.setGravity(EventsUtils.setGravity());

			patient.setState(State.AVAILABLE);
			new Thread(new EvBedroomResearch(data, patient)).start();

			Patient nextPatient = getNextPatient();
			
			if (nextPatient != null) {
				EvEndPatientArrival e = new EvEndPatientArrival(data, nextPatient, receptionistAvailable);
				e.run();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

	}

}
