package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import utils.Data;
import utils.SortPatientTime;

/**
 * Class containing all the necessary methods to create a new waiting list.
 *
 */
public class WaitingList implements Serializable{

	private static final long serialVersionUID = -323374299864244714L;
	private ArrayList<Patient> listA;
	private ArrayList<Patient> listB;
	private ArrayList<Patient> listC;
	private ArrayList<Patient> listD;

	private State stateList;


	/**
	 * Constructor of waiting list, creates all the list.
	 */
	public WaitingList() {
		listA = new ArrayList<>();
		listB = new ArrayList<>();
		listC = new ArrayList<>();
		listD = new ArrayList<>();
		stateList = null;
	}

	/**
	 * Constructor of waiting list, creates all the list, with a specified state.
	 */
	public WaitingList(State state) {
		listA = new ArrayList<>();
		listB = new ArrayList<>();
		listC = new ArrayList<>();
		listD = new ArrayList<>();
		stateList = state;
	}

	/**
	 * Add a patient to list depending of his gravity.
	 * @param p Patient to be added.
	 */
	public void add(Patient p) {
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

	/**
	 * Sizes of all the list combined.
	 * @return
	 */
	public int size() {
		return listA.size()+listB.size()+listC.size()+listD.size();
	}

	/**
	 * Removes patient from list depending of his gravity.
	 * @param p Patient to delete.
	 */
	public void remove(Patient p) {
		p.setState(State.AVAILABLE);
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
	 * Check if a patient is contained in one of the waiting list.
	 * @param p Patient we are looking for.
	 * @return True if the patient is contained in one of the list, null otherwise.
	 */
	public Boolean contains(Patient p) {
		switch(p.getGravity()){
	       case A:
	    	   return listA.contains(p);
	       case B:
	    	   return listB.contains(p);
	       case C:
	    	   return listD.contains(p);
	       case D:
	           return listD.contains(p);
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

	/**
	 * Check if in a given list of resources if some are available, comparing them to the actual time of the simulation.
	 * @param listR List of resources to check state.
	 * @return A list of available resources.
	 */
	public static ArrayList<?> verifyRessourceAvailable(List<?> listR) {
			ArrayList<Ressource> rl = new ArrayList<>();
			for (int i = 0; i < listR.size(); i++) {
				int lastTimeR = ((Ressource) listR.get(i)).getListState().get((State)State.AVAILABLE);
				if (lastTimeR <= Data.getTimeValue()) {
					((Ressource)listR.get(i)).setState(State.AVAILABLE);
					((Ressource)listR.get(i)).getListState().put(State.AVAILABLE, Data.getTimeValue());
				}
				else
					((Ressource)listR.get(i)).setState(State.OCCUPIED);
				if (((Ressource)listR.get(i)).getState() == State.AVAILABLE)
					rl.add(((Ressource)listR.get(i)));
			}
			return rl;
	}

	/**
	 * Cheks if a bedroom is available or not.
	 * @param listR List of bedrooms.
	 * @return a list of bedrooms containing all the available bedrooms.
	 */
	public static ArrayList<Bedroom> verifyRessourceAvailableBedroom(List<Bedroom> listR) {
			ArrayList<Bedroom> rl = new ArrayList<>();
			for (int i = 0; i < listR.size(); i++) {
				//Get last state of ressource
				if (listR.get(i).getState() == State.AVAILABLE)
					rl.add(listR.get(i));
			}
			return rl;
	}

	/**
	 * Checks if a patient is available or not, depending of its last time registered in his list of state(s).
	 * @param p Patient to check.
	 * @param r Resource to check.
	 * @return True if a patient can use the resource, false otherwise.
	 */
	public static boolean verifyPatientTime(Patient p, Ressource r) {
		//Get last state of patient
		List<Entry<State,Integer>> entryList = new ArrayList<>(p.getListState().entrySet());
		Entry<State, Integer> lastEntry = entryList.get(entryList.size()-1);

		//Get last state of ressource
		int lastTimeR = r.getListState().get((State)State.AVAILABLE);

		//Waiting time
		int lastTime = p.getListState().get(lastEntry.getKey());

		if ((lastTime <= Data.getTimeValue() && lastTimeR <= Data.getTimeValue() ))
			return true;
		else
			return false;

	}

	/**
	 * Get the next patient index in the list. Check if this patient is available or not.
	 * @param list List of patients to check.
	 * @param r Ressource on which we will test if a patient can be added.
	 * @return The index of the next patient in the list, or -1 if no patient is selected.
	 */
	public int getNextPatientIndex(ArrayList<Patient> list, Ressource r) {
		this.sort();
		if (!list.isEmpty()) {
			int i = 0;
			boolean available = false;
			while (!available && i < list.size()) {
				available = verifyPatientTime(list.get(i), r);
				i++;
			}
			if (available) { //i is incremented but the value found in the test is using i - 1
				return --i;
			}
		}
		return -1;
	}

	/**
	 * Attribute the next patient that will use the resource reception.
	 * @param r Receptionist on which we will add or not a patient.
	 * @param d Data.
	 * @return A patient if a patient is selected or null if no patient has been selected.
	 */
	public Patient attributeNextPatientReception(Receptionist r, Data d) {
		if (r.getState()==State.AVAILABLE) {
			ArrayList<Patient> p = new ArrayList<>();
			for (int i = 0; i < d.getWaitListArrival().size(); i++) {
				p.add(d.getWaitListArrival().get(i));
			}
			if (!p.isEmpty() && getNextPatientIndex(p, r) != -1) {
				return p.get(getNextPatientIndex(p, r));
			}
		}
		return null;
	}

	/**
	 * Attribute the next patient for an available ressource.
	 * For this, we first check if the ressource if available, then we use function {@link Model#getNextPatientIndex(ArrayLit<Patient>, Ressource) getNextPatientIndex method} to check if there is a patient in wainting to use this ressource.
	 * @param res Ressource on which we will try to add a patient.
	 * @param d Data.
	 * @return A next patient selected for the ressource if there is one, null otherwise.
	 */
	public Patient attributeNextPatient(Ressource res, Data d) {
		if (res.getState()==State.AVAILABLE) {
			if (!listA.isEmpty() && getNextPatientIndex(listA, res) != -1)
				return listA.get(getNextPatientIndex(listA, res));
			else if (!listB.isEmpty() && getNextPatientIndex(listB, res) != -1)
				return listB.get(getNextPatientIndex(listB, res));
			else if (!listC.isEmpty() && getNextPatientIndex(listC, res) != -1)
				return listC.get(getNextPatientIndex(listC, res));
			else if (!listD.isEmpty() && getNextPatientIndex(listD, res) != -1)
				return listD.get(getNextPatientIndex(listD, res));
		}
		return null;
	}

	/*
	 * Sort all the list contained in this class.
	 */
	public void sort() {
		if (!this.listA.isEmpty())
			this.listA.sort(new SortPatientTime());
		if (!this.listB.isEmpty())
			this.listB.sort(new SortPatientTime());
		if (!this.listC.isEmpty())
			this.listC.sort(new SortPatientTime());
		if (!this.listD.isEmpty())
			this.listD.sort(new SortPatientTime());
	}
	

	/**
	 * @return the listA
	 */
	public ArrayList<Patient> getListA() {
		return listA;
	}

	/**
	 * @param listA the listA to set
	 */
	public void setListA(ArrayList<Patient> listA) {
		this.listA = listA;
	}

	/**
	 * @return the listB
	 */
	public ArrayList<Patient> getListB() {
		return listB;
	}

	/**
	 * @param listB the listB to set
	 */
	public void setListB(ArrayList<Patient> listB) {
		this.listB = listB;
	}

	/**
	 * @return the listC
	 */
	public ArrayList<Patient> getListC() {
		return listC;
	}

	/**
	 * @param listC the listC to set
	 */
	public void setListC(ArrayList<Patient> listC) {
		this.listC = listC;
	}

	/**
	 * @return the listD
	 */
	public ArrayList<Patient> getListD() {
		return listD;
	}

	/**
	 * @param listD the listD to set
	 */
	public void setListD(ArrayList<Patient> listD) {
		this.listD = listD;
	}

	/**
	 * @return the stateList
	 */
	public State getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(State stateList) {
		this.stateList = stateList;
	}

	/**
	 * method to print a WaitingList
	 */
	@Override
	public String toString() {
		String str = " ";
		for(Patient p : listA) {
			str += p.getName() + "|";
		}
		for(Patient p : listB) {
			str += p.getName() + "|";
		}
		for(Patient p : listC) {
			str += p.getName() + "|";
		}
		for(Patient p : listD) {
			str += p.getName() + "|";
		}
		return str;
	}
}
