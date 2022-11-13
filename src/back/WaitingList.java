package back;

import java.util.ArrayList;

/**
 * Class containing all the necessary methods to create a new waiting list.
 *
 */
public class WaitingList {
	
	private ArrayList<Patient> waitingList;
	
	/**
	 * Constructor of WaitingList, creates an ArrayList. 
	 */
	public WaitingList() {
		waitingList = new ArrayList<>();
	}
	
	/**
	 * Add a patient if is not already in the list and if the patient is not null. 
	 * @param p Patient to be added to the list. 
	 */
	public void addList(Patient p) {
		if (!this.waitingList.contains(p) && p!=null)
			this.waitingList.add(p);
		else
			throw new IllegalArgumentException("Patient is already in the list or null");
	}
	
	/**
	 * Remove a patient from a list if he isn't null. 
	 * @param p Patient to be removed from the list. 
	 */
	public void removeList(Patient p) {
		if (this.waitingList.contains(p) && p!=null)
			this.waitingList.remove(p);
		else
			throw new IllegalArgumentException("Patient is not in the list or null");
	}
	
	//TODO TEST
	/**
	 * Selects the highest priority patient from multiple lists. 
	 * @param listA Highest priority list.
	 * @param listB 2nd priority list.
	 * @return Next patient to be selected or null if no patient has been selected (empty list).
	 */
	public Patient selectPatientFromArrayList(ArrayList<Patient> listA, ArrayList<Patient> listB) {
		if (listA==null || listB==null)
			throw new IllegalArgumentException("None of the lists should be null");
		
		if (!listA.isEmpty()) {
			return listA.get(0);
		} else if (!listB.isEmpty()) {
			return listB.get(0);
		}
		return null;
	}
	
	/**
	 * Selects the highest priority patient from multiple lists. 
	 * @param listA Highest priority list.
	 * @param listB 2nd priority list.
	 * @param listC 3rd priority list.
	 * @return Next patient to be selected or null if no patient has been selected (empty list).
	 */
	public Patient selectPatientFromArrayList(ArrayList<Patient> listA, ArrayList<Patient> listB, ArrayList<Patient> listC) {
		if (listA==null || listB==null || listC==null)
			throw new IllegalArgumentException("None of the lists should be null");
		
		if (!listA.isEmpty()) {
			return listA.get(0);
		} else if (!listB.isEmpty()) {
			return listB.get(0);
		} else if (!listC.isEmpty()) {
			return listC.get(0);
		}
		return null;
	}
	
	/**
	 * Selects the highest priority patient from multiple lists. 
	 * @param listA Highest priority list.
	 * @param listB 2nd priority list.
	 * @param listC 3rd priority list.
	 * @param listD non-priority list.
	 * @return Next patient to be selected or null if no patient has been selected (empty list).
	 */ 
	public Patient selectPatientFromArrayList(ArrayList<Patient> listA, ArrayList<Patient> listB, ArrayList<Patient> listC, ArrayList<Patient> listD) {
		if (listA==null || listB==null || listC==null || listD==null)
			throw new IllegalArgumentException("None of the lists should be null");
		
		if (!listA.isEmpty()) {
			return listA.get(0);
		} else if (!listB.isEmpty()) {
			return listB.get(0);
		} else if (listC!=null && !listC.isEmpty()) {
			return listC.get(0);
		} else if (listD!=null && !listD.isEmpty()) {
			return listD.get(0);
		}
		return null;
	}

}
