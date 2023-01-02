package events;

import back.Gravity;
import back.Patient;
import back.State;
import utils.Data;
import utils.EventsUtils;

public class EvEndScanner implements Runnable {
	Patient patient;
	Data data;
	int scannerAvailable;

	public EvEndScanner(Data d, Patient p, int available) {
		patient = p;
		data = d;
		scannerAvailable = available;
	}

	public void path() {
		switch (patient.getGravity()) {
		case B:
			new Thread(new EvBloc(data, patient)).start();
			break;
		case C:
			new Thread(new EvEndPathC(data, patient)).start();
			break;
		default:
			System.out.println(patient.getGravity());
			throw new IllegalArgumentException("The patient shouldn't be here");
		}

	}
	
	public Patient chooseNextPatient() {
		Patient nextPatient = null;
		synchronized (data.getWaitListPathC().get(State.SCANNER)) {
			if (data.getWaitListPathC().get(State.SCANNER).size() > 0) {

				nextPatient = data.getWaitListPathC().get(State.SCANNER).selectPatientFromArrayList();
				if (nextPatient.getGravity() == Gravity.C) {
					if (EventsUtils.patientAvailable(data, nextPatient, State.ANALYSIS)) {
						data.getWaitListPathC().get(State.SCANNER).remove(nextPatient, data.getTime());
					}else {
						data.getWaitListPathC().get(State.SCANNER).remove(nextPatient);
						nextPatient = chooseNextPatient();
					}
				} else {
					data.getWaitListPathC().get(State.SCANNER).remove(nextPatient, data.getTime());
				}

			} else {
				synchronized (data.getScanners()) {
					data.getScanners().get(scannerAvailable).setState(State.AVAILABLE);
				}
			}
		}
		return nextPatient;
		
	}

	@Override
	public void run() {
		try {
			patient.setState(State.SCANNER, data.getTime());

			Thread.sleep(data.getTimeScanner() / data.getReduceTime());

			patient.setState(State.AVAILABLE, data.getTime());
			
			path();
			
			Patient nextPatient = chooseNextPatient();
			
			if(nextPatient!=null) {
				System.out.println("scanner patient next: "+nextPatient.getName());
				EvEndScanner e = new EvEndScanner(data, nextPatient, scannerAvailable);
				e.run();
			}
			

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
