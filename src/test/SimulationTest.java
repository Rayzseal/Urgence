package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import controller.Scheduler;
import model.Patient;
import model.State;
/**
 * Test on the simulation
 */
public class SimulationTest {
	private int nbPatient;
	private Scheduler s;

	@Before
	/**
	 * Method called before every test
	 */
	public void testBefore() {
		nbPatient = 50;
		s = new Scheduler(nbPatient);
		s.run();
	}

	@Test
	/**
	 * verify for every patient if they have the right path once they get a bedroom
	 */
	public void testPaths() {
		int nbCorrectPath = 0;
		for (Patient p : s.getData().getPatientsOver()) {
			switch (p.getGravity()) {

			case A:
				// BEDROOM, BLOC, PRESCRIPTION, OUT
				if (p.getListState().containsKey(State.BEDROOM) 
						&& p.getListState().containsKey(State.BLOC)
						&& p.getListState().containsKey(State.PRESCRIPTION)
						&& p.getListState().containsKey(State.OUT)) {
					nbCorrectPath++;
				}
				break;

			case B:
				// BEDROOM, SCANNER, BLOC, PRESCRIPTION, OUT
				if (p.getListState().containsKey(State.BEDROOM) 
						&& p.getListState().containsKey(State.SCANNER)
						&& p.getListState().containsKey(State.BLOC) 
						&& p.getListState().containsKey(State.PRESCRIPTION)
						&& p.getListState().containsKey(State.OUT)) {
					nbCorrectPath++;
				}
				break;

			case C:
				// BEDROOM, ANALYSIS, SCANNER, PRESCRIPTION, OUT
				if (p.getListState().containsKey(State.BEDROOM) 
						&& p.getListState().containsKey(State.ANALYSIS)
						&& p.getListState().containsKey(State.SCANNER)
						&& p.getListState().containsKey(State.PRESCRIPTION)
						&& p.getListState().containsKey(State.OUT)) {
					nbCorrectPath++;
				}
				break;
			case D:
				// BEDROOM, PRESCRIPTION, OUT
				if (p.getListState().containsKey(State.BEDROOM) 
						&& p.getListState().containsKey(State.PRESCRIPTION)
						&& p.getListState().containsKey(State.OUT)) {
					nbCorrectPath++;
				}
				break;
			}
		}
		assertEquals(nbCorrectPath, nbPatient);
	}

	@Test
	/**
	 * verify for every patient if they arrive
	 */
	public void testArrival() {
		Scheduler s = new Scheduler(nbPatient);
		s.run();
		int nbCorrectPath = 0;
		for (Patient p : s.getData().getPatientsOver()) {
			if (p.getListState().containsKey(State.ARRIVAL)) {
				nbCorrectPath++;
			}
		}
		assertEquals(nbCorrectPath, nbPatient);
	}

	@Test
	/**
	 * verify the arrival of every patient is correct, only patient from non
	 * critical arrival should go to the reception
	 */
	public void testReception() {
		Scheduler s = new Scheduler(nbPatient);
		s.run();
		int nbCorrectPath = 0;
		for (Patient p : s.getData().getPatientsOver()) {
			if (p.getListState().containsKey(State.RECEPTION) && !p.isTypeArrival()) {
				nbCorrectPath++;
			} else if (!p.getListState().containsKey(State.RECEPTION) && p.isTypeArrival()) {
				nbPatient--;
			}
		}
		assertEquals(nbCorrectPath, nbPatient);
	}

	@Test
	/**
	 * verify if every patient finished his path at the end of the simulation
	 */
	public void testList() {
		Scheduler s = new Scheduler(nbPatient);
		s.run();
		assertEquals(s.getData().getPatientsOver().size(), nbPatient);
		assertEquals(s.getData().getPatients().size(), 0);
		assertEquals(s.getData().getPatientsActive().size(), 0);

	}

}
