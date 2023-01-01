package events;

import back.Gravity;
import back.Patient;
import back.State;
import utils.Data;
import utils.EventsUtils;

public class EndScanner implements Runnable {
	Patient patient;
	Data data;
	int scannerAvailable;

	public EndScanner(Data d, Patient p, int available) {
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
		synchronized (data.getWaitListScanner()) {
			if (data.getWaitListScanner().size() > 0) {

				nextPatient = data.getWaitListScanner().selectPatientFromArrayList();
				if (patient.getGravity() == Gravity.C) {
					if (EventsUtils.patientAvailable(data, patient)) {
						data.getWaitListScanner().remove(nextPatient, data.getTime());
					}else {
						
						nextPatient = chooseNextPatient();
						System.out.println(nextPatient);
						System.out.println("Contenu Liste scanner: "+data.getWaitListScanner());
					}
				} else {
					data.getWaitListScanner().remove(nextPatient, data.getTime());
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
			//System.out.println("scanner patient before chosing : "+patient.getName());
			Patient nextPatient = chooseNextPatient();
			if(nextPatient!=null) {
				EndScanner e = new EndScanner(data, nextPatient, scannerAvailable);
				e.run();
			}
			

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
