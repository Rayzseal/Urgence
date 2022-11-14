package back;

import java.util.ArrayList;
import java.util.Random;

import utils.ReadFile;
import utils.SortPatientArrival;

public class Main {
		
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
	
	private int time;
	private int reduceTime;
	
	public static String[] names = { "Olivia", "Amelia", "Isla", "Ava", "Mia", "Ivy", "Lily", "Oliver", "George",
			"Arthur", "Noah", "Muhammad", "Leo", "Oscar", "Harry", "Archie", "Jack", "Liam", "Jackson", "Aiden",
			"Grayson", "Lucas", "Emily", "Ashley", "Alyssa" };

	public static String[] surnames = { "Anderson", "Brown", "Byrne", "Clark", "Cooper", "Davies", "Evans", "Garcia",
			"Gonzalez", "Green", "Hall", "Harris", "Hernandez", "Hughes", "Jackson", "Johnson", "Jones", "Lam", "Lee",
			"Lewis", "Lopez", "Martin", "Miller", "O'connor", "O'Neil" };
	
	public Main() {
		generateLists();
		generatePatients();
		addReadFile();
		
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
						
			int nbName = (int) (Math.random() * names.length);
			int nbSur = (int) (Math.random() * surnames.length);

			Patient p = new Patient(names[nbName], surnames[nbSur],millisDelay);
			
			this.patients.add(p);
		}
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
	
	public void showList(ArrayList<?> list) {
		for (int i = 0; i < list.size(); i++)
			System.out.println(list.get(i).toString());
	}
	
	public static void main(String args[]) {
		
		Main m = new Main();
		
		m.showList(m.patients);
		System.out.println("------------------");
		System.out.println("BEGIN");
		System.out.println("------------------");
		
		
		//While we still have patients to arrive or patients in treatment we wait, when every patients has been treated we stop.  
		while(m.patients.size()!=0 || m.patientsActive.size()!=0) {
			try {
				// 1000 == 1 seconde / reduceTime 
				// with reduceTime = an accelerator 
				Thread.sleep(1000/m.reduceTime);
				m.time++;
				
				System.out.println("Time : "+m.time);
				
				if (m.patients.get(0).getArrivalDate()==m.time) {
					//Start patient 
					System.out.println("Arrivée patient : "+m.patients.get(0));
					
					//Add to "over" list & remove patients from waiting list
					m.patientsActive.add(m.patients.get(0));
					m.patients.remove(m.patients.get(0));
					
					//If patient active = terminate state then add to patientOver & remove from patientActive
					
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
	}

}
