package utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

		day = secondsTime /(24*3600);
		secondsTime -= day*24*3600;


		hour = secondsTime/3600;
		secondsTime -= hour*3600;
		if(hour==24)
			hour = 0;

		min = secondsTime/60;
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
	 * Print the list of any object.
	 * @param list List to display.
	 */
	public static void showList(List<?> list) {
		for (Object element : list)
			System.out.println(element.toString());
	}

	/**
	 * Print a map of any object.
	 * @param list List to display.
	 */
	@SuppressWarnings("rawtypes")
	public static void showMap(Map<?,?> list) {
		Iterator iterator2 = list.entrySet().iterator();
		while (iterator2.hasNext()) {
			Map.Entry mapentry = (Map.Entry) iterator2.next();

			System.out.println("Key : "+mapentry.getKey()+" Value : "+mapentry.getValue());
		}
	}

}
