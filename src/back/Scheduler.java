package back;

import java.util.Random;

import events.PatientArrival;
import utils.Data;
import utils.SortPatientArrival;
import utils.Utils;

public class Scheduler {
	protected Data data;

	public Scheduler() {
		data = new Data();
		// 1000 == 1 seconde / reduceTime
		// with reduceTime = an accelerator
		data.setReduceTime(100);
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
				//Thread.sleep(1000 / data.getReduceTime());
				Thread.sleep(1 / data.getReduceTime());
				data.setTime(data.getTime() + 1);

				//System.out.println("Time : "+ Utils.globalWaitingTime(data.getTime()));
				 //if(data.getTime()%5000==0) System.out.println("Time : "+ Utils.globalWaitingTime(data.getTime()));
				 
				while (data.getPatients().size() > 0 && data.getPatients().get(0).getArrivalDate() == data.getTime()) { 
					// Start patient
					//System.out.println("Arrivée patient : " + data.getPatients().get(0) + Utils.globalWaitingTime(data.getTime()));

					// Add to "over" list & remove patients from waiting list
					Patient patient = data.getPatients().get(0);
					synchronized (data.getPatientsActive()) {
						data.getPatientsActive().add(data.getPatients().get(0));
					}
					synchronized (data.getPatientsActive()) {
						data.getPatients().remove(patient);
					}
					new Thread(new PatientArrival(data, patient)).start();
				}

			} catch (InterruptedException e) {
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

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
}
