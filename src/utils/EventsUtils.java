package utils;

import model.Gravity;
import model.Patient;
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
			data.getWaitListBloc().add(p);
			break;
		case B:
			data.getWaitListScanner().add(p);
			break;
		case C:
			// The patient can either do his analysis or his scanner 
			//(at the end, he will have to do both)
			data.getWaitListAnalysis().getListC().add(p);
			data.getWaitListScanner().getListC().add(p);
			break;
		case D:
			data.getWaitListPrescription().add(p);
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


}
