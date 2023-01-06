package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Gravity;
import model.Patient;
import model.State;
import utils.Data;
import utils.Utils;

/**
 * 
 * Class statistics. 
 * Contain all functions to generate statistics using a specified execution (data). 
 *
 */
public class Statistics {
	
	/**
	 * Display all the possible statictics. 
	 * @param data Data from which are extracted statistics.
	 */
	public static void statistics(Data data) {
		System.out.print("Average global waiting time : ");
		System.out.println(Utils.timeIntToString(Statistics.getAverageWaitingTime(data)));
	}
	
	/**
	 * Get the average waiting time for all patients. 
	 * @param data Data from which are extracted statistics. 
	 * @return Average waiting time. 
	 */
	public static int getAverageWaitingTime(Data data) {
		int time = 0;
		for(Patient p : data.getPatientsOver()) {
			time+=p.getGlobalWaitInSeconds();
		}
		time = time/data.getPatientsOver().size();
		return time;
	}
	
	/**
	 * Get the average waiting time for each gravity. 
	 * @param data Data from which are extracted statistics. 
	 * @return A map with a key = the gravity & a value = the average waiting time. 
	 */
	public static Map<Gravity, Integer> getAverageWaitingTimeByGravity(Data data){
		Map<Gravity, Integer> time = new HashMap<Gravity, Integer>();
		Map<Gravity, Integer> nbPatient = getNumberOfPatientByPath(data);
		for(Gravity g : Gravity.values()) {
			time.put(g, 0);
		}
		for(Patient p : data.getPatientsOver()) {
			int t = time.get(p.getGravity()).intValue() + p.getGlobalWaitInSeconds();
			time.put(p.getGravity(), t);
		}
		for(Gravity g : Gravity.values()) {
			int t = time.get(g).intValue() /nbPatient.get(g);
			time.put(g, t);
		}
		return time;
	}
	
	//TODO check if equal to time for a state (if we subtract waiting time for each state)
	/**
	 * Get the number of patients that patients spend in each states. 
	 * @param data Data from which are extracted statistics. 
	 * @return A map with a key = the state & a value = the average time time that a patient spent in a state. 
	 */
	@SuppressWarnings("rawtypes")
	public static Map<State, Integer> getAverageSpendTimeInEachState(Data data){
		Map<State, Integer> average = new TreeMap<State, Integer>();
		Map<State, Integer> nbPatientsByState = new TreeMap<State, Integer>();
		
		//Initialization
		for (State s : State.values()) {
			average.put(s, 0);
			nbPatientsByState.put(s, 0);
		}
		
		//For all patients
		for(Patient p : data.getPatientsOver()) {
			Iterator iterator = p.getListState().entrySet().iterator();
			List<Integer> timeValuesForPatient = new ArrayList<Integer>();
			List<State> statesForPatient = new ArrayList<State>();
			
			//For each states that the patient went through
			while (iterator.hasNext()) {
				Map.Entry mapentry = (Map.Entry) iterator.next();
				timeValuesForPatient.add((int)mapentry.getValue());
				statesForPatient.add((State)mapentry.getKey());
			}
			
			//Sort the values of time
			Collections.sort(timeValuesForPatient);
			
			//Time values
			for (int i = 0 ; i < timeValuesForPatient.size()-1; i++) {
				int patientTime = 0;
				State patientState = null;
				/* Substraction to know how much time a patient stayed in each state
				   Next state begin time - atual state begin time 					*/
				patientTime = timeValuesForPatient.get(i+1) - timeValuesForPatient.get(i);
				patientState = statesForPatient.get(i+1);
				
				int getTime = average.get(patientState);
				
				//Add values to hashmap
				average.put(patientState, patientTime + getTime);
				int nbPatients = nbPatientsByState.get(patientState) ;
				nbPatientsByState.put(patientState, nbPatients + 1);
			}
			
		}
		
		// Divide by number of patients for each state
		Iterator iterator = average.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry mapentry = (Map.Entry) iterator.next();
			int time = 0;
			
			if (nbPatientsByState.get(mapentry.getKey()) > 0)
				time = (int)mapentry.getValue() / nbPatientsByState.get(mapentry.getKey());
			
			average.put((State)mapentry.getKey(), time);
		}
		
		return average;
	}
	
	//TODO TO TEST
	/**
	 * Get the percentage of utilization for each states. 
	 * @param data Data from which are extracted statistics. 
	 * @return A map with a key = the state & a value = percentage of utilization. 
	 */
	@SuppressWarnings("rawtypes")
	public static Map<State, Double> getPercentageUtilizationStates(Data data) {
		Map<State, Double> percentage = new TreeMap<State, Double>();
		Map<State, Integer> nbPatientForEachState = new TreeMap<State, Integer>();
		
		//Initialization
		for (State s : State.values()) {
			percentage.put(s, 0.0);
			nbPatientForEachState.put(s, 0);
		}
		
		//For all patients
		for(Patient p : data.getPatientsOver()) {
			Iterator iterator = p.getListState().entrySet().iterator();
			List<State> statesForPatient = new ArrayList<State>();
			
			//For each states that the patient went through
			while (iterator.hasNext()) {
				Map.Entry mapentry = (Map.Entry) iterator.next();
				statesForPatient.add((State)mapentry.getKey());
			}
			
			//All states of a patient values
			for (int i = 0 ; i < statesForPatient.size()-1; i++) {
				int nbPatientForState = nbPatientForEachState.get(statesForPatient.get(i));
				//increment value
				nbPatientForEachState.put(statesForPatient.get(i), nbPatientForState + 1);
			}
		}
		
		// Divide to get the percentage of utilization for each state
		Iterator iterator = nbPatientForEachState.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry mapentry = (Map.Entry) iterator.next();
			double time = 0.0;
			
			switch ((State)mapentry.getKey()) {
				case RECEPTION : 
					time = ((double)(nbPatientForEachState.get(mapentry.getKey()).intValue()/ data.getReceptionists().size()) * data.getTimeReception()) / Data.nbSecondsPerDay;
				case SCANNER : 
					time = ((double)(nbPatientForEachState.get(mapentry.getKey()).intValue() / data.getScanners().size()) * data.getTimeScanner()) / Data.nbSecondsPerDay;
				case ANALYSIS : 
					time = ((double)(nbPatientForEachState.get(mapentry.getKey()).intValue() / data.getNurses().size()) * data.getTimeAnalysis()) / Data.nbSecondsPerDay;
				case BLOC : 
					time = ((double)(nbPatientForEachState.get(mapentry.getKey()).intValue() / data.getBlocs().size()) * data.getTimeBloc()) / Data.nbSecondsPerDay;
				case PRESCRIPTION : 
					time =((double)(nbPatientForEachState.get(mapentry.getKey()).intValue() / data.getDoctors().size()) * data.getTimePrescription()) / Data.nbSecondsPerDay;
				default:
					break;
			}
			percentage.put((State)mapentry.getKey(), time * 100);
		}
		
		Utils.showMap(percentage);
		
		return percentage;
	}
	
	/**
	 * Get the number of patients by path. 
	 * @param data Data from which are extracted statistics. 
	 * @return A map with a key = the path & a value = the number of patients. 
	 */
	public static Map<Gravity, Integer> getNumberOfPatientByPath(Data data){
		Map<Gravity, Integer> nbPatient = new TreeMap<Gravity, Integer>();
		for(Gravity g : Gravity.values()) {
			nbPatient.put(g, 0);
		}
		for(Patient p : data.getPatientsOver()) {
			nbPatient.put(p.getGravity(), nbPatient.get(p.getGravity()).intValue()+1);
		}
		return nbPatient;
	}
	
	//TODO temps passé journée patient dans urgence

}

