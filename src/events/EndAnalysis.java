package events;

import back.Patient;
import utils.Data;

public class EndAnalysis implements Runnable{	
	Patient patient;
	Data data;

	public EndAnalysis() {
		// TODO Auto-generated constructor stub
	}
	
	public EndAnalysis(Data d, Patient p) {
		patient = p;
		data = d;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
