package back;

import java.util.ArrayList;

/**
 * 
 * Class containing the list of waiting patients for the event events.PatientArrival this list is using a simple fifo system. 
 *
 */
public class WaitingListPatientArrival {
	
	private ArrayList<Patient> list;

	/**
	 * Constructor of WaitingListPatientArrival, creates a list. 
	 */
	public WaitingListPatientArrival() {
		this.list = new ArrayList<>();
	}
	
	/**
	 * Add a patient to the list if he isn't null nor contained in the list. 
	 * @param p Patient to be added. 
	 */
	public void addPatient(Patient p) {
		if (!list.contains(p) && p!=null)
			this.list.add(p);
		else
			throw new IllegalArgumentException("Patient is already in the list or null");
	}
	
	/**
	 * Remove a patient from a list if he isn't null and contained in the list. 
	 * @param p Patient to be removed. 
	 */
	private void removePatient(Patient p) {
		if (this.list.contains(p) && p!=null)
			this.list.remove(p);
		else
			throw new IllegalArgumentException("Patient isn't in the list or null");
	}
	
	/**
	 * Select the next patient to be selected from the list (and deletes him from the list). 
	 * @return The next patient. 
	 */
	public Patient selectPatient() {
		Patient p = this.list.get(0);
		removePatient(p);
		return p;
	}

}
