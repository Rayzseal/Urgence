package back;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.Map.Entry;

import utils.Utils;

public class Patient {
	
	private String name;
	private String surname;
	//private int age;
	
	private Gravity gravity;
	
	private Bedroom bedroom;
	
	private int arrivalDate;
	
	private int waitInSeconds;
	private int GlobalWaitInSeconds;
	
	private State state;
	
	private Map<State, Integer> listState;

	public Patient() {
		listState = new TreeMap<>();
		name= null;
		surname = null;
		arrivalDate = 0;
		state = State.WAITING;
		bedroom = null;
		
	}

	public Patient(String name, String surname, int arrivalDate) {
		listState = new TreeMap<>();
		this.name = name;
		this.surname = surname;
		this.arrivalDate = arrivalDate;
		waitInSeconds = 0;
		GlobalWaitInSeconds = 0;
		state = State.WAITING;
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

	public void setState(State state, int time) {
		this.state = state;
	}
	
	public Bedroom getBedroom() {
		return bedroom;
	}

	public void setBedroom(Bedroom bedroom) {
		this.bedroom = bedroom;
	}

	@Override
	public String toString() {
		String str = "Patient "+name+" "+surname+" : ";
		for(Entry<State, Integer> i : listState.entrySet()){
			//int time = i.getKey();
			str += Utils.globalWaitingTime(i.getValue()) + " " + i.getKey()+" | ";
		}
		return str;
		//return "Patient [name=" + name + ", surname=" + surname + ", arrivalDate=" + arrivalDate + "]";
	}

	public void addWaitingTime() {
		this.GlobalWaitInSeconds += this.waitInSeconds;
		this.waitInSeconds = 0;
	}
	
	

}
