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
	}

}
