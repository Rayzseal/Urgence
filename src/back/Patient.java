package back;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.Map.Entry;

import utils.Utils;

public class Patient {

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

	public Patient() {
		patient();
		name = null;
		surname = null;
		arrivalDate = 0;

	}

	public Patient(String name, String surname, int arrivalDate) {
		patient();
		this.name = name;
		this.surname = surname;
		this.arrivalDate = arrivalDate;

	}

	/**
	 * function to initialize patient
	 */
	private void patient() {
		listState = new TreeMap<>();
		listWaitTime = new TreeMap<>();

		typeArrival = false; // TODO gravité

		state = State.AVAILABLE;
		bedroom = null;

		GlobalWaitInSeconds = 0;
	}

	public void addWaitingTime(int time) {
		// this.startWait = time - this.startWait;
		// this.GlobalWaitInSeconds += this.startWait;
		// this.startWait = 0;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public int getGlobalWaitInSeconds() {
		return GlobalWaitInSeconds;
	}

	public Gravity getGravity() {
		return gravity;
	}

	public Map<State, Integer> getListState() {
		return listState;
	}

	public Map<State, Integer> getListWaitTime() {
		return listWaitTime;
	}

	public void setListWaitTime(Map<State, Integer> listWaitTime) {
		this.listWaitTime = listWaitTime;
	}

	public boolean isTypeArrival() {
		return typeArrival;
	}

	public void setTypeArrival(boolean typeArrival) {
		this.typeArrival = typeArrival;
	}

	public void setListState(TreeMap<State, Integer> listState) {
		this.listState = listState;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setGlobalWaitInSeconds(int globalWaitInSeconds) {
		GlobalWaitInSeconds = globalWaitInSeconds;
	}

	public void setGravity(Gravity gravity) {
		this.gravity = gravity;
	}

	public int getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(int arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setState(State state, int time) {
		if(state != State.WAITING && state != State.AVAILABLE ) {
			this.state = State.OCCUPIED;
			listState.put(state, time);
		}else			
			this.state = state;
			
	}

	public Bedroom getBedroom() {
		return bedroom;
	}

	public void setBedroom(Bedroom bedroom) {
		this.bedroom = bedroom;
	}

	public void setListState(Map<State, Integer> listState) {
		this.listState = listState;
	}

	@Override
	public String toString() {
		String str = "Patient " + "(parcours " + gravity + ") " + name + " " + surname + " : ";
		for (Entry<State, Integer> i : listState.entrySet()) {
			str += Utils.globalWaitingTime(i.getValue()) + " " + i.getKey() + " | ";
		}
		str += "Global waiting time : " + Utils.globalWaitingTime(GlobalWaitInSeconds);
		return str;
	}

}
