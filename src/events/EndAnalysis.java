package events;

import back.Patient;
import back.State;
import utils.Data;

public class EndAnalysis implements Runnable{	
	private Patient patient;
	private Data data;
	private int nurseAvailable;
	
	public EndAnalysis(Data d, Patient p, int available) {
		patient = p;
		data = d;
		nurseAvailable = available;
	}

	@Override
	public void run() {
		try {
			patient.setState(State.ANALYSIS, data.getTime());

			Thread.sleep(data.getTimeAnalysis() / data.getReduceTime());

			patient.setState(State.AVAILABLE);

			new Thread(new ParcoursC(data, patient)).start();
			
			Patient nextPatient = null;
			synchronized (data.getWaitListAnalysis()) {
				if (data.getWaitListAnalysis().size() > 0) {

					nextPatient = data.getWaitListAnalysis().selectPatientFromArrayList();
					data.getWaitListAnalysis().remove(nextPatient, data.getTime());
					

				} else {
					synchronized (data.getNurses()) {
						data.getNurses().get(nurseAvailable).setState(State.AVAILABLE);
					}
				}
			}
			if(nextPatient!=null) {
				EndAnalysis e = new EndAnalysis(data, nextPatient, nurseAvailable);
				e.run();
			}

		} catch (

		InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
