package back;

import java.util.ArrayList;

import events.PatientLeave;

/**
 * Class containing all the necessary methods to create a new waiting list.
 *
 */

public class WaitingList{

	private ArrayList<Patient> listA;
	private ArrayList<Patient> listB;
	private ArrayList<Patient> listC;
	private ArrayList<Patient> listD;
	

	public WaitingList() {
		listA = new ArrayList<Patient>();
		listB = new ArrayList<Patient>();
		listC = new ArrayList<Patient>();
		listD = new ArrayList<Patient>();
	}
	
	public void add(Patient p, int time) {
		p.setState(State.WAITING, time);
		switch(p.getGravity()){
		   
	       case A: 
	    	   listA.add(p);
	           break;
	   
	       case B:
	    	   listB.add(p);
	           break;
	   
	       case C:
	    	   listD.add(p);
	           break;
	       case D:
	           listD.add(p);
	           break;
	   }
	}
	
	public int size() {
		return listA.size()+listB.size()+listC.size()+listD.size();
	}
	
	public void remove(Patient p, int time) {
		p.setState(State.AVAILABLE, time);
		switch(p.getGravity()){
	       case A: 
	    	   listA.remove(p);
	           break;
	   
	       case B:
	    	   listB.remove(p);
	           break;
	   
	       case C:
	    	   listD.remove(p);
	           break;
	       case D:
	           listD.remove(p);
	           break;
	   }
	}
	
	/**
	 * Selects the highest priority patient from multiple lists.
	 * 
	 * @param listA Highest priority list.
	 * @param listB 2nd priority list.
	 * @param listC 3rd priority list.
	 * @param listD non-priority list.
	 * @return Next patient to be selected or null if no patient has been selected
	 *         (empty list).
	 */
	public Patient selectPatientFromArrayList() {
		if (listA == null || listB == null || listC == null || listD == null)
			throw new IllegalArgumentException("None of the lists should be null");

		if (!listA.isEmpty()) {
			return listA.get(0);
		} else if (!listB.isEmpty()) {
			return listB.get(0);
		} else if (listC != null && !listC.isEmpty()) {
			return listC.get(0);
		} else if (listD != null && !listD.isEmpty()) {
			return listD.get(0);
		}
		return null;
	}
	
	
	
	// TODO TEST
		/**
		 * Selects the highest priority patient from multiple lists.
		 * 
		 * @param listA Highest priority list.
		 * @param listB 2nd priority list.
		 * @return Next patient to be selected or null if no patient has been selected
		 *         (empty list).
		 */
		public static Patient selectPatientFromArrayList(ArrayList<Patient> listA, ArrayList<Patient> listB) {
			if (listA == null || listB == null)
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
		 * 
		 * @param listA Highest priority list.
		 * @param listB 2nd priority list.
		 * @param listC 3rd priority list.
		 * @return Next patient to be selected or null if no patient has been selected
		 *         (empty list).
		 */
		public Patient selectPatientFromArrayList(ArrayList<Patient> listA, ArrayList<Patient> listB,
				ArrayList<Patient> listC) {
			if (listA == null || listB == null || listC == null)
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
		 * 
		 * @param listA Highest priority list.
		 * @param listB 2nd priority list.
		 * @param listC 3rd priority list.
		 * @param listD non-priority list.
		 * @return Next patient to be selected or null if no patient has been selected
		 *         (empty list).
		 */
		public Patient selectPatientFromArrayList(ArrayList<Patient> listA, ArrayList<Patient> listB,
				ArrayList<Patient> listC, ArrayList<Patient> listD) {
			if (listA == null || listB == null || listC == null || listD == null)
				throw new IllegalArgumentException("None of the lists should be null");

			if (!listA.isEmpty()) {
				return listA.get(0);
			} else if (!listB.isEmpty()) {
				return listB.get(0);
			} else if (listC != null && !listC.isEmpty()) {
				return listC.get(0);
			} else if (listD != null && !listD.isEmpty()) {
				return listD.get(0);
			}
			return null;
		}


}
