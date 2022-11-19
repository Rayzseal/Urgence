package events;

import java.util.ArrayList;

import back.Patient;
import utils.Data;

public class EvBloc implements Runnable{
	Patient patient;
	Data data;

	public EvBloc() {
		// TODO Auto-generated constructor stub
	}
	
	public EvBloc(Data d, Patient p) {
		patient = p;
		data = d;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
