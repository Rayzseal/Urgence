package back;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import events.PatientArrival;
import utils.ReadFile;
import utils.SortPatientArrival;
import utils.Utils;

public class Data {

	private int nbOfBedrooms;
	// private int nbOfAvailableBedrooms;

	private int nbOfBlocs;
	// private int nbOfAvailableBlocs;

	private int nbOfScanners;
	// private int nbOfAvailableScanners;

	private int nbOfReceptionists;
	// private int nbOfAvailableReceptionists;

	private int nbOfPatients = 50;

	// --- Gaussian values --- //
	// value in seconds
	private int hourPeakArrivals = 54000; // average
	private int standardDeviation = 10000; // deviation

	private ArrayList<Bedroom> bedrooms;
	private ArrayList<Bloc> blocs;
	private ArrayList<Scanner> scanners;
	private ArrayList<Receptionist> receptionists;

	private ArrayList<Patient> patients;
	private ArrayList<Patient> patientsActive;
	private ArrayList<Patient> patientsOver;

	private ArrayList<Patient> waitListArrival;
	private ArrayList<Patient> waitListBedroom;
	private ArrayList<Patient> waitListAnalysis;
	private ArrayList<Patient> waitListBloc;
	private ArrayList<Patient> waitListPrescription;
	private ArrayList<Patient> waitListScanner;

	private int time;
	private int reduceTime;

	private int timeReception;
	private int timeScanner;
	private int timeAnalysis;
	private int timeBloc;
	private int timePrescription;

	public Data() {
		addReadFile();
		generateLists();
		generatePatients();
		generateWaitingList();

		this.patients.sort(new SortPatientArrival());
	}

	public void generateLists() {
		this.bedrooms = new ArrayList<>();
		this.blocs = new ArrayList<>();
		this.scanners = new ArrayList<>();
		this.receptionists = new ArrayList<>();
		this.patients = new ArrayList<>();
		this.patientsActive = new ArrayList<>();
		this.patientsOver = new ArrayList<>();

		for (int i = 0; i < nbOfBedrooms; i++)
			bedrooms.add(new Bedroom());

		for (int i = 0; i < nbOfBlocs; i++)
			blocs.add(new Bloc());

		for (int i = 0; i < nbOfScanners; i++)
			scanners.add(new Scanner());

		for (int i = 0; i < nbOfReceptionists; i++)
			receptionists.add(new Receptionist());
	}

	public void generatePatients() {
		this.time = 0;
		this.reduceTime = 300;

		int nbSecondsPerDay = 86400;

		for (int i = 0; i < nbOfPatients; i++) {
			Random r = new Random();

			double val = r.nextGaussian() * this.standardDeviation + this.hourPeakArrivals;
			int millisDelay = (int) Math.round(val);

			/**
			 * To make sure we don't generate a value > than the number of seconds per day.
			 * Even if the probability of this is very small, it can occurs. Since
			 * nextGaussian have theoretically nor maximum or minimum. In other words, 70%
			 * of values will be between standardDeviation +/- hourPeakArrivals.
			 **/
			while (millisDelay > nbSecondsPerDay) {
				val = r.nextGaussian() * this.standardDeviation + this.hourPeakArrivals;
				millisDelay = (int) Math.round(val);
			}

			int nbName = (int) (Math.random() * Utils.names.length);
			int nbSur = (int) (Math.random() * Utils.surnames.length);

			Patient p = new Patient(Utils.names[nbName], Utils.surnames[nbSur], millisDelay);

			this.patients.add(p);
		}
	}

	public void generateWaitingList() {
		waitListArrival = new ArrayList<Patient>();
		waitListBedroom = new ArrayList<Patient>();
	}

	public void addReadFile() {
		ReadFile f = new ReadFile();
		this.nbOfBedrooms = f.nbBedrooms;
		// this.nbOfAvailableBedrooms = f.nbBedrooms;
		this.nbOfBlocs = f.nbBloc;
		// this.nbOfAvailableBlocs = f.nbBloc;
		this.nbOfReceptionists = f.nbReceptionist;
		// this.nbOfAvailableReceptionists = f.nbReceptionist;
		this.nbOfScanners = f.nbScanner;
		// this.nbOfAvailableScanners = f.nbScanner;

		this.timeReception = f.timeReception;
		this.timeAnalysis = f.timeAnalysis;
		this.timeBloc = f.timeBloc;
		this.timeScanner = f.timeScanner;
		this.timePrescription = f.timePrescription;
	}

	/**
	 * setter and getter of class Data
	 * 
	 * @return
	 */
	public int getNbOfBedrooms() {
		return nbOfBedrooms;
	}

	public int getNbOfBlocs() {
		return nbOfBlocs;
	}

	public void setNbOfBlocs(int nbOfBlocs) {
		this.nbOfBlocs = nbOfBlocs;
	}

	public int getNbOfScanners() {
		return nbOfScanners;
	}

	public void setNbOfScanners(int nbOfScanners) {
		this.nbOfScanners = nbOfScanners;
	}

	public int getNbOfReceptionists() {
		return nbOfReceptionists;
	}

	public void setNbOfReceptionists(int nbOfReceptionists) {
		this.nbOfReceptionists = nbOfReceptionists;
	}

	public int getNbOfPatients() {
		return nbOfPatients;
	}

	public void setNbOfPatients(int nbOfPatients) {
		this.nbOfPatients = nbOfPatients;
	}

	public int getHourPeakArrivals() {
		return hourPeakArrivals;
	}

	public void setHourPeakArrivals(int hourPeakArrivals) {
		this.hourPeakArrivals = hourPeakArrivals;
	}

	public int getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(int standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public ArrayList<Bedroom> getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(ArrayList<Bedroom> bedrooms) {
		this.bedrooms = bedrooms;
	}

	public ArrayList<Bloc> getBlocs() {
		return blocs;
	}

	public void setBlocs(ArrayList<Bloc> blocs) {
		this.blocs = blocs;
	}

	public ArrayList<Scanner> getScanners() {
		return scanners;
	}

	public void setScanners(ArrayList<Scanner> scanners) {
		this.scanners = scanners;
	}

	public ArrayList<Receptionist> getReceptionists() {
		return receptionists;
	}

	public void setReceptionists(ArrayList<Receptionist> receptionists) {
		this.receptionists = receptionists;
	}

	public ArrayList<Patient> getPatients() {
		return patients;
	}

	public void setPatients(ArrayList<Patient> patients) {
		this.patients = patients;
	}

	public ArrayList<Patient> getPatientsActive() {
		return patientsActive;
	}

	public void setPatientsActive(ArrayList<Patient> patientsActive) {
		this.patientsActive = patientsActive;
	}

	public ArrayList<Patient> getPatientsOver() {
		return patientsOver;
	}

	public void setPatientsOver(ArrayList<Patient> patientsOver) {
		this.patientsOver = patientsOver;
	}

	public ArrayList<Patient> getWaitListArrival() {
		return waitListArrival;
	}

	public void setWaitListArrival(ArrayList<Patient> waitListArrival) {
		this.waitListArrival = waitListArrival;
	}

	public ArrayList<Patient> getWaitListBedroom() {
		return waitListBedroom;
	}

	public void setWaitListBedroom(ArrayList<Patient> waitListBedroom) {
		this.waitListBedroom = waitListBedroom;
	}

	public ArrayList<Patient> getWaitListAnalysis() {
		return waitListAnalysis;
	}

	public void setWaitListAnalysis(ArrayList<Patient> waitListAnalysis) {
		this.waitListAnalysis = waitListAnalysis;
	}

	public ArrayList<Patient> getWaitListBloc() {
		return waitListBloc;
	}

	public void setWaitListBloc(ArrayList<Patient> waitListBloc) {
		this.waitListBloc = waitListBloc;
	}

	public ArrayList<Patient> getWaitListPrescription() {
		return waitListPrescription;
	}

	public void setWaitListPrescription(ArrayList<Patient> waitListPrescription) {
		this.waitListPrescription = waitListPrescription;
	}

	public ArrayList<Patient> getWaitListScanner() {
		return waitListScanner;
	}

	public void setWaitListScanner(ArrayList<Patient> waitListScanner) {
		this.waitListScanner = waitListScanner;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getReduceTime() {
		return reduceTime;
	}

	public void setReduceTime(int reduceTime) {
		this.reduceTime = reduceTime;
	}

	public int getTimeReception() {
		return timeReception;
	}

	public void setTimeReception(int timeReception) {
		this.timeReception = timeReception;
	}

	public int getTimeScanner() {
		return timeScanner;
	}

	public void setTimeScanner(int timeScanner) {
		this.timeScanner = timeScanner;
	}

	public int getTimeAnalysis() {
		return timeAnalysis;
	}

	public void setTimeAnalysis(int timeAnalysis) {
		this.timeAnalysis = timeAnalysis;
	}

	public int getTimeBloc() {
		return timeBloc;
	}

	public void setTimeBloc(int timeBloc) {
		this.timeBloc = timeBloc;
	}

	public int getTimePrescription() {
		return timePrescription;
	}

	public void setTimePrescription(int timePrescription) {
		this.timePrescription = timePrescription;
	}

	public void setNbOfBedrooms(int nbOfBedrooms) {
		this.nbOfBedrooms = nbOfBedrooms;
	}

}
