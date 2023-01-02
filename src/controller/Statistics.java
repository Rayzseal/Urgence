package controller;

import java.util.HashMap;
import java.util.Map;

import model.Gravity;
import model.Patient;
import model.Ressource;
import utils.Data;
import utils.Utils;

public class Statistics {
	
	public static void statistics(Data data) {
		
		System.out.print("Average global waiting time : ");
		System.out.println(Utils.timeIntToString(Statistics.getAverageWaitingTime(data)));
	}
	
	public static int getAverageWaitingTime(Data data) {
		int time = 0;
		for(Patient p : data.getPatientsOver()) {
			time+=p.getGlobalWaitInSeconds();
		}
		time = time/data.getPatientsOver().size();
		return time;
	}
	
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
	public static Map<Ressource, Integer> getAverageWaitingTimeByActivity(Data data){
		Map<Ressource, Integer> average = new HashMap<Ressource, Integer>();
		//TODO
		return average;
	}
	
	public static Map<Gravity, Integer> getNumberOfPatientByPath(Data data){
		HashMap<Gravity, Integer> nbPatient = new HashMap<Gravity, Integer>();
		for(Gravity g : Gravity.values()) {
			nbPatient.put(g, 0);
		}
		for(Patient p : data.getPatientsOver()) {
			nbPatient.put(p.getGravity(), nbPatient.get(p.getGravity()).intValue()+1);
		}
		return nbPatient;
	}

}

