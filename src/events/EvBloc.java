package events;

import back.Patient;
import back.State;
import utils.Data;

public class EvBloc extends Event implements Runnable{
	public EvBloc(Data d, Patient p) {
		super(d, p);
		setState();

	}
	public void setState() {
		setStateEvent(State.BLOC);
		setRessources(getData().getBlocs());
		setTimeRessource(getData().getTimeBloc());
		setWaitingList(getData().getWaitListBloc());
	}
	public void sameEvent() {
		
	}
	public void nextEvent() {
		EvEndBloc e = new EvEndBloc(getData(), getPatient(), getObjectAvailable());
		e.run();
	}

	@Override
	public void run() {
		startEvent();
		
	}
	

}
