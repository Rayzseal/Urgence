package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import controller.Statistics;
import model.State;
import utils.DataFile;

public class StatisticsTest {

	/**
	 * Test on average spent time waiting. 
	 */
	@Test
	public void testAverage() {
		int average = Statistics.getAverageWaitingTime(DataFile.readDataFile("SimulationEndTest.csv"));
		assertEquals(average, 87);
	}
	
	/**
	 * Test on average spent time in emergency. 
	 */
	@Test
	public void testAverageSpendTimeEmergency() {
		double average = Statistics.getAverageTimeSpentInEmergency(DataFile.readDataFile("SimulationEndTest.csv"));
		assertEquals(average, 4047.7);
	}
	
	/**
	 * Test on percentage of utilization of each states. 
	 */
	@Test
	public void percentageUtilizationReception() {
		Map<State, Double> mapPercentageBedroom = 
		Statistics.getPercentageUtilizationStates(DataFile.readDataFile("SimulationEndTest.csv"));
		double percentageBedroom = mapPercentageBedroom.get(State.RECEPTION);
		assertEquals(percentageBedroom, (double)15.851768352825138);
	}
	
	/**
	 * Test on percentage of utilization of each states. 
	 */
	@Test
	public void percentageUtilizationBloc() {
		Map<State, Double> mapPercentageBedroom = 
		Statistics.getPercentageUtilizationStates(DataFile.readDataFile("SimulationEndTest.csv"));
		double percentageBedroom = mapPercentageBedroom.get(State.BLOC);
		assertEquals(percentageBedroom, (double)3.170353670565027);
	}
	
	/**
	 * Test on percentage of utilization of each states. 
	 */
	@Test
	public void percentageUtilizationScanner() {
		Map<State, Double> mapPercentageBedroom = 
		Statistics.getPercentageUtilizationStates(DataFile.readDataFile("SimulationEndTest.csv"));
		double percentageBedroom = mapPercentageBedroom.get(State.SCANNER);
		assertEquals(percentageBedroom, (double)6.340707341130054);
	}
	
	/**
	 * Test on percentage of utilization of each states. 
	 */
	@Test
	public void percentageUtilizationPrescription() {
		Map<State, Double> mapPercentageBedroom = 
		Statistics.getPercentageUtilizationStates(DataFile.readDataFile("SimulationEndTest.csv"));
		double percentageBedroom = mapPercentageBedroom.get(State.PRESCRIPTION);
		assertEquals(percentageBedroom, (double)15.851768352825138);
	}
	
	/**
	 * Test on percentage of utilization of each states. 
	 */
	@Test
	public void percentageUtilizationAnalysis() {
		Map<State, Double> mapPercentageBedroom = 
		Statistics.getPercentageUtilizationStates(DataFile.readDataFile("SimulationEndTest.csv"));
		double percentageBedroom = mapPercentageBedroom.get(State.ANALYSIS);
		assertEquals(percentageBedroom, (double)1.5851768352825135);
	}

}
