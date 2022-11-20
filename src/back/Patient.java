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

	private Bedroom bedroom;

	private int arrivalDate;
	private boolean arrival; //false by himself

	private int waitInSeconds;
	private int GlobalWaitInSeconds;

	public Patient() {
		listState = new TreeMap<>();
		name = null;
		surname = null;
		arrivalDate = 0; //TODO gravité
		arrival = false;
		state = State.WAITING;
		bedroom = null;

	}

	public Patient(String name, String surname, int arrivalDate) {
		listState = new TreeMap<>();
		this.name = name;
		this.surname = surname;
		this.arrivalDate = arrivalDate;
		arrival = false; //TODO gravité
		waitInSeconds = 0;
		GlobalWaitInSeconds = 0;
		state = State.AVAILABLE;
	}
	

	public void addWaitingTime(int time) {
		this.waitInSeconds = time - this.waitInSeconds;
		this.GlobalWaitInSeconds += this.waitInSeconds;
		this.waitInSeconds = 0;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public int getWaitInSeconds() {
		return waitInSeconds;
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

	public void setListState(TreeMap<State, Integer> listState) {
		this.listState = listState;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setWaitInSeconds(int waitInSeconds) {
		this.waitInSeconds = waitInSeconds;
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
		this.state = state;
	}

	public Bedroom getBedroom() {
		return bedroom;
	}

	public void setBedroom(Bedroom bedroom) {
		this.bedroom = bedroom;
	}
	

	public boolean isArrival() {
		return arrival;
	}

	public void setArrival(boolean arrival) {
		this.arrival = arrival;
	}


	public void setListState(Map<State, Integer> listState) {
		this.listState = listState;
	}

	@Override
	public String toString() {
		String str = "Patient " + name + " " + surname + " : ";
		for (Entry<State, Integer> i : listState.entrySet()) {
			// int time = i.getKey();
			str += Utils.globalWaitingTime(i.getValue()) + " " + i.getKey() + " | ";
		}
		str+= "Global waiting time : "+ Utils.globalWaitingTime(GlobalWaitInSeconds);
		return str;
	}

}
