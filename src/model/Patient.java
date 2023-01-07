package model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import utils.Utils;

/**
 * 
 * Class patient, generates a patient.
 *
 */
public class Patient implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	// private int age;

	private Gravity gravity;

	private State state;

	private Map<State, Integer> listState;
	private Map<State, Integer> listWaitTime;
	private int GlobalWaitInSeconds;

	private Bedroom bedroom;

	private int arrivalDate;
	private boolean typeArrival; // false patient arrive by himself
	
	/**
	 * Default constructor, with no parameters.
	 */
	public Patient() {
		patient();
		name = null;
		surname = null;
		arrivalDate = 0;

	}

	/**
	 * Constructor to generate a patient with specified parameters.
	 * @param name Name to set. 
	 * @param surname Surname to set. 
	 * @param arrivalDate Arrival date to set.
	 */
	public Patient(String name, String surname, int arrivalDate) {
		patient();
		this.name = name;
		this.surname = surname;
		this.arrivalDate = arrivalDate;

	}

	/**
	 * Function to initialize patient
	 */
	private void patient() {
		listState = new LinkedHashMap<>();
		listWaitTime = new LinkedHashMap<>();
		int g = (int) (Math.random() * 100);
		int critic = 2;

		if (g < critic)
			typeArrival = true;
		else
			typeArrival = false; // TODO gravité
		gravity = null;
		state = State.AVAILABLE;
		bedroom = null;

		GlobalWaitInSeconds = 0;
	}
	
	/**
	 * Reset patient.
	 * @param p Patient.
	 * @return a patient.
	 */
	public static Patient resetPatient(Patient p){
		Patient patient = new Patient(p.name,p.surname,p.arrivalDate);
		patient.typeArrival = p.typeArrival;
		patient.gravity = p.gravity;
		return patient;
	}

	/**
	 * Add waiting time to a patient.
	 * @param time Time to be added. 
	 */
	public void addWaitingTime(int time) {
		this.GlobalWaitInSeconds += time;
	}

	/* Getters & Setters */
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return the gravity
	 */
	public Gravity getGravity() {
		return gravity;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @return the listState
	 */
	public Map<State, Integer> getListState() {
		return listState;
	}

	/**
	 * @return the listWaitTime
	 */
	public Map<State, Integer> getListWaitTime() {
		return listWaitTime;
	}

	/**
	 * @return the globalWaitInSeconds
	 */
	public int getGlobalWaitInSeconds() {
		return GlobalWaitInSeconds;
	}

	/**
	 * @return the bedroom
	 */
	public Bedroom getBedroom() {
		return bedroom;
	}

	/**
	 * @return the arrivalDate
	 */
	public int getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @return the typeArrival
	 */
	public boolean isTypeArrival() {
		return typeArrival;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @param gravity the gravity to set
	 */
	public void setGravity(Gravity gravity) {
		this.gravity = gravity;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @param listState the listState to set
	 */
	public void setListState(Map<State, Integer> listState) {
		this.listState = listState;
	}

	/**
	 * @param listWaitTime the listWaitTime to set
	 */
	public void setListWaitTime(Map<State, Integer> listWaitTime) {
		this.listWaitTime = listWaitTime;
	}

	/**
	 * @param globalWaitInSeconds the globalWaitInSeconds to set
	 */
	public void setGlobalWaitInSeconds(int globalWaitInSeconds) {
		GlobalWaitInSeconds = globalWaitInSeconds;
	}

	/**
	 * @param bedroom the bedroom to set
	 */
	public void setBedroom(Bedroom bedroom) {
		this.bedroom = bedroom;
	}

	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(int arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @param typeArrival the typeArrival to set
	 */
	public void setTypeArrival(boolean typeArrival) {
		this.typeArrival = typeArrival;
	}

	/**
	 * @param state the state to set
	 * @param time the time to set
	 */
	public void setState(State state, int time) {
		if(state != State.WAITING && state != State.AVAILABLE ) {
			this.state = State.OCCUPIED;
			listState.put(state, time);
		}else			
			this.state = state;
			
	}

	@Override
	public String toString() {
		String str = "Patient " + "(parcours " + gravity + ") " + name + " " + surname + " : ";
		for (Entry<State, Integer> i : listState.entrySet()) {
			str += Utils.timeIntToString(i.getValue()) + " " + i.getKey() + " | ";
		}
		if(listWaitTime.size()>0)
			str += System.getProperty("line.separator") + "Temps d'attente : ";
		for (Entry<State, Integer> i : getListWaitTime().entrySet()) {
			str += Utils.timeIntToString(i.getValue()) + " " + i.getKey() + " | ";
		}
		str += "Global waiting time : " + Utils.timeIntToString(GlobalWaitInSeconds);
		return str;
	}

}
