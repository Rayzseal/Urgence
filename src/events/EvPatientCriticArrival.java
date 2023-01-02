package events;

import back.Bedroom;
import back.Gravity;
import back.Patient;
import back.State;
import utils.Data;
import utils.EventsUtils;

public class EvPatientCriticArrival implements Runnable{
	private Patient patient;
	private Data data;

	public EvPatientCriticArrival(Data d, Patient p) {
		data = d;
		patient = p;
	}
	
	@Override
	public void run() {
		patient.setBedroom(new Bedroom());
		patient.setGravity(Gravity.A);
		patient.setState(State.BEDROOM, data.getTime());
		EventsUtils.pathChoice(data, patient);
	}

}
