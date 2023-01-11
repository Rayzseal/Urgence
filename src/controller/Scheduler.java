package controller;

import events.EvBedroomResearch;
import events.EvBloc;
import events.EvPathC;
import events.EvPatientArrival;
import events.EvPatientCriticArrival;
import events.EvPrescription;
import model.Patient;
import model.State;
import utils.Data;
import utils.DataFile;
import utils.EventsUtils;

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
		//DataFile.writeSimulationFile("SimulationExemple.csv",data);

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

			//Time goes on
			Data.setTimeValue(Data.getTimeValue()+1);

			while (data.getPatients().size() > 0 && data.getPatients().get(0).getArrivalDate() == Data.getTimeValue()) {
				// Start patient

				// Add to "over" list & remove patients from waiting list
				Patient patient;

				//Get first patient in waiting list
				patient = data.getPatients().get(0);

				//test to prevent modification of the gravity data from Simulation file
				if(patient.getGravity() == null)
					patient.setGravity(EventsUtils.setGravity());

				if (patient.getArrivalDate() == Data.getTimeValue()) {
					patient.setState(State.ARRIVAL, Data.getTimeValue());
					data.getPatientsActive().add(data.getPatients().get(0));
					data.getPatients().remove(patient);
				}

				if (patient.isTypeArrival())  {
					new EvPatientCriticArrival(data, patient).run();
				}
				else
					data.getWaitListArrival().add(patient);
			}


			setPatientToPathNonCritical(data);
		}

		System.out.println("------------------");
		System.out.println("END");
		System.out.println("------------------");
		//DataFile.writeSimulationFile("PatientExemple.csv",data.getPatientsOver());
		//DataFile.writeSimulationFile("SimulationEnd.csv",data);
	}
	/**
	 * method to run events of the simulation
	 * @param data Data
	 */
	public void setPatientToPathNonCritical(Data data) {
		new EvPatientArrival(data, null).run();
		
		new EvBedroomResearch(data, null).run();
		
		new EvBloc(data, null).run();
		
		new EvPathC(data, null).run();

		new EvPrescription(data, null).run();
	}

	/**
	 * runnable function
	 */
	public void run() {
		scheduler();
	}

	/**
	 * @return the data
	 */
	public Data getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Data data) {
		this.data = data;
	}

}
