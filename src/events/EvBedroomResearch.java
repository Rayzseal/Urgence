package events;

import back.Patient;
import back.State;
import utils.Data;
import utils.EventsUtils;

public class EvBedroomResearch extends Event implements Runnable {
	
	public EvBedroomResearch(Data d, Patient p) {
		super(d, p);
		setState();

	}
	public void setState() {
		setStateEvent(State.BEDROOM);
		setRessources(getData().getBedrooms());
		setTimeRessource(0);
		setWaitingList(getData().getWaitListBedroom());
	}
	
	@Override
	public void otherAction() {
		getPatient().setBedroom(getData().getBedrooms().get(getObjectAvailable()));
		getPatient().setState(State.BEDROOM, getData().getTime());
	}
	public void nextEvent() {
		//start of the path
		EventsUtils.pathChoice(getData(), getPatient());
	}
	
	@Override
	public void run() {
		startEvent();
	}


}
