package utils;

import java.util.List;

import model.Ressource;
import model.State;

/**
 * 
 * Class used to generated patients (using a random name & surname).
 * Can also format a number of seconds to DAY/HOUR/MIN/SEC format.
 * Loops through a list to find an object available. 
 * Display a list on screen. 
 *
 */
public class Utils {
	
	public static String[] names = { "Olivia", "Amelia", "Isla", "Ava", "Mia", "Ivy", "Lily", "Oliver", "George",
			"Arthur", "Noah", "Muhammad", "Leo", "Oscar", "Harry", "Archie", "Jack", "Liam", "Jackson", "Aiden",
			"Grayson", "Lucas", "Emily", "Ashley", "Alyssa" };

	public static String[] surnames = { "Anderson", "Brown", "Byrne", "Clark", "Cooper", "Davies", "Evans", "Garcia",
			"Gonzalez", "Green", "Hall", "Harris", "Hernandez", "Hughes", "Jackson", "Johnson", "Jones", "Lam", "Lee",
			"Lewis", "Lopez", "Martin", "Miller", "O'connor", "O'Neil" };

	/**
	 * Format a string DAY/HOUR/MIN/SEC with a number of seconds given in argument.
	 * @param secondsTime Number of seconds to get a time from
	 * @return A string with the total waiting time in format days/hours/minutes
	 */
	public static String timeIntToString(int secondsTime) {
		if (secondsTime<0)
			throw new IllegalArgumentException("The given argument must be >= 0.");
		
		int sec=0,hour=0,min=0,day=0;
		
		day = (int) secondsTime /(24*3600);
		secondsTime -= day*24*3600;
		
		
		hour = (int) secondsTime/3600;
		secondsTime -= hour*3600;
		if(hour==24)
			hour = 0;
		
		min = (int) secondsTime/60;
		secondsTime -= min*60;
		
		sec = secondsTime;
		
		String str = "";
		
		if (day>0)
			str += day+" day(s) ";
		if(hour==0)
			str+="0";
		if (hour>0)
			if(hour <10)
				str+="0";
			str+=hour+":";
		if(min==0)
			str += "0";
		if (min>0)
			if(min<10)
				str+="0";
			str+=min+":";
		if(sec<10)
			str += "0";
		return str + sec;
	}
	
	/**
	 * Print the list of any object
	 * @param list List to display.
	 */
	public static void showList(List<?> list) {
		for (int i = 0; i < list.size(); i++)
			System.out.println(list.get(i).toString());
	}
	
	
	/**
	 * -1 if objects are not available or an index in the list of the first object available
	 * @param list List in which we are looking for an available resource. 
	 * @return The index of an available object, or -1 if no objects or available.
	 */
	public static int objectAvailable(List<?> list) {
		for(int i = 0 ; i<list.size(); i++){
			if(((Ressource) list.get(i)).getState()== State.AVAILABLE)
				return i;	
		}
		return -1;
	}

}
