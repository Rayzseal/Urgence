package utils;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import back.Bedroom;
import back.Bloc;
import back.Doctor;
import back.Nurse;
import back.Patient;
import back.Receptionist;
import back.Scanner;
import back.State;
import back.WaitingList;
import events.PatientArrival;

public class Data {
	
	private int nbOfPatients = 500;
	
	

	// --- Gaussian values --- //
	// value in seconds
	private int hourPeakArrivals = 54000; // average
	private int standardDeviation = 10000; // deviation
	private double lambda = 50;
	

	private ArrayList<Bedroom> bedrooms;
	private ArrayList<Bloc> blocs;
	private ArrayList<Scanner> scanners;
	private ArrayList<Receptionist> receptionists;
	private ArrayList<Doctor> doctors;
	private ArrayList<Nurse> nurses;

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
		//generatePatientsNormal();
		generatePatients(lambda);
		generateWaitingList();
	}

	public void generateLists(DataFile dataFile) {
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

	public void generatePatientsNormal() {
		this.time = 0;

		this.patients = new ArrayList<>();
		this.patientsActive = new ArrayList<>();
		this.patientsOver = new ArrayList<>();
		
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
	
	public int getMax(ArrayList<Integer> list) {
		int max = 0;
		for (int i = 0 ; i < list.size(); i++) {
			if (list.get(i)>max)
				max=list.get(i);
		}
		return max;
	}
	
	public int getMin(ArrayList<Integer> list) {
		int min = list.get(0);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i)<min)
				min = list.get(i);
		}
		return min;
	}
	

	public void generatePatients(double lambda) {

		this.patients = new ArrayList<>();
		this.patientsActive = new ArrayList<>();
		this.patientsOver = new ArrayList<>();
		this.time = 0;
		
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
	
	public int getNextValue(int value, int index) {
		int i = index;
		while(value==patients.get(i).getArrivalDate() && i < patients.size()-1)
			i++;
		
		return patients.get(i).getArrivalDate();
	}
	
	public ArrayList<Integer> realValues() {
		  ArrayList<Integer> tmp = new ArrayList<>();
		
		int lower = 0;
		int upper = patients.get(0).getArrivalDate();
		for (int i = 0; i < patients.size(); i++) {
			//TODO ajouter pour le dernier element 86400
			//TODO changer la valeur dans patient
			int newValue = (int) (Math.random() * (upper - lower)) + lower;
			System.out.println("New value is "+newValue+" beteween "+lower+" and "+upper); 
			upper = getNextValue(patients.get(i).getArrivalDate(),i);	
			lower = patients.get(i).getArrivalDate();;
		}
		return tmp;
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
		
	public ArrayList<Nurse> getNurses() {
		return nurses;
	}

	public void setNurses(ArrayList<Nurse> nurses) {
		this.nurses = nurses;
	}

	public static void main(String args[]) {
		
		 Data d = new Data();
		 
		int values[] = new int[18];

		for (int i = 0; i < 18; i ++)
			values[i]=0;

		for (int i = 0; i < d.getNbOfPatients(); i++) {
			if (d.getPatients().get(i).getArrivalDate() > 0 && d.getPatients().get(i).getArrivalDate() < 5000) 
				values[0]=values[0]+1;
			if (d.getPatients().get(i).getArrivalDate() > 5000 && d.getPatients().get(i).getArrivalDate() < 10000) 
				values[1]=values[1]+1;
			if (d.getPatients().get(i).getArrivalDate() > 10000 && d.getPatients().get(i).getArrivalDate() < 15000) 
				values[2]=values[2]+1;
			if (d.getPatients().get(i).getArrivalDate() > 15000 && d.getPatients().get(i).getArrivalDate() < 20000) 
				values[3]=values[3]+1;
			if (d.getPatients().get(i).getArrivalDate() > 20000 && d.getPatients().get(i).getArrivalDate() < 25000) 
				values[4]=values[4]+1;
			if (d.getPatients().get(i).getArrivalDate() > 25000 && d.getPatients().get(i).getArrivalDate() < 30000) 
				values[5]=values[5]+1;
			if (d.getPatients().get(i).getArrivalDate() > 30000 && d.getPatients().get(i).getArrivalDate() < 35000) 
				values[6]=values[6]+1;
			if (d.getPatients().get(i).getArrivalDate() > 35000 && d.getPatients().get(i).getArrivalDate() < 40000) 
				values[7]=values[7]+1;
			if (d.getPatients().get(i).getArrivalDate() > 40000 && d.getPatients().get(i).getArrivalDate() < 45000) 
				values[8]=values[8]+1;		
			if (d.getPatients().get(i).getArrivalDate() > 45000 && d.getPatients().get(i).getArrivalDate() < 50000) 
				values[9]=values[9]+1;			
			if (d.getPatients().get(i).getArrivalDate() > 50000 && d.getPatients().get(i).getArrivalDate() < 55000) 
				values[10]=values[10]+1;				
			if (d.getPatients().get(i).getArrivalDate() > 55000 && d.getPatients().get(i).getArrivalDate() < 60000) 
				values[11]=values[11]+1;
			if (d.getPatients().get(i).getArrivalDate() > 60000 && d.getPatients().get(i).getArrivalDate() < 65000) 
				values[12]=values[12]+1;	
			if (d.getPatients().get(i).getArrivalDate() > 65000 && d.getPatients().get(i).getArrivalDate() < 70000) 
				values[13]=values[13]+1;	
			if (d.getPatients().get(i).getArrivalDate() > 70000 && d.getPatients().get(i).getArrivalDate() < 75000) 
				values[14]=values[14]+1;			
			if (d.getPatients().get(i).getArrivalDate() > 75000 && d.getPatients().get(i).getArrivalDate() < 80000) 
				values[15]=values[15]+1;				
			if (d.getPatients().get(i).getArrivalDate() > 80000 && d.getPatients().get(i).getArrivalDate() < 85000) 
				values[16]=values[16]+1;					
			if (d.getPatients().get(i).getArrivalDate() > 85000 && d.getPatients().get(i).getArrivalDate() < 90000) 
				values[17]=values[17]+1;
	
			//System.out.println(d.getPatients().get(i).getArrivalDate());
		}
		
		for (int i = 0; i < 18; i++) {
			System.out.println("Values : "+values[i]);
		}
		
	}
	
}
