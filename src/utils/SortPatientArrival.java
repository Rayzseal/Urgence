package utils;

import java.util.Comparator;

import model.Patient;

/**
 *
 * Class to sort patients arrival, compares the time when the patient arrives.
 *
 */
public class SortPatientArrival implements Comparator<Patient>{

	@Override
	public int compare(Patient o1, Patient o2) {
		return o1.getArrivalDate() - o2.getArrivalDate();
	}

}
