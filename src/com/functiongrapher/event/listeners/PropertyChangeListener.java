package com.functiongrapher.event.listeners;

import com.functiongrapher.event.events.PropertyChangeEvent;

public interface PropertyChangeListener extends AbstractListener {

	public void propertyChanged(PropertyChangeEvent e);
	
}
