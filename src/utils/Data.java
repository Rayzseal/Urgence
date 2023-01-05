package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.Bedroom;
import model.Bloc;
import model.Doctor;
import model.Nurse;
import model.Patient;
import model.Receptionist;
import model.Scanner;
import model.State;
import model.WaitingList;

public class Data {
	
	private int nbOfPatients = 500;
	
	// --- Gaussian values --- //
	// value in seconds
	private int hourPeakArrivals = 54000; // average
	private int standardDeviation = 10000; // deviation
	private double lambda = 50;
	
	/**
	 * ressources
	 */
	private List<Bedroom> bedrooms;
	private List<Bloc> blocs;
	private List<Scanner> scanners;
	private List<Receptionist> receptionists;
	private List<Doctor> doctors;
	private List<Nurse> nurses;

	private List<Patient> patients;
	private List<Patient> patientsActive;
	private List<Patient> patientsOver;

	
	
	/**
	 * waiting list
	 */
	private List<Patient> waitListArrival;
	private WaitingList waitListBedroom;
	private WaitingList waitListAnalysis;
	private WaitingList waitListBloc;
	private WaitingList waitListPrescription;
	private WaitingList waitListScanner;
	private Map<State, WaitingList> waitListPathC;
	
	/**
	 * value of time
	 */
	private int time;
	private int reduceTime;

	private int timeReception;
	private int timeScanner;
	private int timeAnalysis;
	private int timeBloc;
	private int timePrescription;
	/**
	 * constructor of the class, the number of patients is 500 by default
	 */
	public Data() {
		generateData();
	}
	/**
	 * constructor of the class, generate data, and the number of patients : nbPatient
	 * @param nbPatient
	 */
	public Data(int nbPatient) {
		nbOfPatients = nbPatient;
		generateData();
		generatePatients(null);
	}
	
	/**
	 * constructor of the class, generate data, and a list of patients from the file SimulationFile
	 * @param nbPatient
	 */
	public Data(List<Patient> patients) {
		nbOfPatients = patients.size();
		generateData();
		generatePatients(patients);
	}
	/**
	 * generate every data : ressources, data time, waiting List
	 */
	private void generateData() {
		this.reduceTime = 300;
		DataFile dataFile = new DataFile();
		generateLists(dataFile);
		generateTime(dataFile);
		
		generateWaitingList();
	}
	/**
	 * if patients is null, patients are generate randomly else the list is the list given
	 */
	public void generatePatients(List<Patient> listPatients) {
		this.patients = new ArrayList<>();
		this.patientsActive = new ArrayList<>();
		this.patientsOver = new ArrayList<>();
		this.time = 0;
		if(listPatients == null) {
			
			//generatePatientsNormal();
			generatePatientsPoisson(lambda);
		}else {
			for(Patient p : listPatients) {
				patients.add(p);
			}
			patients.sort(new SortPatientArrival());
		}
		
	}
	/**
	 * generate lists of ressources needed for activities
	 * @param dataFile
	 */
	private void generateLists(DataFile dataFile) {
		this.bedrooms = new ArrayList<>();
		this.blocs = new ArrayList<>();
		this.scanners = new ArrayList<>();
		this.receptionists = new ArrayList<>();
		this.doctors = new ArrayList<>();
		this.nurses = new ArrayList<>();

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
		for(int i = 0; i<dataFile.getNbNurse();i++)
			nurses.add(new Nurse());
	}
	/**
	 * generate time value for every activity in paths
	 * @param dataFile
	 */
	private void generateTime(DataFile dataFile) {
		this.timeReception = dataFile.getTimePrescription();
		this.timeScanner = dataFile.getTimeScanner();
		this.timeAnalysis = dataFile.getTimeAnalysis();
		this.timeBloc = dataFile.getTimeBloc();
		this.timePrescription = dataFile.getTimePrescription();
	}
	/**
	 * TODO
	 */
	public void generatePatientsNormal() {
		
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
	/**
	 * TODO
	 * @param lambda
	 * @return
	 */
	public ArrayList<Integer> poisson (double lambda) {
	    ArrayList<Integer> values = new ArrayList<>();

		for (int i = 0; i < nbOfPatients; i++) {
			Random random = new Random();
			double somme = 1;
		    double t = 1;
		    int n = 0;
		    double r = random.nextDouble() * Math.exp (lambda);
		    while (somme < r) {
		      n ++;
		      t *= lambda / n;
		      somme += t;
		    }
			values.add(n);
		}
		return values;
	}
	/**
	 * TODO
	 * @param list
	 * @return
	 */
	public int getMax(ArrayList<Integer> list) {
		int max = 0;
		for (int i = 0 ; i < list.size(); i++) {
			if (list.get(i)>max)
				max=list.get(i);
		}
		return max;
	}
	/**
	 * TODO
	 * @param list
	 * @return
	 */
	public int getMin(ArrayList<Integer> list) {
		int min = list.get(0);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i)<min)
				min = list.get(i);
		}
		return min;
	}
	
	/**
	 * generate patients for a number given
	 * @param lambda
	 */
	private void generatePatientsPoisson(double lambda) {

		int nbSecondsPerDay = 86400;
		
		ArrayList<Integer> values = new ArrayList<>();
		values = poisson(lambda);
		
		int max = getMax(values);
		int min = getMin(values);
		int gap = max - min + 1;
		
		int gapP = (nbSecondsPerDay/gap)*max - nbSecondsPerDay;
		
		for (int i = 0; i < nbOfPatients; i++) {
			
			int val = (nbSecondsPerDay/gap)*(values.get(i));
			
		    int nbName = (int) (Math.random() * Utils.names.length);
			int nbSur = (int) (Math.random() * Utils.surnames.length);
			
			this.patients.add(new Patient(Utils.names[nbName], Utils.surnames[nbSur], val-gapP));
		}
		patients.sort(new SortPatientArrival());
		realValues();
	}
	/**
	 * TODO
	 * @param value
	 * @param index
	 * @return
	 */
	private int getNextValue(int value, int index) {
		int i = index;
		while(value==patients.get(i).getArrivalDate() && i < patients.size()-1)
			i++;
		
		return patients.get(i).getArrivalDate();
	}
	/**
	 * TODO
	 * @return
	 */
	private ArrayList<Integer> realValues() {
		  ArrayList<Integer> tmp = new ArrayList<>();
		
		int lower = 0;
		int upper = patients.get(0).getArrivalDate();
		for (int i = 0; i < patients.size(); i++) {
			//TODO ajouter pour le dernier element 86400
			//TODO changer la valeur dans patient
			int newValue = (int) (Math.random() * (upper - lower)) + lower;
			upper = getNextValue(patients.get(i).getArrivalDate(),i);	
			lower = patients.get(i).getArrivalDate();;
		}
		return tmp;
	}
	/**
	 * generate every WaitingList
	 */
	private void generateWaitingList() {
		waitListArrival = new ArrayList<Patient>();
		waitListBedroom = new WaitingList(State.BEDROOM);
		waitListPrescription = new WaitingList(State.PRESCRIPTION);
		waitListAnalysis = new WaitingList(State.ANALYSIS);
		waitListBloc = new WaitingList(State.BLOC);
		waitListScanner = new WaitingList(State.SCANNER);
		waitListPathC = new HashMap<State, WaitingList>();
		waitListPathC.put(State.ANALYSIS, waitListAnalysis);
		waitListPathC.put(State.SCANNER, waitListScanner);
		//add new waitingList to prevent synchronization error on the pathC
		waitListPathC.put(State.WAITING, new WaitingList(State.WAITING));
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

	public List<Bedroom> getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(List<Bedroom> bedrooms) {
		this.bedrooms = bedrooms;
	}

	public List<Bloc> getBlocs() {
		return blocs;
	}

	public void setBlocs(List<Bloc> blocs) {
		this.blocs = blocs;
	}

	public List<Scanner> getScanners() {
		return scanners;
	}

	public void setScanners(List<Scanner> scanners) {
		this.scanners = scanners;
	}

	public List<Receptionist> getReceptionists() {
		return receptionists;
	}

	public void setReceptionists(List<Receptionist> receptionists) {
		this.receptionists = receptionists;
	}
	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}
		
	public List<Nurse> getNurses() {
		return nurses;
	}

	public void setNurses(List<Nurse> nurses) {
		this.nurses = nurses;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public List<Patient> getPatientsActive() {
		return patientsActive;
	}

	public void setPatientsActive(List<Patient> patientsActive) {
		this.patientsActive = patientsActive;
	}

	public List<Patient> getPatientsOver() {
		return patientsOver;
	}

	public void setPatientsOver(List<Patient> patientsOver) {
		this.patientsOver = patientsOver;
	}

	public List<Patient> getWaitListArrival() {
		return waitListArrival;
	}

	public void setWaitListArrival(List<Patient> waitListArrival) {
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

	public Map<State, WaitingList> getWaitListPathC() {
		return waitListPathC;
	}

	public void setWaitListPathC(Map<State, WaitingList> waitListPathC) {
		this.waitListPathC = waitListPathC;
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
	
}
