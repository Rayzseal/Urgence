package model;

public class Ressource {
	
	private String name;
	
	private Patient actualPatient;
	
	private State state;

	public Ressource() {
		state = State.AVAILABLE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Patient getActualPatient() {
		return actualPatient;
	}

	public void setActualPatient(Patient actualPatient) {
		this.actualPatient = actualPatient;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	

}
