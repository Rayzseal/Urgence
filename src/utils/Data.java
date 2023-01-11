package utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * Class Data, contains all the datas needed to run a simulation.
 * Stores all datas of a simulation.
 * Generates patients using a poisson's distribution. (gaussian distribution is also available)
 *
 */
public class Data implements Serializable{

	private static final long serialVersionUID = 3065201526377906093L;

	public static int nbSecondsPerDay = 86400;

	private int nbOfPatients = 50;

	private static int timeValue = 0;

	// --- Gaussian values --- //
	// value in seconds
	private int hourPeakArrivals = 54000; // average
	private int standardDeviation = 10000; // deviation
	// --- Poisson's ditribution values --- //
	private double lambda = 50; //lambda

	/**
	 * Ressources
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
	 * Waiting lists
	 */
	private List<Patient> waitListArrival;
	private WaitingList waitListBedroom;
	private WaitingList waitListAnalysis;
	private WaitingList waitListBloc;
	private WaitingList waitListPrescription;
	private WaitingList waitListScanner;

	/**
	 * Value of time
	 */
	private int time;

	private int timeReception;
	private int timeScanner;
	private int timeAnalysis;
	private int timeBloc;
	private int timePrescription;

	/**
	 * Constructor of the class, the number of patients is 500 by default.
	 */
	public Data() {
		generateData();
	}

	/**
	 * Constructor of the class, generate data, and the number of patients : nbPatient.
	 * @param nbPatient number of patients to be generated.
	 */
	public Data(int nbPatient) {
		nbOfPatients = nbPatient;
		generatePatients(null);
		generateData();
	}

	/**
	 * Constructor of the class, generate data, and a list of patients from the file SimulationFile.
	 * @param patients list of patients to generate.
	 */
	public Data(List<Patient> patients) {
		nbOfPatients = patients.size();
		generateData();
		generatePatients(patients);
	}

	/**
	 * Generate every data : ressources, patients, waiting lists.
	 */
	private void generateData() {
		timeValue = 0;
		DataFile dataFile = new DataFile();
		generateLists(dataFile);
		generateTime(dataFile);
		generateWaitingList();
	}

	/**
	 * If patients is null, patients are generate randomly else the list is the list given
	 */
	public void generatePatients(List<Patient> listPatients) {
		this.patients = new ArrayList<>();
		this.patientsActive = new ArrayList<>();
		this.patientsOver = new ArrayList<>();
		this.time = 0;
		if(listPatients == null) {
			generatePatientsPoisson(lambda);
		}else {
			for(Patient p : listPatients) {
				patients.add(Patient.resetPatient(p));
			}
			patients.sort(new SortPatientArrival());
		}
	}

	/**
	 * Generate lists of ressources needed for activities.
	 * @param dataFile Object that contains all the values.
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
	 * Generate time value for every activity in paths.
	 * @param dataFile Object that contains all the values.
	 */
	private void generateTime(DataFile dataFile) {
		this.timeReception = dataFile.getTimePrescription();
		this.timeScanner = dataFile.getTimeScanner();
		this.timeAnalysis = dataFile.getTimeAnalysis();
		this.timeBloc = dataFile.getTimeBloc();
		this.timePrescription = dataFile.getTimePrescription();
	}

	/**
	 * Generate all the patients using a gaussian law.
	 */
	public void generatePatientsNormal() {

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
	 * Used to generate values based on the Poisson's distribution.
	 * @param lambda Lambda.
	 * @return generated List generated using a Poisson's distribution.
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
	 * Get the maximum value contained in a list.
	 * @param list List in which we are looking for the maximum.
	 * @return THe index of the maximum value in the list.
	 */
	public int getMax(ArrayList<Integer> list) {
		int max = 0;
		for (Integer element : list) {
			if (element>max)
				max=element;
		}
		return max;
	}

	/**
	 * Get the minimum value contained in a list.
	 * @param list List in which we are looking for the minimum.
	 * @return The index of the minimum value in the list.
	 */
	public int getMin(ArrayList<Integer> list) {
		int min = list.get(0);
		for (Integer element : list) {
			if (element<min)
				min = element;
		}
		return min;
	}

	/**
	 * Generate patients using either a gaussian law or a poisson distribution.
	 * @param lambda lambda.
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
		patients.sort(new SortPatientArrival());
	}

	/**
	 * Used to generate patients based on a Poisson's distribution.
	 * Get the next <b>different</b> value in a list.
	 * @param value search loop while it's the same value.
	 * @param index index of value in the list (starting looping in the list at value index).
	 * @return the index (next different arrival date) in the list.
	 */
	private int getNextValue(int value, int index) {
		int i = index;
		while(value==patients.get(i).getArrivalDate() && i < patients.size()-1)
			i++;

		return patients.get(i).getArrivalDate();
	}

	/**
	 * Used to generate random values using based on a Poisson's distribution.
	 * @return A list containing new value randomly generated (in an interval).
	 */
	private void realValues() {

		int lower = 0;
		int upper = patients.get(0).getArrivalDate();
		for (int i = 0; i < patients.size()-1; i++) {
			int newValue = (int) (Math.random() * (upper - lower)) + lower;
			patients.get(i).setArrivalDate(newValue);
			// FOR LAST PATIENT
			if (i == patients.size()-2) {
				newValue = (int) (Math.random() * (upper - lower)) + lower;
				patients.get(i+1).setArrivalDate(newValue);
			}
			upper = getNextValue(patients.get(i).getArrivalDate(),i);
			lower = patients.get(i).getArrivalDate();
		}
	}

	/**
	 * Generate every WaitingList.
	 */
	private void generateWaitingList() {
		waitListArrival = new ArrayList<>();
		waitListBedroom = new WaitingList(State.BEDROOM);
		waitListPrescription = new WaitingList(State.PRESCRIPTION);
		waitListAnalysis = new WaitingList(State.ANALYSIS);
		waitListBloc = new WaitingList(State.BLOC);
		waitListScanner = new WaitingList(State.SCANNER);
	}

	/* Getters & setters of class Data */

	/**
	 * @return the number of patients
	 */
	public int getNbOfPatients() {
		return nbOfPatients;
	}

	/**
	 * @return the hourPeakArrivals
	 */
	public int getHourPeakArrivals() {
		return hourPeakArrivals;
	}
	/**
	 * @return the standardDeviation
	 */
	public int getStandardDeviation() {
		return standardDeviation;
	}
	/**
	 * @return the lambda
	 */
	public double getLambda() {
		return lambda;
	}
	/**
	 * @return a list of bedrooms
	 */
	public List<Bedroom> getBedrooms() {
		return bedrooms;
	}
	/**
	 * @return a list of blocs
	 */
	public List<Bloc> getBlocs() {
		return blocs;
	}
	/**
	 * @return a list of scanners
	 */
	public List<Scanner> getScanners() {
		return scanners;
	}
	/**
	 * @return a list of receptionists
	 */
	public List<Receptionist> getReceptionists() {
		return receptionists;
	}
	/**
	 * @return a list of doctors
	 */
	public List<Doctor> getDoctors() {
		return doctors;
	}
	/**
	 * @return a list of nurses
	 */
	public List<Nurse> getNurses() {
		return nurses;
	}
	/**
	 * @return a list of patients
	 */
	public List<Patient> getPatients() {
		return patients;
	}
	/**
	 * @return a list of patientsActive
	 */
	public List<Patient> getPatientsActive() {
		return patientsActive;
	}
	/**
	 * @return a list of patientsOver
	 */
	public List<Patient> getPatientsOver() {
		return patientsOver;
	}
	/**
	 * @return a list of waitListArrival
	 */
	public List<Patient> getWaitListArrival() {
		return waitListArrival;
	}
	/**
	 * @return a list of waitListBedroom
	 */
	public WaitingList getWaitListBedroom() {
		return waitListBedroom;
	}
	/**
	 * @return a list of waitListAnalysis
	 */
	public WaitingList getWaitListAnalysis() {
		return waitListAnalysis;
	}
	/**
	 * @return a list of waitListBloc
	 */
	public WaitingList getWaitListBloc() {
		return waitListBloc;
	}
	/**
	 * @return a list of waitListPrescription
	 */
	public WaitingList getWaitListPrescription() {
		return waitListPrescription;
	}
	/**
	 * @return a list of waitListScanner
	 */
	public WaitingList getWaitListScanner() {
		return waitListScanner;
	}
	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
	/**
	 * @return the value of timeReception
	 */
	public int getTimeReception() {
		return timeReception;
	}
	/**
	 * @return the value of timeScanner
	 */
	public int getTimeScanner() {
		return timeScanner;
	}
	/**
	 * @return the value of timeAnalysis
	 */
	public int getTimeAnalysis() {
		return timeAnalysis;
	}
	/**
	 * @return the value of timeBloc
	 */
	public int getTimeBloc() {
		return timeBloc;
	}
	/**
	 * @return the value of timePrescription
	 */
	public int getTimePrescription() {
		return timePrescription;
	}
	/**
	 * @param nbOfPatients the nbOfPatients to set
	 */
	public void setNbOfPatients(int nbOfPatients) {
		this.nbOfPatients = nbOfPatients;
	}
	/**
	 * @param hourPeakArrivals the hourPeakArrivals to set
	 */
	public void setHourPeakArrivals(int hourPeakArrivals) {
		this.hourPeakArrivals = hourPeakArrivals;
	}
	/**
	 * @param standardDeviation the standardDeviation to set
	 */
	public void setStandardDeviation(int standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	/**
	 * @param lambda the lambda to set
	 */
	public void setLambda(double lambda) {
		this.lambda = lambda;
	}
	/**
	 * @param bedrooms the bedrooms to set
	 */
	public void setBedrooms(List<Bedroom> bedrooms) {
		this.bedrooms = bedrooms;
	}
	/**
	 * @param blocs the blocs to set
	 */
	public void setBlocs(List<Bloc> blocs) {
		this.blocs = blocs;
	}
	/**
	 * @param scanners the scanners to set
	 */
	public void setScanners(List<Scanner> scanners) {
		this.scanners = scanners;
	}
	/**
	 * @param receptionists the receptionists to set
	 */
	public void setReceptionists(List<Receptionist> receptionists) {
		this.receptionists = receptionists;
	}
	/**
	 * @param doctors the doctors to set
	 */
	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}
	/**
	 * @param nurses the nurses to set
	 */
	public void setNurses(List<Nurse> nurses) {
		this.nurses = nurses;
	}
	/**
	 * @param patients the patients to set
	 */
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	/**
	 * @param patientsActive the patientsActive to set
	 */
	public void setPatientsActive(List<Patient> patientsActive) {
		this.patientsActive = patientsActive;
	}
	/**
	 * @param patientsOver the patientsOver to set
	 */
	public void setPatientsOver(List<Patient> patientsOver) {
		this.patientsOver = patientsOver;
	}
	/**
	 * @param waitListArrival the waitListArrival to set
	 */
	public void setWaitListArrival(List<Patient> waitListArrival) {
		this.waitListArrival = waitListArrival;
	}
	/**
	 * @param waitListBedroom the waitListBedroom to set
	 */
	public void setWaitListBedroom(WaitingList waitListBedroom) {
		this.waitListBedroom = waitListBedroom;
	}
	/**
	 * @param waitListAnalysis the waitListAnalysis to set
	 */
	public void setWaitListAnalysis(WaitingList waitListAnalysis) {
		this.waitListAnalysis = waitListAnalysis;
	}
	/**
	 * @param waitListBloc the waitListBloc to set
	 */
	public void setWaitListBloc(WaitingList waitListBloc) {
		this.waitListBloc = waitListBloc;
	}
	/**
	 * @param waitListPrescription the waitListPrescription to set
	 */
	public void setWaitListPrescription(WaitingList waitListPrescription) {
		this.waitListPrescription = waitListPrescription;
	}
	/**
	 * @param waitListScanner the waitListScanner to set
	 */
	public void setWaitListScanner(WaitingList waitListScanner) {
		this.waitListScanner = waitListScanner;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}
	/**
	 * @param timeReception the timeReception to set
	 */
	public void setTimeReception(int timeReception) {
		this.timeReception = timeReception;
	}
	/**
	 * @param timeScanner the timeScanner to set
	 */
	public void setTimeScanner(int timeScanner) {
		this.timeScanner = timeScanner;
	}
	/**
	 * @param timeAnalysis the timeAnalysis to set
	 */
	public void setTimeAnalysis(int timeAnalysis) {
		this.timeAnalysis = timeAnalysis;
	}
	/**
	 * @param timeBloc the timeBloc to set
	 */
	public void setTimeBloc(int timeBloc) {
		this.timeBloc = timeBloc;
	}
	/**
	 * @param timePrescription the timePrescription to set
	 */
	public void setTimePrescription(int timePrescription) {
		this.timePrescription = timePrescription;
	}

	/**
	 * @return the timeValue
	 */
	public static int getTimeValue() {
		return timeValue;
	}

	/**
	 * @param timeValue the timeValue to set
	 */
	public static void setTimeValue(int timeValue) {
		Data.timeValue = timeValue;
	}
}
