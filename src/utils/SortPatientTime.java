package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Patient;
import model.State;

/**
 *
 * Class to sort patients times, compares the time of patients.
 *
 */
public class SortPatientTime implements Comparator<Patient>{

	@Override
	public int compare(Patient o1, Patient o2) {
		List<Entry<State,Integer>> o1EntryList = new ArrayList<>(o1.getListState().entrySet());
		Entry<State, Integer> o1lastEntry = o1EntryList.get(o1EntryList.size()-1);
		int o1lastTime = o1.getListState().get(o1lastEntry.getKey());

		List<Entry<State,Integer>> o2EntryList = new ArrayList<>(o2.getListState().entrySet());
		Entry<State, Integer> o2lastEntry = o2EntryList.get(o2EntryList.size()-1);
		int o2lastTime = o2.getListState().get(o2lastEntry.getKey());

		return o1lastTime - o2lastTime;
	}

}
