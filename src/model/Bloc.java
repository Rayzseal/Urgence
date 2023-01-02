package model;

public class Bloc extends Ressource {
		
	private static int no = 0;

	public Bloc() {
		this.setName("Bloc no°"+no);
		no++;
		//By default, there's no patient associated to a bloc.
		this.setActualPatient(null);
	}

}
