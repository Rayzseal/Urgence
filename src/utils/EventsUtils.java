package utils;

import back.Gravity;
import back.Patient;
import events.EvBloc;
import events.EvScanner;
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
			// algo parcours C
			System.out.println("parcours C");
			break;
		case D:
			Prescription e4 = new Prescription(data, p);
			e4.run();
			break;
		}
	}

	public static Gravity setGravity() {
		int g = (int) (Math.random() * 100);
		// int statD = 60;
		int statC = -1; // TODO
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

}
