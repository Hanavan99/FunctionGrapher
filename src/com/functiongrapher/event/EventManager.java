package com.functiongrapher.event;

public class EventManager {

	private static ProgramEventQueue queue;

	public static void init() {
		queue = new ProgramEventQueue();
	}

	public static ProgramEventQueue getQueue() {
		return queue;
	}

}
