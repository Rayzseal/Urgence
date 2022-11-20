package events;

import back.Patient;
import back.State;
import utils.Data;

public class EndBloc implements Runnable {
	Patient patient;
	Data data;
	int blocAvailable;

	public EndBloc() {
		// TODO Auto-generated constructor stub
	}

	public EndBloc(Data d, Patient p, int available) {
		patient = p;
		data = d;
		blocAvailable = available;
	}

	@Override
	public void run() {
		try {
			patient.setState(State.OCCUPIED, data.getTime());
			patient.getListState().put(State.BLOC, data.getTime());

			Thread.sleep(data.getTimeBloc() / data.getReduceTime());

			patient.setState(State.AVAILABLE, data.getTime());
			new Thread(new Prescription(data, patient)).start();

			if (data.getWaitListBloc().size() > 0) {
				patient = data.getWaitListBloc().selectPatientFromArrayList();
				data.getWaitListBloc().remove(patient, data.getTime());
				EndBloc e = new EndBloc(data, patient, blocAvailable);
				e.run();
			} else {
				synchronized (data.getBlocs()) {
					data.getBlocs().get(blocAvailable).setState(State.AVAILABLE);
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
