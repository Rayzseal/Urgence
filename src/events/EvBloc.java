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
		int blocAvailable = Utils.objectAvailable(data.getBlocs());
		if(blocAvailable >= 0) { 
			synchronized (data.getBlocs()) {
				data.getBlocs().get(blocAvailable).setState(State.OCCUPIED);
		    }
			EndBloc e = new EndBloc(data, patient, blocAvailable);
			e.run();
		}
		else {
			synchronized (data.getWaitListBloc()) {
				data.getWaitListBloc().add(patient, data.getTime());

		    }
		}
		
	}
	

}
