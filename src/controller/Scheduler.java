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
	 * constructor of scheduler and create an object Data with the default number of
	 * patients
	 */
	public Scheduler() {
		data = new Data();
	}

	/**
	 * constructor of scheduler and create an object Data with patients in the file
	 * nameFile
	 * 
	 * @param nameFile String
	 */
	public Scheduler(String nameFile) {
		data = new Data(DataFile.readPatientFile(nameFile));
	}

	/**
	 * constructor of scheduler and create an object Data with the number of
	 * patients nbPatient
	 * 
	 * @param nbPatient int
	 */
	public Scheduler(int nbPatient) {
		data = new Data(nbPatient);
		// DataFile.writeSimulationFile("SimulationExemple.csv",data);

	}

	/**
	 * constructor of scheduler with the objects data
	 * 
	 * @param data Data
	 */
	public Scheduler(Data data) {
		this.data = data;

	}

	/**
	 * handle time and arrival of everyPatients
	 */
	public void scheduler() {
		System.out.println("------------------");
		System.out.println("BEGIN");
		System.out.println("------------------");

		// While we still have patients to arrive or patients in treatment we wait, when
		// every patients has been treated we stop.
		while (data.getPatients().size() > 0 || data.getPatientsActive().size() != 0) {
			try {
				Thread.sleep(1 / data.getReduceTime());
				data.setTime(data.getTime() + 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (data.getPatients().size() > 0 && data.getPatients().get(0).getArrivalDate() == data.getTime()) {
				// Start patient

				// Add to "over" list & remove patients from waiting list
				Patient patient;
				synchronized (data.getPatients()) {
					patient = data.getPatients().get(0);
					patient.setState(State.ARRIVAL, data.getTime());
					synchronized (data.getPatientsActive()) {
						data.getPatientsActive().add(data.getPatients().get(0));
						data.getPatients().remove(patient);
					}
				}

				if (patient.isTypeArrival()) {
					new Thread(new EvPatientCriticArrival(data, patient)).start();
				} else
					new Thread(new EvPatientArrival(data, patient)).start();
			}

		}
		System.out.println("------------------");
		System.out.println("END");
		System.out.println("------------------");
		// DataFile.writeSimulationFile("PatientExemple.csv",data.getPatientsOver());
		//DataFile.writeSimulationFile("SimulationEnd.csv",data);
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
