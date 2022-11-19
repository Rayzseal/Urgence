package events;

import back.Patient;
import utils.Data;

public class EndScanner implements Runnable{
	Patient patient;
	Data data;

	public EndScanner() {
		// TODO Auto-generated constructor stub
	}
	
	public EndScanner(Data d, Patient p) {
		patient = p;
		data = d;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
