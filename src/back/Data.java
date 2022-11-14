package back;

import java.util.ArrayList;
import java.util.Random;

import events.PatientArrival;
import utils.ReadFile;
import utils.SortPatientArrival;
import utils.Utils;

public class Data {
		
	private int nbOfBedrooms;
	private int nbOfAvailableBedrooms;
	
	private int nbOfBlocs;
	private int nbOfAvailableBlocs;
	
	private int nbOfScanners;
	private int nbOfAvailableScanners;
	
	private int nbOfReceptionists;
	private int nbOfAvailableReceptionists;
	
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
	
	WaitingList waitListArrival;
	WaitingList waitListA;
	WaitingList waitListB;
	WaitingList waitListC;
	WaitingList waitListD;
	
	private int time;
	private int reduceTime;
	
	
	public Data() {
		addReadFile();
		generateLists();
		generatePatients();
		generateWaitingList();
			
		this.patients.sort(new SortPatientArrival());
	}
	
	
	public int getNbOfBedrooms() {
		return nbOfBedrooms;
	}


	public void setNbOfBedrooms(int nbOfBedrooms) {
		this.nbOfBedrooms = nbOfBedrooms;
	}


	public int getNbOfAvailableBedrooms() {
		return nbOfAvailableBedrooms;
	}


	public void setNbOfAvailableBedrooms(int nbOfAvailableBedrooms) {
		this.nbOfAvailableBedrooms = nbOfAvailableBedrooms;
	}


	public int getNbOfBlocs() {
		return nbOfBlocs;
	}


	public void setNbOfBlocs(int nbOfBlocs) {
		this.nbOfBlocs = nbOfBlocs;
	}


	public int getNbOfAvailableBlocs() {
		return nbOfAvailableBlocs;
	}


	public void setNbOfAvailableBlocs(int nbOfAvailableBlocs) {
		this.nbOfAvailableBlocs = nbOfAvailableBlocs;
	}


	public int getNbOfScanners() {
		return nbOfScanners;
	}


	public void setNbOfScanners(int nbOfScanners) {
		this.nbOfScanners = nbOfScanners;
	}


	public int getNbOfAvailableScanners() {
		return nbOfAvailableScanners;
	}


	public void setNbOfAvailableScanners(int nbOfAvailableScanners) {
		this.nbOfAvailableScanners = nbOfAvailableScanners;
	}


	public int getNbOfReceptionists() {
		return nbOfReceptionists;
	}


	public void setNbOfReceptionists(int nbOfReceptionists) {
		this.nbOfReceptionists = nbOfReceptionists;
	}


	public int getNbOfAvailableReceptionists() {
		return nbOfAvailableReceptionists;
	}


	public void setNbOfAvailableReceptionists(int nbOfAvailableReceptionists) {
		this.nbOfAvailableReceptionists = nbOfAvailableReceptionists;
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


	public WaitingList getWaitListArrival() {
		return waitListArrival;
	}


	public void setWaitListArrival(WaitingList waitListArrival) {
		this.waitListArrival = waitListArrival;
	}


	public WaitingList getWaitListA() {
		return waitListA;
	}


	public void setWaitListA(WaitingList waitListA) {
		this.waitListA = waitListA;
	}


	public WaitingList getWaitListB() {
		return waitListB;
	}


	public void setWaitListB(WaitingList waitListB) {
		this.waitListB = waitListB;
	}


	public WaitingList getWaitListC() {
		return waitListC;
	}


	public void setWaitListC(WaitingList waitListC) {
		this.waitListC = waitListC;
	}


	public WaitingList getWaitListD() {
		return waitListD;
	}


	public void setWaitListD(WaitingList waitListD) {
		this.waitListD = waitListD;
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
		this.reduceTime = 300	;
		
		int nbSecondsPerDay = 86400;
		
		for (int i = 0; i < nbOfPatients; i++) {
			Random r = new Random();
			
			double val = r.nextGaussian() * this.standardDeviation + this.hourPeakArrivals;
			int millisDelay = (int) Math.round(val);
			
			/**
			 * To make sure we don't generate a value > than the number of seconds per day. 
			 * Even if the probability of this is very small, it can occurs. 
			 * Since nextGaussian have theoretically nor maximum or minimum. 
			 * In other words, 70% of values will be between standardDeviation +/- hourPeakArrivals.
			**/ 
			while (millisDelay > nbSecondsPerDay) {
				val = r.nextGaussian() * this.standardDeviation + this.hourPeakArrivals;
				millisDelay = (int) Math.round(val);
			}
						
			int nbName = (int) (Math.random() * Utils.names.length);
			int nbSur = (int) (Math.random() * Utils.surnames.length);

			Patient p = new Patient(Utils.names[nbName], Utils.surnames[nbSur],millisDelay);
			
			this.patients.add(p);
		}
	}
	
	public void generateWaitingList(){
		waitListArrival = new WaitingList();
		waitListA = new WaitingList();
		waitListB = new WaitingList();
		waitListC = new WaitingList();
		waitListD = new WaitingList();
	}
	
	public void addReadFile() {
		ReadFile f = new ReadFile();
		this.nbOfBedrooms = f.nbBedrooms;
		this.nbOfAvailableBedrooms = f.nbBedrooms;
		this.nbOfBlocs = f.nbBloc;
		this.nbOfAvailableBlocs = f.nbBloc;
		this.nbOfReceptionists = f.nbReceptionist;
		this.nbOfAvailableReceptionists = f.nbReceptionist;
		this.nbOfScanners = f.nbScanner;
		this.nbOfAvailableScanners = f.nbScanner;
	}

}
