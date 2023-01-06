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
import java.util.ArrayList;
import java.util.List;

import model.Patient;

/**
 * 
 * Class to read a file that contains all of the static information of the
 * application.
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

			//TODO add should be > 0
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

			//TODO add should be > 0
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
	 * the methods read the file nameFile and return a list of Patient
	 * nameFile must be a csv and contain an object List<Patient>
	 * @param nameFile
	 * @return
	 */
	public static List<Patient> readSimulationFile(String nameFile) {
		if(!nameFile.matches("[a-zA-Z0-9]+.csv")) {
			throw new IllegalArgumentException("Invalid name file");
		}
		String filename = "src" + sep + "ressources" + sep + nameFile;

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
	 * the method write a list of patients to start a simulation in the file nameFile
	 * @param nameFile
	 * @param patients
	 */
	public static void writeSimulationStartFile(String nameFile, List<Patient> patients) {
		List<Patient> patientsNew = new ArrayList<Patient>();
		for(Patient p : patients) {
			patientsNew.add(Patient.resetPatient(p));
		}
		writeSimulationFile(nameFile, patientsNew);
	}
	
	/**
	 * the method write a list of patients in the file nameFile
	 * @param nameFile
	 * @param patients
	 */
	public static void writeSimulationFile(String nameFile, List<Patient> patients) {
		if(!nameFile.matches("[a-zA-Z0-9]+.csv")) {
			throw new IllegalArgumentException("Invalid name file");
		}
		String filename = "src" + sep + "ressources" + sep + nameFile;
		try (FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream bos = new ObjectOutputStream(fos);) {

			bos.writeObject(patients);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the nbBedrooms
	 */
	public int getNbBedrooms() {
		return nbBedrooms;
	}

	/**
	 * @param nbBedrooms the nbBedrooms to set
	 */
	public void setNbBedrooms(int nbBedrooms) {
		this.nbBedrooms = nbBedrooms;
	}

	/**
	 * @return the nbScanner
	 */
	public int getNbScanner() {
		return nbScanner;
	}

	/**
	 * @param nbScanner the nbScanner to set
	 */
	public void setNbScanner(int nbScanner) {
		this.nbScanner = nbScanner;
	}

	/**
	 * @return the nbBloc
	 */
	public int getNbBloc() {
		return nbBloc;
	}

	/**
	 * @param nbBloc the nbBloc to set
	 */
	public void setNbBloc(int nbBloc) {
		this.nbBloc = nbBloc;
	}

	/**
	 * @return the nbReceptionist
	 */
	public int getNbReceptionist() {
		return nbReceptionist;
	}

	/**
	 * @param nbReceptionist the nbReceptionist to set
	 */
	public void setNbReceptionist(int nbReceptionist) {
		this.nbReceptionist = nbReceptionist;
	}

	/**
	 * @return the nbDoctor
	 */
	public int getNbDoctor() {
		return nbDoctor;
	}

	/**
	 * @param nbDoctor the nbDoctor to set
	 */
	public void setNbDoctor(int nbDoctor) {
		this.nbDoctor = nbDoctor;
	}

	/**
	 * @return the nbNurse
	 */
	public int getNbNurse() {
		return nbNurse;
	}

	/**
	 * @param nbNurse the nbNurse to set
	 */
	public void setNbNurse(int nbNurse) {
		this.nbNurse = nbNurse;
	}

	/**
	 * @return the timeReception
	 */
	public int getTimeReception() {
		return timeReception;
	}

	/**
	 * @param timeReception the timeReception to set
	 */
	public void setTimeReception(int timeReception) {
		this.timeReception = timeReception;
	}

	/**
	 * @return the timeScanner
	 */
	public int getTimeScanner() {
		return timeScanner;
	}

	/**
	 * @param timeScanner the timeScanner to set
	 */
	public void setTimeScanner(int timeScanner) {
		this.timeScanner = timeScanner;
	}

	/**
	 * @return the timeAnalysis
	 */
	public int getTimeAnalysis() {
		return timeAnalysis;
	}

	/**
	 * @param timeAnalysis the timeAnalysis to set
	 */
	public void setTimeAnalysis(int timeAnalysis) {
		this.timeAnalysis = timeAnalysis;
	}

	/**
	 * @return the timeBloc
	 */
	public int getTimeBloc() {
		return timeBloc;
	}

	/**
	 * @param timeBloc the timeBloc to set
	 */
	public void setTimeBloc(int timeBloc) {
		this.timeBloc = timeBloc;
	}

	/**
	 * @return the timePrescription
	 */
	public int getTimePrescription() {
		return timePrescription;
	}

	/**
	 * @param timePrescription the timePrescription to set
	 */
	public void setTimePrescription(int timePrescription) {
		this.timePrescription = timePrescription;
	}

	

}
