package hud;

import controller.Scheduler;
import model.Patient;

public class MainApp {

	public static void main(String[] args) {
		// testThread();
		// testC();

		Scheduler s = new Scheduler(500);
		s.run();
		for (Patient p : s.getData().getPatientsOver()) {
			if (p.getListWaitTime().size() >= 1) {
				System.out.println(p);

			}

		}

		// Statistics.statistics(s.getData());

	}

	public static void testC() {
		Scheduler s = new Scheduler(1);
		s.run();
		for (Patient p : s.getData().getPatientsOver()) {
			System.out.println(p);
		}

		System.out.println(s.getData().getPatientsOver().size());
	}

	public static void testThread() {
		Thread Thread_B = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (this) {
					int i = 0;
					while (i < 100)
						i++;
					// libérer le thread A
					notify();
				}
			}
		});
		Thread_B.start();

		synchronized (Thread_B) {
			try {
				System.out.println("Thread A est bloqué - En attente de thread B" + "qu'il termine");
				// mettre en attente le thread A
				Thread_B.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("le thread B a terminé - Le thread A est relaché");
		}

	}

}
