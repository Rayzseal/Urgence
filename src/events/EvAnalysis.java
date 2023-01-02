package events;

import java.util.ArrayList;

import model.Gravity;
import model.Patient;
import model.Ressource;
import model.State;
import utils.Data;
import utils.EventsUtils;
import utils.Utils;

public class EvAnalysis extends Event implements Runnable {
	//TODO

	public EvAnalysis(Data d, Patient p) {
		super(d, p);
		setState();

	}
	public void setState() {
		setStateEvent(State.ANALYSIS);
		setRessources(getData().getNurses());
		setTimeRessource(getData().getTimeAnalysis());
		setWaitingList(getData().getWaitListAnalysis());
	}
	public void nextEvent() {
		System.out.println("run analysis : "+getPatient().getName()+ getPatient().getSurname());
		EvEndAnalysis e = new EvEndAnalysis(getData(), getPatient(), getObjectAvailable());
		e.run();
	}
	public void startEvent() {
		setObjectAvailable(-1);
		synchronized (getRessources()) {
			setObjectAvailable(Utils.objectAvailable(getRessources()));
			if (getObjectAvailable() >= 0) {
				if (getPatient().getGravity() == Gravity.C) {
					if (EventsUtils.patientAvailable(getData(), getPatient(), State.SCANNER)) {
						((Ressource) getRessources().get(getObjectAvailable())).setState(State.OCCUPIED);
					}
					else {
						setObjectAvailable(-1);
					}
				} else {
					((Ressource) getRessources().get(getObjectAvailable())).setState(State.OCCUPIED);
				}
			}
			else {
				synchronized (getData().getWaitListPathC()) {
					if (getPatient().getGravity() == Gravity.C) {
						if (getData().getWaitListPathC().get(State.WAITING).contains(getPatient())) {
							getData().getWaitListPathC().get(State.ANALYSIS).add(getPatient(), getData().getTime());
							//System.out.println("waiting list anlysis : "+patient.getName()+ patient.getSurname());
						}
					}
					else {
						getData().getWaitListPathC().get(State.ANALYSIS).add(getPatient(), getData().getTime());
					}
					
				}
			}

		}
		if (getObjectAvailable() >= 0) {
			nextEvent();
		}
		

	}
	

	@Override
	public void run() {
		startEvent();
	}
		

}
