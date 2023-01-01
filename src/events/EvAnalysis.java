package events;

import java.util.ArrayList;

import back.Gravity;
import back.Patient;
import back.State;
import utils.Data;
import utils.EventsUtils;
import utils.Utils;

public class EvAnalysis implements Runnable {
	Patient patient;
	Data data;

	public EvAnalysis(Data d, Patient p) {
		patient = p;
		data = d;
	}

	@Override
	public void run() {
		int nurseAvailable = -1;
		synchronized (data.getNurses()) {
			nurseAvailable = Utils.objectAvailable(data.getNurses());
			if (nurseAvailable >= 0) {
				if (patient.getGravity() == Gravity.C) {
					if (EventsUtils.patientAvailableAnalysis(data, patient)) {
						data.getNurses().get(nurseAvailable).setState(State.OCCUPIED);
					}
					else {
						nurseAvailable = -1;
					}
				} else {
					data.getNurses().get(nurseAvailable).setState(State.OCCUPIED);
				}
			}
			else {
				synchronized (data.getWaitListAnalysis()) {
					if (patient.getGravity() == Gravity.C) {
						if (data.getWaitListPathC().contains(patient)) {
							data.getWaitListAnalysis().add(patient, data.getTime());
							System.out.println("waiting list anlysis : "+patient.getName()+ patient.getSurname());
						}
					}
					else {
						data.getWaitListAnalysis().add(patient, data.getTime());
					}
					
				}
			}

		}
		if (nurseAvailable >= 0) {
			System.out.println("run analysis : "+patient.getName()+ patient.getSurname());
			EndAnalysis e = new EndAnalysis(data, patient, nurseAvailable);
			e.run();
		}
		

	}

}
