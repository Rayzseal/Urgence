package model;

/**
 *
 * Class Bedroom, used to create a bedroom.
 *
 */
public class Bedroom extends Ressource {

	private static int no = 0;

	/**
	 * Creates a bedroom with a name and no associated patient by default.
	 */
	public Bedroom() {
		this.setName("Bedroom no°"+no);
		no++;
		//By default, there's no patient associated to the bedroom.
		this.setActualPatient(null);
	}

}
