package events;

import back.Gravity;
import back.Patient;
import back.State;
import utils.Data;

public class EndPrescription implements Runnable{
	private Patient patient;
	private Data data;
	private int doctortAvailable;

	public EndPrescription() {
		// TODO Auto-generated constructor stub
	}
	
	public EndPrescription(Data d, Patient p, int doctortAvailable) {
		data = d;
		patient = p;
		this.doctortAvailable = doctortAvailable;
	
}
	
	@Override
	public void run() {
		try {
			patient.setState(State.OCCUPIED, data.getTime());
			patient.getListState().put(State.PRESCRIPTION, data.getTime());

			Thread.sleep(data.getTimePrescription()/data.getReduceTime()); 
			
			patient.setState(State.AVAILABLE, data.getTime());
			new Thread(new PatientLeave(data, patient)).start();
			
			if(data.getWaitListPrescription().size() > 0) {
				patient = data.getWaitListPrescription().selectPatientFromArrayList();
				data.getWaitListPrescription().remove(patient, data.getTime());
				EndPrescription prescription = new EndPrescription(data, patient, doctortAvailable);
				prescription.run();
			}else {
				synchronized (data.getDoctors()) {
					data.getDoctors().get(doctortAvailable).setState(State.AVAILABLE);
			    }
			}
				
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
