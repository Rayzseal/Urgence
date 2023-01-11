package events;

import model.Patient;
import utils.Data;

/**
 * Event of the start in the path C, it inherits the class Event
 */
public class EvPathC extends Event implements Runnable{
	/**
	 * constructor of EvPathC
	 * @param d Data
	 * @param p Patient
	 */
	public EvPathC(Data d, Patient p) {
		super(d,p);
	}
	/**
	 * runnable method, add the patient in the list waiting of waitListPathC
	 * then start analysis and scanner event
	 */
	@Override
	public void run() {
		new EvAnalysis(getData(), null).run();

		new EvScanner(getData(), null).run();
	}

}
