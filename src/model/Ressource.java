package model;

import java.io.Serializable;

/**
 * 
 * Class Ressource, used to create a ressource. 
 *
 */
public class Ressource implements Serializable{
	
	private static final long serialVersionUID = -3619731778844577464L;

	private String name;
	
	private Patient actualPatient;
	
	private State state;

	/**
	 * Created a ressources, by default this new ressource is available.
	 */
	public Ressource() {
		state = State.AVAILABLE;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the actualPatient
	 */
	public Patient getActualPatient() {
		return actualPatient;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param actualPatient the actualPatient to set
	 */
	public void setActualPatient(Patient actualPatient) {
		this.actualPatient = actualPatient;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}
}
