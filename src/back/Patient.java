package back;

public class Patient {
	
	private String name;
	private String surname;
	//private int age;
	
	private Gravity gravity;
	
	private int arrivalDate;
	
	private int waitInSeconds;
	private int GlobalWaitInSeconds;
	
	private State state;

	public Patient() {
		name= null;
		surname = null;
		arrivalDate = 0;
		state = State.WAITING;
	}

	public Patient(String name, String surname, int arrivalDate) {
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
	
	

	@Override
	public String toString() {
		return "Patient [name=" + name + ", surname=" + surname + ", arrivalDate=" + arrivalDate + "]";
	}

	public void addWaitingTime() {
		this.GlobalWaitInSeconds += this.waitInSeconds;
		this.waitInSeconds = 0;
	}
	
	

}
