package model;

/**
 *
 * Class Scanner, used to create a scanner.
 *
 */
public class Scanner extends Ressource {

	private static int no = 0;

	/**
	 * Creates a scanenr with a name and no associated patient by default.
	 */
	public Scanner() {
		this.setName("Bedroom no°"+no);
		no++;
		//By default, there's no patient associated to a scanner.
		this.setActualPatient(null);
	}

}
