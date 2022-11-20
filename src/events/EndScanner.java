package events;

import back.Patient;
import back.State;
import utils.Data;

public class EndScanner implements Runnable {
	Patient patient;
	Data data;
	int scannerAvailable;

	public EndScanner() {
		// TODO Auto-generated constructor stub
	}

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
			// algo parcours C
			break;
		default:
			System.out.println(patient.getGravity());
			throw new IllegalArgumentException("The patient shouldn't be here");
		}

	}

	@Override
	public void run() {
		try {
			patient.setState(State.OCCUPIED, data.getTime());
			patient.getListState().put(State.SCANNER, data.getTime());

			Thread.sleep(data.getTimeScanner() / data.getReduceTime());

			patient.setState(State.AVAILABLE, data.getTime());

			path();

			if (data.getWaitListScanner().size() > 0) {
				patient = data.getWaitListScanner().selectPatientFromArrayList();
				data.getWaitListScanner().remove(patient, data.getTime());

				EndScanner e = new EndScanner(data, patient, scannerAvailable);
				e.run();
			} else {
				synchronized (data.getScanners()) {
					data.getScanners().get(scannerAvailable).setState(State.AVAILABLE);
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
