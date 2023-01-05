package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.Patient;

/**
 * Class to read a file that contains all of the static information of the
 * application.
 * 
 * @author chloe
 *
 */
public class DataFile {
	private static String sep = File.separator;

	private int nbBedrooms;
	private int nbScanner;
	private int nbBloc;
	private int nbReceptionist;
	private int nbDoctor;
	private int nbNurse;

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

			// 1st line
			in.readLine();

			String ligne = in.readLine();
			String[] data = ligne.split(";");

			this.nbBedrooms = Integer.parseInt(data[0]);
			this.nbScanner = Integer.parseInt(data[1]);
			this.nbBloc = Integer.parseInt(data[2]);
			this.nbReceptionist = Integer.parseInt(data[3]);
			this.nbDoctor = Integer.parseInt(data[4]);
			this.nbNurse = Integer.parseInt(data[5]);

			in.close();

		} catch (IOException e) {
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

			// 1st line (contain the names of colums)
			in.readLine();

			String ligne = in.readLine();
			String[] data = ligne.split(";");

			this.timeReception = Integer.parseInt(data[0]);
			this.timeScanner = Integer.parseInt(data[1]);
			this.timeAnalysis = Integer.parseInt(data[2]);
			this.timeBloc = Integer.parseInt(data[3]);
			this.timePrescription = Integer.parseInt(data[4]);

			in.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}
	/**
	 * the methods read the file SimulationExemple and return a list of Patient
	 * @return data
	 */
	public static List<Patient> readSimulationFile() {
		String filename = "src" + sep + "ressources" + sep + "simulationExemple.csv";

		try (FileInputStream fis = new FileInputStream(filename); ObjectInputStream bis = new ObjectInputStream(fis);) {

			Object patients;
			try {
				 patients = bis.readObject();
				return (List<Patient>) patients;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * the method write a list of patients in the file SimulationExemple
	 * @param data
	 */
	public static void writeSimulationFile(List<Patient> patients) {
		List<Patient> patientsNew = new ArrayList<Patient>();
		for(Patient p : patients) {
			patientsNew.add(Patient.resetPatient(p));
		}
		String filename = "src" + sep + "ressources" + sep + "simulationExemple.csv";
		try (FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream bos = new ObjectOutputStream(fos);) {

			bos.writeObject(patientsNew);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setter and getter of the class
	 */
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

	public int getNbNurse() {
		return nbNurse;
	}

	public void setNbNurse(int nbNurse) {
		this.nbNurse = nbNurse;
	}

}
