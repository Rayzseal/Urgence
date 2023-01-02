package events;

import model.Patient;
import model.State;
import utils.Data;

public class EvEndBloc extends Event implements Runnable {
	
	public EvEndBloc(Data d, Patient p, int objectAvailable) {
		super(d, p, objectAvailable);
		setState();

	}
	public void setState() {
		setStateEvent(State.BLOC);
		setRessources(getData().getBlocs());
		setTimeRessource(getData().getTimeBloc());
		setWaitingList(getData().getWaitListBloc());
	}
	public void sameEvent(Patient nextPatient) {
		EvEndBloc e = new EvEndBloc(getData(), nextPatient, getObjectAvailable());
		e.run();
	}
	public void nextEvent() {
		new Thread(new EvPrescription(getData(), getPatient())).start();
	}
	
	@Override
	public void run() {
		endEvent();
	}

	/*@Override
	public void run() {
		try {
			patient.setState(State.BLOC, data.getTime());

			Thread.sleep(data.getTimeBloc() / data.getReduceTime());

			patient.setState(State.AVAILABLE);

			new Thread(new EvPrescription(data, patient)).start();
			
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
				EvEndBloc e = new EvEndBloc(data, nextPatient, blocAvailable);
				e.run();
			}

		} catch (

		InterruptedException e) {
			e.printStackTrace();
		}

	}*/

}
