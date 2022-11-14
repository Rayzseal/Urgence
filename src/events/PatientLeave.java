package events;

import back.Data;
import back.Patient;
import back.State;

public class PatientLeave implements Runnable{
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
		//unoccupied berdroom
		patient.setState(State.OUT);
		System.out.println(patient.toString() + " quitte l'hotpital.");
		data.getPatientsActive().remove(patient);
		//System.out.println(data.getPatientsActive());
	}

}
