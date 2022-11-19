import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import back.Scheduler;
import back.State;

public class Main {

	public static void main(String[] args) {
		Scheduler s = new Scheduler();
		s.run();
		
		AtomicInteger i = new AtomicInteger();
		i.set(0);
		i.addAndGet(-1);
		System.out.println(i.get());

	}

}
