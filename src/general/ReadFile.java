package general;

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
public class ReadFile {
	
	public int nbBedrooms;
	public int nbScanner;
	public int nbBloc;
	public int nbReceptionist;
	
	public int timeReception;
	public int timeScanner;
	public int timeAnalysis;
	public int timeBloc;
	public int timePrescription;

	/**
	 * Constructor read file.
	 */
	public ReadFile() {
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
	
	/**
	public static void main(String args[]) {
		ReadFile test= new ReadFile();
		System.out.println("Nb bedrooms : "+test.nbBedrooms+" nb scanner : "+test.nbScanner+" nb blocs : "+test.nbBloc+" nbReceptionist : "+test.nbReceptionist);
		System.out.println("timeReception : "+test.timeReception+" timeScanner : "+test.timeScanner+" timeAnalysis : "+test.timeAnalysis+" timeBloc : "+test.timeBloc+" timePrescription : "+test.timePrescription);

	}
	**/	
}
