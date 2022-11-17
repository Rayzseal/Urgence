package back;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing all the necessary methods to create a new waiting list.
 *
 */
public class ExtensionList extends ArrayList<Object>{

	/**
	 * Constructor of WaitingList, creates an ArrayList.
	 */
	public ExtensionList() {
	}
	
	/**
	 * 
	 * -1 if no objcts are available and an index in the list of the first object available
	 */
	public static int objectAvailable(ArrayList<?> list) {
		for(int i = 0 ; i<list.size(); i++){
			if(((Room) list.get(i)).getState()== State.AVAILABLE)
				return i;	
		}
		return -1;
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
