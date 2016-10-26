package com.functiongrapher.event;

import java.awt.EventQueue;
import java.util.ArrayList;

import com.functiongrapher.event.events.AbstractEvent;
import com.functiongrapher.event.listeners.AbstractListener;

public class ProgramEventQueue extends EventQueue {

	private ArrayList<AbstractListener> listeners = new ArrayList<AbstractListener>();
	
	public void addListener(AbstractListener l) {
		listeners.add(l);
	}
	
	public void dispatchEvent(AbstractEvent e) {
		for (AbstractListener l : listeners) {
			
		}
	}
	
}
