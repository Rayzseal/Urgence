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
			//patient.setState(State.OCCUPIED, data.getTime());
			//patient.getListState().put(State.PRESCRIPTION, data.getTime());
			patient.setState(State.PRESCRIPTION, data.getTime());

			Thread.sleep(data.getTimePrescription()/data.getReduceTime()); 
			
			patient.setState(State.AVAILABLE);
			new Thread(new PatientLeave(data, patient)).start();
			
			Patient nextPatient = null;
			synchronized (data.getWaitListPrescription()) {
				if (data.getWaitListPrescription().size() > 0) {

					nextPatient = data.getWaitListPrescription().selectPatientFromArrayList();
					data.getWaitListPrescription().remove(nextPatient, data.getTime());
					

				} else {
					synchronized (data.getDoctors()) {
						data.getDoctors().get(doctortAvailable).setState(State.AVAILABLE);
					}
				}
			}
			if(nextPatient!=null) {
				EndPrescription e = new EndPrescription(data, nextPatient, doctortAvailable);
				e.run();
			}
				
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
