package events;

import back.Gravity;
import back.Patient;
import back.State;
import utils.Data;
import utils.EventsUtils;

public class EndAnalysis implements Runnable{	
	private Patient patient;
	private Data data;
	private int nurseAvailable;
	
	public EndAnalysis(Data d, Patient p, int available) {
		patient = p;
		data = d;
		nurseAvailable = available;
	}
	
	public Patient chooseNextPatient() {
		Patient nextPatient = null;
		synchronized (data.getWaitListAnalysis()) {
			if (data.getWaitListAnalysis().size() > 0) {

				nextPatient = data.getWaitListAnalysis().selectPatientFromArrayList();
				
				/*if (patient.getGravity() == Gravity.C) {
					if (EventsUtils.patientAvailableAnalysis(data, nextPatient)) {
						data.getWaitListAnalysis().remove(nextPatient, data.getTime());
					}else {
						nextPatient = chooseNextPatient();
						System.out.println(nextPatient);
						System.out.println("Contenu Liste analysis : "+data.getWaitListAnalysis());
					}
				} else {
					data.getWaitListAnalysis().remove(nextPatient, data.getTime());
				}*/
				if (patient.getGravity() == Gravity.C) 
					EventsUtils.patientAvailableAnalysis(data, nextPatient);
				data.getWaitListAnalysis().remove(nextPatient, data.getTime());

			} else {
				synchronized (data.getNurses()) {
					data.getNurses().get(nurseAvailable).setState(State.AVAILABLE);
				}
			}
		}
		return nextPatient;
	}

	@Override
	public void run() {
		try {
			patient.setState(State.ANALYSIS, data.getTime());

			Thread.sleep(data.getTimeAnalysis() / data.getReduceTime());

			patient.setState(State.AVAILABLE);

			new Thread(new EvEndPathC(data, patient)).start();
			
			Patient nextPatient = chooseNextPatient();
			
			if(nextPatient!=null) {
				System.out.println("analysis patient next : "+nextPatient.getName());
				EndAnalysis e = new EndAnalysis(data, nextPatient, nurseAvailable);
				e.run();
			}

		} catch (

		InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
