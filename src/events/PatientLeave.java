package events;

import back.Gravity;
import back.Patient;
import back.State;
import utils.Data;

public class PatientLeave implements Runnable {
	private Patient patient;
	private Data data;

	public PatientLeave() {
		// TODO Auto-generated constructor stub
	}

	public PatientLeave(Data d, Patient p) {
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
			patient.setState(State.OUT, data.getTime());
			System.out.println(patient.toString());
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
				new Thread(new BedroomResearch(data, patient)).start();
			}
		}

	}

}
