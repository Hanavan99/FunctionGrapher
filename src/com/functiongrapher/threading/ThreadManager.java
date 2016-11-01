package com.functiongrapher.threading;

import java.util.ArrayList;

public class ThreadManager {

	private static ArrayList<Thread> threads = new ArrayList<Thread>();
	
	public static void createAndStartThread(Runnable r) {
		Thread t = new Thread(r);
		t.start();
		threads.add(t);
	}
	
	public static void stopThreads() {
		for (Thread t : threads) {
			t.interrupt();
		}
	}
	
}
