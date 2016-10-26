package com.functiongrapher.event.events;

import com.functiongrapher.util.GraphProperty;

public class PropertyChangeEvent extends AbstractEvent {

	private final GraphProperty p;
	
	public PropertyChangeEvent(GraphProperty p) {
		this.p = p;
	}
	
	public GraphProperty getProperty() {
		return p;
	}

}
