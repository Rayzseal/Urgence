package utils;

import java.util.ArrayList;
import java.lang.IllegalArgumentException;

import back.Patient;

public class Utils {

	public Utils() {
		// TODO Auto-generated constructor stub
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
