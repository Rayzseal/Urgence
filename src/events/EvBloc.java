package events;

import java.util.ArrayList;

import back.Patient;
import back.State;
import utils.Data;
import utils.Utils;

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
		int blocAvailable = -1;
		synchronized (data.getBlocs()) {
			blocAvailable = Utils.objectAvailable(data.getBlocs());
			if(blocAvailable >= 0) { 
				
					data.getBlocs().get(blocAvailable).setState(State.OCCUPIED);
			    }
			else {
				synchronized (data.getWaitListBloc()) {
					data.getWaitListBloc().add(patient, data.getTime());
	
			    }
			}
			
		}
		
		if(blocAvailable >= 0) {
			EndBloc e = new EndBloc(data, patient, blocAvailable);
			e.run();
		}
		
	}
	

}
