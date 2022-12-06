package back;

public class Scanner extends Ressource {
	
	private static int no = 0;

	public Scanner() {
		this.setName("Bedroom no°"+no);
		no++;
		//By default, there's no patient associated to a scanner.
		this.setActualPatient(null);
	}

}
