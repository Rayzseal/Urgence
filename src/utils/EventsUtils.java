package utils;

import events.EvBloc;
import events.EvScanner;
import model.Gravity;
import model.Patient;
import model.State;
import events.EvPathC;
import events.EvPrescription;

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
			EvPrescription e4 = new EvPrescription(data, p);
			e4.run();
			break;
		}
	}

	public static Gravity setGravity() {
		//TODO remove
		//return Gravity.C;
		
		int g = (int) (Math.random() * 100);
		int statC = -1;//45;
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
	
	public static Boolean patientAvailable(Data data, Patient p, State stateWaitList) {
		Boolean bool = false;
		synchronized (data.getWaitListPathC()) {
			if(data.getWaitListPathC().get(State.WAITING).contains(p)){
				data.getWaitListPathC().get(State.WAITING).remove(p);
				if(data.getWaitListPathC().get(stateWaitList).contains(p))
					data.getWaitListPathC().get(stateWaitList).remove(p);
				bool = true;
			}
		}
		
		return bool;
	}
	

}
