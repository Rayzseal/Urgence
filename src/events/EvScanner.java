package events;

import back.Gravity;
import back.Patient;
import back.State;
import utils.Data;
import utils.EventsUtils;
import utils.Utils;

public class EvScanner implements Runnable {
	Patient patient;
	Data data;

	public EvScanner(Data d, Patient p) {
		patient = p;
		data = d;
	}

	public void startEventTest() {
		int scannerAvailable = -1;
		synchronized (data.getScanners()) {
			scannerAvailable = Utils.objectAvailable(data.getScanners());
			if (scannerAvailable >= 0) {
				if (patient.getGravity() == Gravity.C) {
					if(EventsUtils.patientAvailable(data, patient, State.ANALYSIS)) {
						data.getScanners().get(scannerAvailable).setState(State.OCCUPIED);
					}else {
						scannerAvailable = -1;
					}
				}else {
					data.getScanners().get(scannerAvailable).setState(State.OCCUPIED);
				}
				

			} else {
				synchronized (data.getWaitListPathC()) {
					if (patient.getGravity() == Gravity.C) {
						if(data.getWaitListPathC().get(State.WAITING).contains(patient)) {
							data.getWaitListPathC().get(State.SCANNER).add(patient, data.getTime());
						}
					}else {
						data.getWaitListPathC().get(State.SCANNER).add(patient, data.getTime());
					}
					
				}

			}

		}
		if (scannerAvailable >= 0) {
			System.out.println("run scanner : " + patient.getName());
			EvEndScanner e = new EvEndScanner(data, patient, scannerAvailable);
			e.run();
		}
	}

	/*
	 * public void startEvent() { int scannerAvailable = -1; synchronized
	 * (data.getScanners()) { scannerAvailable =
	 * Utils.objectAvailable(data.getScanners()); if (scannerAvailable >= 0) { if
	 * (patient.getGravity() == Gravity.C) { if
	 * (EventsUtils.patientAvailableScanner(data, patient)) {
	 * data.getScanners().get(scannerAvailable).setState(State.OCCUPIED); } else {
	 * scannerAvailable = -1; } } else {
	 * data.getScanners().get(scannerAvailable).setState(State.OCCUPIED); }
	 * 
	 * } else { synchronized (data.getWaitListScanner()) { if (patient.getGravity()
	 * == Gravity.C) { if (data.getWaitListPathC().contains(patient)) {
	 * data.getWaitListScanner().add(patient, data.getTime());
	 * System.out.println("liste attent scanner:" + patient); } } else {
	 * data.getWaitListScanner().add(patient, data.getTime()); }
	 * 
	 * }
	 * 
	 * } if (scannerAvailable >= 0) { System.out.println("run scanner : " +
	 * patient.getName()); EvEndScanner e = new EvEndScanner(data, patient,
	 * scannerAvailable); e.run(); } } }
	 */

	@Override
	public void run() {
		startEventTest();
	}

}
