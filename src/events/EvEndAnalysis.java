package events;

import back.Gravity;
import back.Patient;
import back.State;
import utils.Data;
import utils.EventsUtils;

public class EvEndAnalysis implements Runnable{	
	private Patient patient;
	private Data data;
	private int nurseAvailable;
	
	public EvEndAnalysis(Data d, Patient p, int available) {
		patient = p;
		data = d;
		nurseAvailable = available;
	}
	
	public Patient chooseNextPatient() {
		Patient nextPatient = null;
		synchronized (data.getWaitListPathC().get(State.ANALYSIS)) {
			if (data.getWaitListPathC().get(State.ANALYSIS).size() > 0) {

				nextPatient = data.getWaitListPathC().get(State.ANALYSIS).selectPatientFromArrayList();
				
				if (nextPatient.getGravity() == Gravity.C) {
					if (EventsUtils.patientAvailable(data, nextPatient, State.SCANNER)) {
						data.getWaitListPathC().get(State.ANALYSIS).remove(nextPatient, data.getTime());
					}else {
						data.getWaitListPathC().get(State.ANALYSIS).remove(nextPatient);
						nextPatient = chooseNextPatient();
					}
				} else {
					data.getWaitListPathC().get(State.ANALYSIS).remove(nextPatient, data.getTime());
				}

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
				EvEndAnalysis e = new EvEndAnalysis(data, nextPatient, nurseAvailable);
				e.run();
			}

		} catch (

		InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
