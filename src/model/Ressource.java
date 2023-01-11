package model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * Class Ressource, used to create a ressource.
 *
 */
public class Ressource implements Serializable{

	private static final long serialVersionUID = -3619731778844577464L;

	private String name;

	private Patient actualPatient;

	private int actualTime;

	private Map<State, Integer> listState;
	private State state;


	/**
	 * Created a ressources, by default this new ressource is available.
	 */
	public Ressource() {
		state = State.AVAILABLE;
		actualTime = 0;
		listState = new LinkedHashMap<>();
		listState.put(State.AVAILABLE, 0);
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
	 * @return the actualTime
	 */
	public int getActualTime() {
		return actualTime;
	}

	/**
	 * @return the listState
	 */
	public Map<State, Integer> getListState() {
		return listState;
	}

	/**
	 * @param listState the listState to set
	 */
	public void setListState(Map<State, Integer> listState) {
		this.listState = listState;
	}

	/**
	 * @param actualTime the actualTime to set
	 */
	public void setActualTime(int actualTime) {
		this.actualTime = actualTime;
	}

	/**
	 * @param time add time to actualTime
	 */
	public void addActualTime(int time) {
		this.actualTime += time;
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
