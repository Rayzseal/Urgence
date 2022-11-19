package utils;

import back.Patient;
import events.EvBloc;
import events.EvScanner;
import events.Prescription;

public class EventsUtils {


	public static void pathChoice(Data data, Patient p) {
		switch(p.getGravity()){
		   
	       case A: 
	    	   EvBloc e1 = new EvBloc(data, p);
	    	   e1.run();
	           break;
	   
	       case B:
	    	   EvScanner e2 = new EvScanner(data, p);
	    	   e2.run();
	           break;
	   
	       case C:
	    	   //algo parcours C
	    	   System.out.println("parcours C");
	           break;
	       case D:
	           Prescription e4 = new Prescription(data, p);
	    	   e4.run();
	           break;
	   }
	}
	

}
