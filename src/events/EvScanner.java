package events;

import back.Patient;
import utils.Data;

public class EvScanner implements Runnable{
	Patient patient;
	Data data;

	public EvScanner() {
		// TODO Auto-generated constructor stub
	}
	
	public EvScanner(Data d, Patient p) {
		patient = p;
		data = d;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
