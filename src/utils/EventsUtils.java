package utils;

import events.EvBloc;
import events.EvScanner;
import model.Gravity;
import model.Patient;
import model.State;
import events.EvPathC;
import events.EvPrescription;
/**
 * class which contain static methods to help in different events
 */
public class EventsUtils {
	/**
	 * The method direct a Patient p to his right path.
	 * @param data Data
	 * @param p Patient
	 */
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
	/**
	 * The method set the gravity of a patient randomly 
	 * @return gravity Gravity generate
	 */
	public static Gravity setGravity() {
		
		int g = (int) (Math.random() * 100);
		int statC = 60;
		int statB = 40;
		int statA = 1;

		if (g < statA)
			return Gravity.A;
		else if (g < statB)
			return Gravity.B;
		else if (g < statC)
			return Gravity.C;
		return Gravity.D;
	}
	/**
	 * The methods return true if a patients C is available to continue in an activity 
	 * then he is removed from the List Waiting and WaitingList stateWaitingList
	 * and false if the patient is not in the List waiting
	 * @param data Data
	 * @param p Patient
	 * @param stateWaitList State of list to remove patient from
	 * @return bool
	 */
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
