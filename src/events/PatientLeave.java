package events;

import back.Data;
import back.Gravity;
import back.Patient;
import back.State;

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
			if(patient.getBedroom() != null) {
				patient.getBedroom().setState(State.AVAILABLE);
				patient.setBedroom(null);
			}
			patient.setState(State.OUT);
			System.out.println(patient.toString() + " quitte l'hotpital.");
			data.getPatientsActive().remove(patient);
			synchronized (data.getWaitListBedroom()) {
				if (data.getWaitListBedroom().size() > 0) {
					patient = (Patient) data.getWaitListBedroom().selectPatientFromArrayList();
					data.getWaitListBedroom().remove(patient);//verif temps
					new Thread(new BedroomResearch(data, patient)).start();
				}
			}
		}

		

	}

}
