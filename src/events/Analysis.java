package events;

import java.util.ArrayList;

import back.Patient;
import utils.Data;

public class Analysis implements Runnable{
	Patient patient;
	Data data;

	public Analysis() {
		// TODO Auto-generated constructor stub
	}
	
	public Analysis(Data d, Patient p) {
		patient = p;
		data = d;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
