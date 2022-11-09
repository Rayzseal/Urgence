package utils;

import java.util.ArrayList;
import java.lang.IllegalArgumentException;

import back.Patient;

public class Utils {

	public Utils() {
		// TODO Auto-generated constructor stub
	}
	
	//TODO TEST + retirer patient
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
	
	//TODO TEST
	/**
	 * Format a string DAY/HOUR/MIN/SEC with a number of seconds given in argument.
	 * @param secondsTime Number of seconds to get a time from
	 * @return A string with the total waiting time in format days/hours/minutes
	 */
	public String globalWaitingTime(int secondsTime) {
		if (secondsTime<=0)
			throw new IllegalArgumentException("The given argument must be > 0.");
		
		int sec,hour,min,day=0;
		
		min = secondsTime%60;	
		hour = min%60;
		day = hour%60;
		sec = secondsTime - 60*min;
		min -=hour*60;
		hour -=day*60;
		
		if (day>0)
			return day+"day(s) "+hour+":"+min+":"+sec;
		if (hour>0)
			return hour+":"+min+":"+sec;
		if (min>0)
			return "00:"+min+":"+sec;
		else
			return "00:00:"+sec;
	}

}
