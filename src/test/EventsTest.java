package test;

import static org.junit.Assert.assertEquals;
import org.junit.*;

import back.Patient;
import back.Scheduler;
import back.State;
import events.EvBloc;
import events.EvScanner;
import events.PatientArrival;
import events.Prescription;
import utils.Data;

public class EventsTest {

	@Test
	 public void testPaths() {
		Scheduler s = new Scheduler(50);
		s.run();
		int nbCorrectPath = 0;
		s.getData().getPatientsOver();
		for(Patient p : s.getData().getPatientsOver()) {
			switch(p.getGravity()){
			   
		       case A: 
		    	   
		           break;
		   
		       case B:
		           break;
		   
		       case C:
		    	   //algo parcours C
		    	   System.out.println("parcours C");
		           break;
		       case D:
		           // ARRIVAL, RECEPTION, BEDROOM, PRESCRIPTION, OUT
		    	   if(p.getListState().containsKey(State.ARRIVAL) && 
		    	   p.getListState().containsKey(State.RECEPTION) &&
		    	   p.getListState().containsKey(State.BEDROOM) &&
		    	   p.getListState().containsKey(State.PRESCRIPTION) &&
		    	   p.getListState().containsKey(State.OUT)) {
		    		   nbCorrectPath++;
		    	   }else
		    		   System.out.println(p);
		           break;
		   }
		}
		System.out.println(nbCorrectPath);
		assertEquals(nbCorrectPath, s.getData().getNbOfPatients());
    }
	
	@Test
	 public void testArrival() {
		Scheduler s = new Scheduler(50);
		s.run();
		int nbCorrectPath = 0;
		s.getData().getPatientsOver();
		for(Patient p : s.getData().getPatientsOver()) {
	    	   if(p.getListState().containsKey(State.ARRIVAL)) {
	    		   nbCorrectPath++;
	    	   }else
	    		   System.out.println(p);
		}
		System.out.println(nbCorrectPath);
		assertEquals(nbCorrectPath, s.getData().getNbOfPatients());
   }
	@Test
	 public void testReception() {
		Scheduler s = new Scheduler();
		s.run();
		int nbCorrectPath = 0;
		s.getData().getPatientsOver();
		for(Patient p : s.getData().getPatientsOver()) {
	    	   if(p.getListState().containsKey(State.RECEPTION)) {
	    		   nbCorrectPath++;
	    	   }else
	    		   System.out.println(p);
		}
		System.out.println(nbCorrectPath);
		assertEquals(nbCorrectPath, s.getData().getNbOfPatients());
  }
	

}
