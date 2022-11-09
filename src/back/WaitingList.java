package back;

import java.util.ArrayList;

public class WaitingList {
	private ArrayList<Patient> waitingList;
	
	public WaitingList() {
		waitingList = new ArrayList<>();
	}
	
	//TODO TEST
		/**
		 * Selects the highest priority patient from multiple lists. 
		 * @param listA Highest priority list.
		 * @param listB 2nd priority list.
		 * @param listC Null or 3rd priority list.
		 * @param listD Null or non-priority list.
		 * @return Next patient to be selected or null if no patient has been selected (empty list).
		 */
		public Patient selectPatientFromArrayList(ArrayList<Patient> listA, ArrayList<Patient> listB, ArrayList<Patient> listC, ArrayList<Patient> listD) {
			if (listA==null || listB==null)
				throw new IllegalArgumentException("You should give at least 2 valid list to select a patient from");
			
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
