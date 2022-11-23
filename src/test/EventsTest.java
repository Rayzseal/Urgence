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
		    	 //BEDROOM, BLOC, PRESCRIPTION, OUT
		    	   if(p.getListState().containsKey(State.BEDROOM) &&
		    			   p.getListState().containsKey(State.BLOC) &&
				    	   p.getListState().containsKey(State.PRESCRIPTION) &&
				    	   p.getListState().containsKey(State.OUT)) {
				    		   nbCorrectPath++;
				    	   }		    	   
		           break;
		   
		       case B:
		    	 //BEDROOM, SCANNER, BLOC, PRESCRIPTION, OUT
		    	   if(p.getListState().containsKey(State.BEDROOM) &&		    			  
		    			   p.getListState().containsKey(State.SCANNER) &&
		    			   p.getListState().containsKey(State.BLOC) &&
				    	   p.getListState().containsKey(State.PRESCRIPTION) &&
				    	   p.getListState().containsKey(State.OUT)) {
				    		   nbCorrectPath++;
				    	   }
		           break;
		   
		       case C:
		    	//BEDROOM, ANALYSIS, SCANNER, PRESCRIPTION, OUT
		    	   if(p.getListState().containsKey(State.BEDROOM) &&
		    			   p.getListState().containsKey(State.ANALYSIS) &&
		    			   p.getListState().containsKey(State.SCANNER) &&
				    	   p.getListState().containsKey(State.PRESCRIPTION) &&
				    	   p.getListState().containsKey(State.OUT)) {
				    		   nbCorrectPath++;
				    	   }
		           break;
		       case D:
		           //BEDROOM, PRESCRIPTION, OUT
		    	   if(p.getListState().containsKey(State.BEDROOM) &&
		    	   p.getListState().containsKey(State.PRESCRIPTION) &&
		    	   p.getListState().containsKey(State.OUT)) {
		    		   nbCorrectPath++;
		    	   }
		           break;
		   }
		}
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
		assertEquals(nbCorrectPath, s.getData().getNbOfPatients());
   }
	@Test
	 public void testReception() {
		Scheduler s = new Scheduler();
		s.run();
		int nbCorrectPath = 0;
		int nbPatient = s.getData().getNbOfPatients();
		s.getData().getPatientsOver();
		for(Patient p : s.getData().getPatientsOver()) {
	    	   if(p.getListState().containsKey(State.RECEPTION) && !p.isTypeArrival()) {
	    		   nbCorrectPath++;
	    	   }else if(p.isTypeArrival()){
	    		   nbPatient--;
	    	   }else
	    		   System.out.println(p);
		}
		assertEquals(nbCorrectPath, nbPatient);
  }
	

}
