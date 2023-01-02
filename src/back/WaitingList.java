package back;

import java.util.ArrayList;

import events.EvPatientLeave;
import utils.Utils;

/**
 * Class containing all the necessary methods to create a new waiting list.
 *
 */

public class WaitingList{

	private ArrayList<Patient> listA;
	private ArrayList<Patient> listB;
	private ArrayList<Patient> listC;
	private ArrayList<Patient> listD;
	
	private State stateList;
	

	public WaitingList() {
		listA = new ArrayList<Patient>();
		listB = new ArrayList<Patient>();
		listC = new ArrayList<Patient>();
		listD = new ArrayList<Patient>();
		stateList = null;
	}
	
	public WaitingList(State state) {
		listA = new ArrayList<Patient>();
		listB = new ArrayList<Patient>();
		listC = new ArrayList<Patient>();
		listD = new ArrayList<Patient>();
		stateList = state;
	}
	
	public void add(Patient p, int time) {
		p.setState(State.WAITING);
		p.getListWaitTime().put(stateList, time);
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
		p.setState(State.AVAILABLE);
		int timeWait = time - p.getListWaitTime().get(stateList);
		
		p.getListWaitTime().put(stateList, timeWait);
		p.addWaitingTime(timeWait);
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
