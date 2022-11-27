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
			patient.setState(State.BLOC, data.getTime());

			Thread.sleep(data.getTimeBloc() / data.getReduceTime());

			patient.setState(State.AVAILABLE);

			new Thread(new Prescription(data, patient)).start();
			
			Patient nextPatient = null;
			synchronized (data.getWaitListBloc()) {
				if (data.getWaitListBloc().size() > 0) {

					nextPatient = data.getWaitListBloc().selectPatientFromArrayList();
					data.getWaitListBloc().remove(nextPatient, data.getTime());
					

				} else {
					synchronized (data.getBlocs()) {
						data.getBlocs().get(blocAvailable).setState(State.AVAILABLE);
					}
				}
			}
			if(nextPatient!=null) {
				EndBloc e = new EndBloc(data, nextPatient, blocAvailable);
				e.run();
			}

		} catch (

		InterruptedException e) {
			e.printStackTrace();
		}

	}

}
