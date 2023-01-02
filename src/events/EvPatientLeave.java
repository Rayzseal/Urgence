package events;

import model.Gravity;
import model.Patient;
import model.State;
import utils.Data;

public class EvPatientLeave implements Runnable {
	private Patient patient;
	private Data data;

	public EvPatientLeave() {
		// TODO Auto-generated constructor stub
	}

	public EvPatientLeave(Data d, Patient p) {
		data = d;
		patient = p;
	}

	@Override
	public void run() {

		synchronized (data.getBedrooms()) {
			if (patient.getBedroom() != null) {
				patient.getBedroom().setState(State.AVAILABLE);
				patient.setBedroom(null);
			}
			patient.getListState().put(State.OUT, data.getTime());
			
			synchronized (data.getPatientsActive()) {
				data.getPatientsActive().remove(patient);
			}
			synchronized (data.getPatientsOver()) {
				data.getPatientsOver().add(patient);
			}

			if (data.getWaitListBedroom().size() > 0) {
				patient = data.getWaitListBedroom().selectPatientFromArrayList();
				synchronized (data.getWaitListBedroom()) {					
					data.getWaitListBedroom().remove(patient, data.getTime());
				}
				new Thread(new EvBedroomResearch(data, patient)).start();
			}
		}

	}

}
