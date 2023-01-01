package utils;

import back.Gravity;
import back.Patient;
import back.State;
import events.EvBloc;
import events.EvScanner;
import events.EvPathC;
import events.Prescription;

public class EventsUtils {

	public static void pathChoice(Data data, Patient p) {
		switch (p.getGravity()) {

		case A:
			EvBloc e1 = new EvBloc(data, p);
			e1.run();
			break;

		case B:
			EvScanner e2 = new EvScanner(data, p);
			e2.run();
			break;

		case C:
			EvPathC e3 = new EvPathC(data, p);
			e3.run();
			break;
		case D:
			Prescription e4 = new Prescription(data, p);
			e4.run();
			break;
		}
	}

	public static Gravity setGravity() {
		//TODO remove
		//return Gravity.C;
		
		int g = (int) (Math.random() * 100);
		int statC = -1;
		int statB = 30;
		int statA = 5;

		if (g < statA)
			return Gravity.A;
		else if (g < statB)
			return Gravity.B;
		else if (g < statC)
			return Gravity.C;
		return Gravity.D;
	}
	

	public static Boolean patientAvailableScanner(Data data, Patient p) {
		Boolean bool = false;
		synchronized (data.getWaitListPathC()) {
			synchronized (data.getWaitListAnalysis()) {
				if(data.getWaitListPathC().contains(p)){
					data.getWaitListPathC().remove(p);
					if(data.getWaitListAnalysis().contains(p))
						data.getWaitListAnalysis().remove(p);
					bool = true;
				}
			}
			
		}
		
		return bool;
	}
	public static Boolean patientAvailableAnalysis(Data data, Patient p) {
		Boolean bool = false;
		synchronized (data.getWaitListPathC()) {
			synchronized (data.getWaitListScanner()) {
				if(data.getWaitListPathC().contains(p)){
					data.getWaitListPathC().remove(p);
					if(data.getWaitListScanner().contains(p))
						data.getWaitListScanner().remove(p);
					bool = true;
				}
			}
			
		}
		
		return bool;
	}
	

}
