package back;

import java.util.ArrayList;
import java.util.Random;

import events.PatientArrival;
import utils.ReadFile;
import utils.SortPatientArrival;
import utils.Utils;

public class Scheduler {
	private Data data;

	public Scheduler() {
		data = new Data();
	}

	public void scheduler() {
		// Utils.showList(data.getPatients());
		System.out.println("------------------");
		System.out.println("BEGIN");
		System.out.println("------------------");

		// While we still have patients to arrive or patients in treatment we wait, when
		// every patients has been treated we stop.
		while (data.getPatients().size() > 0 || data.getPatientsActive().size() != 0) {
			try {
				// 1000 == 1 seconde / reduceTime
				// with reduceTime = an accelerator
				data.setReduceTime(5000);
				Thread.sleep(1000 / data.getReduceTime());
				data.setTime(data.getTime() + 1);

				/*
				 * if(data.getTime()%5000==0) System.out.println("Time : "+ data.getTime());
				 */
				if (data.getPatients().size() > 0) {
					if (data.getPatients().get(0).getArrivalDate() == data.getTime()) { // Faire un while -> pb si 2
																						// patients arrive meme heure ?
						// Start patient
						System.out.println("Arrivée patient : " + data.getPatients().get(0));

						// Add to "over" list & remove patients from waiting list
						data.getPatientsActive().add(data.getPatients().get(0));

						new Thread(new PatientArrival(data, data.getPatients().get(0))).start();

						data.getPatients().remove(data.getPatients().get(0));

						// If patient active = terminate state then add to patientOver & remove from
						// patientActive

					}
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("------------------");
		System.out.println("END");
		System.out.println("------------------");
	}

	public void run() {
		scheduler();
	}
}
