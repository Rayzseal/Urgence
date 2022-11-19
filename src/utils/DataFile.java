package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class to read a file that contains all of the static information of the application. 
 * @author chloe
 *
 */
public class DataFile {
	
	private int nbBedrooms;
	private int nbScanner;
	private int nbBloc;
	private int nbReceptionist;
	private int nbDoctor;
	
	private int timeReception;
	private int timeScanner;
	private int timeAnalysis;
	private int timeBloc;
	private int timePrescription;

	/**
	 * Constructor read file.
	 */
	public DataFile() {
		readDataFile();
		readTimeFile();
	}
	
	/**
	 * The method readDataFile reads a csv file.
	 */
	public void readDataFile() {
		try {
			File fileDir = new File("src/ressources/data.csv");
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			
			//1st line
			in.readLine();
			
			String ligne = in.readLine();
			String[] data = ligne.split(";");
			
			this.nbBedrooms = Integer.parseInt(data[0]);
			this.nbScanner = Integer.parseInt(data[1]);
			this.nbBloc = Integer.parseInt(data[2]);
			this.nbReceptionist = Integer.parseInt(data[3]);
			this.nbDoctor = Integer.parseInt(data[4]);
			
			in.close();
			
		} catch(IOException e) {
				System.out.println(e);
		}
	}
	
	/**
	 * The method readTimeFile reads a csv file.
	 */
	public void readTimeFile() {
		try {
			File fileDir = new File("src/ressources/time.csv");
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			
			//1st line (contain the names of colums)
			in.readLine();
			
			String ligne = in.readLine();
			String[] data = ligne.split(";");
			
			this.timeReception = Integer.parseInt(data[0]);
			this.timeScanner = Integer.parseInt(data[1]);
			this.timeAnalysis = Integer.parseInt(data[2]);
			this.timeBloc = Integer.parseInt(data[3]);
			this.timePrescription = Integer.parseInt(data[4]);
			
			in.close();
			
		} catch(IOException e) {
				System.out.println(e);
		}
	}

	public int getNbBedrooms() {
		return nbBedrooms;
	}

	public void setNbBedrooms(int nbBedrooms) {
		this.nbBedrooms = nbBedrooms;
	}

	public int getNbScanner() {
		return nbScanner;
	}

	public void setNbScanner(int nbScanner) {
		this.nbScanner = nbScanner;
	}

	public int getNbBloc() {
		return nbBloc;
	}

	public void setNbBloc(int nbBloc) {
		this.nbBloc = nbBloc;
	}

	public int getNbReceptionist() {
		return nbReceptionist;
	}

	public void setNbReceptionist(int nbReceptionist) {
		this.nbReceptionist = nbReceptionist;
	}

	public int getNbDoctor() {
		return nbDoctor;
	}

	public void setNbDoctor(int nbDoctor) {
		this.nbDoctor = nbDoctor;
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
