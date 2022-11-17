package utils;

import java.util.ArrayList;

import back.Data;
import back.Gravity;
import back.Patient;
import back.Room;
import back.State;
import events.PatientLeave;

public class Utils {
	
	public static String[] names = { "Olivia", "Amelia", "Isla", "Ava", "Mia", "Ivy", "Lily", "Oliver", "George",
			"Arthur", "Noah", "Muhammad", "Leo", "Oscar", "Harry", "Archie", "Jack", "Liam", "Jackson", "Aiden",
			"Grayson", "Lucas", "Emily", "Ashley", "Alyssa" };

	public static String[] surnames = { "Anderson", "Brown", "Byrne", "Clark", "Cooper", "Davies", "Evans", "Garcia",
			"Gonzalez", "Green", "Hall", "Harris", "Hernandez", "Hughes", "Jackson", "Johnson", "Jones", "Lam", "Lee",
			"Lewis", "Lopez", "Martin", "Miller", "O'connor", "O'Neil" };

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
	

	public static void showList(ArrayList<?> list) {
		for (int i = 0; i < list.size(); i++)
			System.out.println(list.get(i).toString());
	}
	
	public static void pathChoice(Data data, Patient p) {
		switch(p.getGravity()){
		   
	       case A: 
	           System.out.println("Bonjour");
	           break;
	   
	       case B:
	           System.out.println("Hello");
	           break;
	   
	       case C:
	           System.out.println("Buenos dias");
	           break;
	       case D:
	           //TODO parcours D
	    	   //for now
	    	   try {
				Thread.sleep(10000/5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	   PatientLeave pl = new PatientLeave(data, p);
	    	   pl.run();
	           break;
	   }
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

}
