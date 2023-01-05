package model;

/**
 * 
 * Class bloc, used to create a bloc. 
 *
 */
public class Bloc extends Ressource {
		
	private static int no = 0;

	/**
	 * Created a bloc with a generated name and no patient by default.
	 */
	public Bloc() {
		this.setName("Bloc no°"+no);
		no++;
		//By default, there's no patient associated to a bloc.
		this.setActualPatient(null);
	}

}
