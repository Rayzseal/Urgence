package utils;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import back.Bedroom;
import back.Bloc;
import back.Doctor;
import back.Patient;
import back.Receptionist;
import back.Scanner;
import back.State;
import back.WaitingList;
import events.PatientArrival;

public class Data {
	
	private int nbOfPatients = 50;
	
	

	// --- Gaussian values --- //
	// value in seconds
	private int hourPeakArrivals = 54000; // average
	private int standardDeviation = 10000; // deviation

	private ArrayList<Bedroom> bedrooms;
	private ArrayList<Bloc> blocs;
	private ArrayList<Scanner> scanners;
	private ArrayList<Receptionist> receptionists;
	private ArrayList<Doctor> doctors;

	private ArrayList<Patient> patients;
	private ArrayList<Patient> patientsActive;
	private ArrayList<Patient> patientsOver;

	private ArrayList<Patient> waitListArrival;
	
	private WaitingList waitListBedroom;
	private WaitingList waitListAnalysis;
	private WaitingList waitListBloc;
	private WaitingList waitListPrescription;
	private WaitingList waitListScanner;

	private int time;
	private int reduceTime;

	private int timeReception;
	private int timeScanner;
	private int timeAnalysis;
	private int timeBloc;
	private int timePrescription;
	
	public Data() {
		generateData();
	}

	public Data(int nbPatient) {
		nbOfPatients = nbPatient;
		generateData();
	}
	
	public void generateData() {
		this.reduceTime = 300;
		DataFile dataFile = new DataFile();
		generateLists(dataFile);
		generatePatients();
		generateWaitingList();

		this.patients.sort(new SortPatientArrival());
	}

	public void generateLists(DataFile dataFile) {
		this.bedrooms = new ArrayList<>();
		this.blocs = new ArrayList<>();
		this.scanners = new ArrayList<>();
		this.receptionists = new ArrayList<>();
		this.doctors = new ArrayList<>();
		this.patients = new ArrayList<>();
		this.patientsActive = new ArrayList<>();
		this.patientsOver = new ArrayList<>();

		for (int i = 0; i < dataFile.getNbBedrooms(); i++)
			bedrooms.add(new Bedroom());

		for (int i = 0; i < dataFile.getNbBloc(); i++)
			blocs.add(new Bloc());

		for (int i = 0; i < dataFile.getNbScanner(); i++)
			scanners.add(new Scanner());

		for (int i = 0; i < dataFile.getNbReceptionist(); i++)
			receptionists.add(new Receptionist());
		for (int i = 0; i < dataFile.getNbDoctor(); i++)
			doctors.add(new Doctor());
	}

	public void generatePatients() {
		this.time = 0;
		
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

			//Patient p = new Patient(Utils.names[nbName], Utils.surnames[nbSur], millisDelay);

			this.patients.add(new Patient(Utils.names[nbName], Utils.surnames[nbSur], millisDelay));
		}
	}

	public void generateWaitingList() {
		waitListArrival = new ArrayList<Patient>();
		waitListBedroom = new WaitingList(State.BEDROOM);
		waitListPrescription = new WaitingList(State.PRESCRIPTION);
		waitListAnalysis = new WaitingList(State.ANALYSIS);
		waitListBloc = new WaitingList(State.BLOC);
		waitListScanner = new WaitingList(State.SCANNER);
	}

	/**
	 * setter and getter of class Data
	 * 
	 * @return
	 */

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

	public WaitingList getWaitListBedroom() {
		return waitListBedroom;
	}

	public void setWaitListBedroom(WaitingList waitListBedroom) {
		this.waitListBedroom = waitListBedroom;
	}

	public WaitingList getWaitListAnalysis() {
		return waitListAnalysis;
	}

	public void setWaitListAnalysis(WaitingList waitListAnalysis) {
		this.waitListAnalysis = waitListAnalysis;
	}

	public WaitingList getWaitListBloc() {
		return waitListBloc;
	}

	public void setWaitListBloc(WaitingList waitListBloc) {
		this.waitListBloc = waitListBloc;
	}

	public WaitingList getWaitListPrescription() {
		return waitListPrescription;
	}

	public void setWaitListPrescription(WaitingList waitListPrescription) {
		this.waitListPrescription = waitListPrescription;
	}

	public WaitingList getWaitListScanner() {
		return waitListScanner;
	}

	public void setWaitListScanner(WaitingList waitListScanner) {
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

	public ArrayList<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(ArrayList<Doctor> doctors) {
		this.doctors = doctors;
	}
	
}
