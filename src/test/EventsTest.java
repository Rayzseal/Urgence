package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import back.Patient;
import back.Scheduler;
import back.State;

public class EventsTest {

	@Test
	 public void testPaths() {
		Scheduler s = new Scheduler(500);
		s.run();
		int nbCorrectPath = 0;
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
		Scheduler s = new Scheduler(500);
		s.run();
		int nbCorrectPath = 0;
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
		Scheduler s = new Scheduler(500);
		s.run();
		int nbCorrectPath = 0;
		int nbPatient = s.getData().getNbOfPatients();
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
	
	@Test
	public void testList() {
		Scheduler s = new Scheduler(100);
		s.run();
		int nbPatient = s.getData().getNbOfPatients();
		
		assertEquals(s.getData().getPatientsOver().size(), nbPatient);
		assertEquals(s.getData().getPatients().size(), 0);
		assertEquals(s.getData().getPatientsActive().size(), 0);
		
		
		
	}
	

}
