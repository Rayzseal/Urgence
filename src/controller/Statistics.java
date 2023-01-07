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
		Map<Gravity, Integer> time = new TreeMap<Gravity, Integer>();
		Map<Gravity, Integer> nbPatient = getNumberOfPatientByPath(data);
		for(Gravity g : Gravity.values()) {
			time.put(g, 0);
		}
		for(Patient p : data.getPatientsOver()) {
			int t = time.get(p.getGravity()).intValue() + p.getGlobalWaitInSeconds();
			time.put(p.getGravity(), t);
		}
		for(Gravity g : Gravity.values()) {
			int t = 0;
			if (nbPatient.get(g) > 0)
				 t = time.get(g).intValue() /nbPatient.get(g);
			time.put(g, t/60);
		}
		return time;
	}
	
	//TODO check if equal to time for a state (if we subtract waiting time for each state)
	/**
	 * Get the number of patients that patients spend in each states. 
	 * @param data Data from which are extracted statistics. 
	 * @return A map with a key = the state & a value = the average time time that a patient spent in a state (in minutes). 
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
				/* Subtraction to know how much time a patient stayed in each state
				   Next state begin time - actual state begin time 					*/
				patientTime = timeValuesForPatient.get(i+1) - timeValuesForPatient.get(i);
				patientState = statesForPatient.get(i+1);
				
				int getTime = average.get(patientState);
				
				//Add values to map
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
			
			// We divide by 60 so we have a time in minutes (it was originally in seconds)
			average.put((State)mapentry.getKey(), time / 60);
		}
		
		//remove unwanted values
		average.remove(State.ARRIVAL);
		average.remove(State.AVAILABLE);
		average.remove(State.OCCUPIED);
		average.remove(State.OUT);
		average.remove(State.WAITING);
		return average;
	}
	
	public static Map<State, Double> getAverageWaitingTimeState(Data data) {
		Map<State, Double> averageWaitTime = new TreeMap<State, Double>();
		Map<State, Integer> nbPatientForEachState = new TreeMap<State, Integer>();
		
		//Initialization
		for (State s : State.values()) {
			averageWaitTime.put(s, 0.0);
			nbPatientForEachState.put(s, 0);
		}
		
		for(Patient p : data.getPatientsOver()) {
			Iterator iterator = p.getListWaitTime().entrySet().iterator();
			List<State> statesForPatient = new ArrayList<State>();
			
			//For each states that the patient went through
			while (iterator.hasNext()) {
				Map.Entry mapentry = (Map.Entry) iterator.next();
				statesForPatient.add((State)mapentry.getKey());
				double value = averageWaitTime.get((State)mapentry.getKey()) + (double)p.getListWaitTime().get((State)mapentry.getKey()).intValue();
				averageWaitTime.put((State)mapentry.getKey(),value);
			}
			
			//All states of a patient values
			for (int i = 0 ; i < statesForPatient.size()-1; i++) {
				int nbPatientForState = nbPatientForEachState.get(statesForPatient.get(i));
				//increment value
				nbPatientForEachState.put(statesForPatient.get(i), nbPatientForState + 1);
			}
		}
	
		Utils.showMap(nbPatientForEachState);
		
		Iterator iterator = averageWaitTime.entrySet().iterator();
		//Get the average value
		while (iterator.hasNext()) {
			Map.Entry mapentry = (Map.Entry) iterator.next();
			double value = averageWaitTime.get((State)mapentry.getKey());
			value = value / nbPatientForEachState.get((State)mapentry.getKey());
			//averageWaitTime.put((State)mapentry.getKey(), value/ 60);
		}
		return averageWaitTime;
	}
 	
	/**
	 * Get the percentage of utilization for each states. 
	 * @param data Data from which are extracted statistics. 
	 * @return A map with a key = the state & a value = percentage of utilization. 
	 */
	@SuppressWarnings("rawtypes")
	public static Map<State, Double> getPercentageUtilizationStates(Data data) {
		Map<State, Double> percentage = new TreeMap<State, Double>();
		Map<State, Integer> nbPatientForEachState = new TreeMap<State, Integer>();
		List<Integer> bedroomValue = new ArrayList<Integer>();
		List<Integer> outValue = new ArrayList<Integer>();
		double averageForOutBedroom = 0.0;
		
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
				
				if (p.getGravity()!=Gravity.A && statesForPatient.get(i) == State.BEDROOM) {
					bedroomValue.add(p.getListState().get(State.BEDROOM));
					outValue.add(p.getListState().get(State.OUT));
				}
				
				else //increment value
					nbPatientForEachState.put(statesForPatient.get(i), nbPatientForState + 1);
			}
		}
		
		//Get the percentage of utilization of all bedrooms
		int averageValueBedroom = 0;
		int averageValueOut = 0;
		for (int i = 0; i < bedroomValue.size(); i++) {
			averageValueBedroom += bedroomValue.get(i);
			averageValueOut += outValue.get(i);
		}
		averageForOutBedroom = (averageValueOut - averageValueBedroom) / bedroomValue.size();
		averageForOutBedroom = (double)(averageForOutBedroom / Data.nbSecondsPerDay) * 100;

		
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
					time = ((double)(nbPatientForEachState.get(mapentry.getKey()).intValue() / data.getDoctors().size()) * data.getTimePrescription()) / Data.nbSecondsPerDay;
				default:
					break;
			}
			if ((State)mapentry.getKey()==State.BEDROOM) 
				percentage.put((State)mapentry.getKey(), averageForOutBedroom);
			else
				percentage.put((State)mapentry.getKey(), time * 100);
		}
		
		Utils.showMap(nbPatientForEachState);
		
		//remove unwanted values
		percentage.remove(State.ARRIVAL);
		percentage.remove(State.AVAILABLE);
		percentage.remove(State.OCCUPIED);
		percentage.remove(State.OUT);
		percentage.remove(State.WAITING);
		
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
	
	//TODO TO TEST
	/**
	 * Get the total time that a patient spent in the hospital.
	 * @param data Data from which are extracted statistics. 
	 * @return A map with a key = the patient & a value = total time the patient spent in the hospital. 
	 */
	public static Map<Patient, Integer> getTimeSpentInEmergency(Data data) {
		Map<Patient, Integer> timeInEmergency = new HashMap<Patient, Integer>();
		for(Patient p : data.getPatientsOver()) {
			int time = p.getListState().get(State.OUT) - p.getArrivalDate();
			timeInEmergency.put(p,time);
		}
		return timeInEmergency;
	}
	
	//TODO TO TEST
	/**
	 * Get the average of time that patients spent in the hospital.
	 * @param data Data from which are extracted statistics. 
	 * @return The average time that patients spent in emergency. 
	 */
	public static double getAverageTimeSpentInEmergency(Data data) {
		double timeInEmergency = 0;
		for(Patient p : data.getPatientsOver()) {
			timeInEmergency += p.getListState().get(State.OUT) - p.getArrivalDate();
		}
		timeInEmergency = timeInEmergency / data.getNbOfPatients();
		return timeInEmergency;
	}
	
	//TODO TO TEST
	/**
	 * Get the average of time that patients spent in the hospital depending of their gravity.
	 * @param data Data from which are extracted statistics. 
	 * @return A map with a key = the gravity & a value = the average time spent in the hospital by gravity. 
	 */
	public static Map<Gravity, Double> getAverageTimeSpentInEmergencyByGravity(Data data) {
		Map<Gravity, Integer> nbPatientByGravity = new TreeMap<Gravity, Integer>();
		Map<Gravity, Double> averageTimeInEmergency = new TreeMap<Gravity, Double>();
		
		for (Gravity g : Gravity.values()) {
			averageTimeInEmergency.put(g, 0.0);
			nbPatientByGravity.put(g, 0);
		}
		
		for(Patient p : data.getPatientsOver()) {
			nbPatientByGravity.put(p.getGravity(), nbPatientByGravity.get(p.getGravity()).intValue()+1);
			double time = p.getListState().get(State.OUT) - p.getArrivalDate();
			averageTimeInEmergency.put(p.getGravity(), time);
		}
		
		for(Gravity g : Gravity.values()) {
			double t = 0;
			if (nbPatientByGravity.get(g) > 0)
				 t = averageTimeInEmergency.get(g).intValue() /nbPatientByGravity.get(g);
			averageTimeInEmergency.put(g, t/60);
		}
		
		return averageTimeInEmergency;
	}
	
	/**
	 * Get the percentage of patients by gravity.
	 * @param data Data from which are extracted statistics. 
	 * @return A map with a key = the gravity & a value = the percentage of patients by gravity. 
	 */
	public static Map<Gravity, Double> getPercentagePatientByGravity(Data data) {
		Map<Gravity, Double> percentage = new TreeMap<Gravity, Double>();
		Map<Gravity, Integer> nbPatient = new TreeMap<Gravity, Integer>();

		for(Gravity g : Gravity.values()) {
			nbPatient.put(g, 0);
		}
		
		for(Patient p : data.getPatientsOver()) {
			nbPatient.put(p.getGravity(), nbPatient.get(p.getGravity()).intValue()+1);
		}

		for(Gravity g : Gravity.values()) {
			double valuePercentage = ((double)nbPatient.get(g).intValue() / data.getNbOfPatients()) * 100;
			percentage.put(g, valuePercentage);
		}
		return percentage;
	}

}

