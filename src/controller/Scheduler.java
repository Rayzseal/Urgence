package controller;

import events.EvPatientArrival;
import events.EvPatientCriticArrival;
import model.Patient;
import model.State;
import utils.Data;
import utils.DataFile;

/**
 * The class Scheduler time the arrival of every patients
 *
 */
public class Scheduler {
	private Data data;
	/**
	 * constructor of scheduler and create an object Data with the default number of patients
	 */
	public Scheduler() {
		data = new Data();
	}
	/**
	 * constructor of scheduler and create an object Data with patients in the file nameFile
	 * @param nameFile
	 */
	public Scheduler(String nameFile) {
		data = new Data(DataFile.readSimulationFile(nameFile));
	}
	/**
	 * constructor of scheduler and create an object Data with the number of patients nbPatient
	 * @param nbPatient
	 */
	public Scheduler(int nbPatient) {
		data = new Data(nbPatient);
		
	}
	/**
	 * handle time and arrival of everyPatients
	 */
	public void scheduler() {
		// 1000 == 1 seconde / reduceTime
		// with reduceTime = an accelerator
		data.setReduceTime(1000);

		// Utils.showList(data.getPatients());
		System.out.println("------------------");
		System.out.println("BEGIN");
		System.out.println("------------------");

		// While we still have patients to arrive or patients in treatment we wait, when
		// every patients has been treated we stop.
		while (data.getPatients().size() > 0 || data.getPatientsActive().size() != 0) {
			try {
				// Thread.sleep(1000 / data.getReduceTime());
				Thread.sleep(1 / data.getReduceTime());
				data.setTime(data.getTime() + 1);

				// System.out.println("Time : "+ Utils.globalWaitingTime(data.getTime()));
				/*if (data.getTime() % 5000 == 0)
					System.out.println("Time : " + Utils.globalWaitingTime(data.getTime()));
				*/
				while (data.getPatients().size() > 0 && data.getPatients().get(0).getArrivalDate() == data.getTime()) {
					// Start patient
					// System.out.println("Arrivée patient : " + data.getPatients().get(0) +
					// Utils.globalWaitingTime(data.getTime()));

					// Add to "over" list & remove patients from waiting list
					Patient patient;
					synchronized (data.getPatients()) {
						patient = data.getPatients().get(0);
						patient.setState(State.ARRIVAL, data.getTime());
						synchronized (data.getPatientsActive()) {
							data.getPatientsActive().add(data.getPatients().get(0));
						}
						synchronized (data.getPatientsActive()) {
							data.getPatients().remove(patient);
						}
					}
					
					if (patient.isTypeArrival()) {
						new Thread(new EvPatientCriticArrival(data, patient)).start();						
					}else
						new Thread(new EvPatientArrival(data, patient)).start();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			

		}
		while(data.getPatientsOver().size()<data.getNbOfPatients()) {
			
		}
		//DataFile.writeSimulationFile("SimulationExemple2.csv",data.getPatientsOver());
		System.out.println("------------------");
		System.out.println("END");
		System.out.println("------------------");
	}
	/**
	 * runnable function
	 */
	public void run() {
		scheduler();
	}
	/**
	 * Setter and getter of the class
	 */

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
