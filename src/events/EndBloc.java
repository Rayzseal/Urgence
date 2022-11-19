package events;

import back.Patient;
import utils.Data;

public class EndBloc implements Runnable{	
	Patient patient;
	Data data;

	public EndBloc() {
		// TODO Auto-generated constructor stub
	}
	
	public EndBloc(Data d, Patient p) {
		patient = p;
		data = d;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
