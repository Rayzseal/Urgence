package events;

import back.Gravity;
import back.Patient;
import back.Receptionist;
import back.State;
import utils.Data;
import utils.EventsUtils;
import utils.Utils;

public class EndPatientArrival implements Runnable {
	private Patient patient;
	private Data data;
	private int receptionistAvailable;

	public EndPatientArrival() {
		patient = null;
		data = null;
	}

	public EndPatientArrival(Data d, Patient p, int rAvailable) {
		data = d;
		patient = p;
		receptionistAvailable = rAvailable;
	}

	@Override
	public void run() {
		try {
			// patient.setState(State.OCCUPIED, data.getTime());
			// patient.getListState().put(State.RECEPTION, data.getTime());
			patient.setState(State.RECEPTION, data.getTime());
			Thread.sleep(data.getTimeReception() / data.getReduceTime());

			// TODO assigne gravité
			//

			// patient.setGravity(Gravity.D); // TODEL for now
			patient.setGravity(EventsUtils.setGravity());

			patient.setState(State.AVAILABLE);
			new Thread(new BedroomResearch(data, patient)).start();

			Patient nextPatient = null;
			synchronized (data.getWaitListArrival()) {
				if (data.getWaitListArrival().size() > 0) {

					nextPatient = data.getWaitListArrival().get(0);
					data.getWaitListArrival().remove(nextPatient);
					int time = nextPatient.getListWaitTime().get(State.RECEPTION);
					time = data.getTime() - time;
					nextPatient.getListWaitTime().put(State.RECEPTION, time);
					nextPatient.addWaitingTime(time);

				} else {
					synchronized (data.getReceptionists()) {
						data.getReceptionists().get(receptionistAvailable).setState(State.AVAILABLE);
					}
				}
			}
			if (nextPatient != null) {
				EndPatientArrival e = new EndPatientArrival(data, nextPatient, receptionistAvailable);
				e.run();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
