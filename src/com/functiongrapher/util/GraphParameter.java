package com.functiongrapher.util;

import java.util.ArrayList;

public class GraphParameter<E> {

	private final GraphProperty type;
	private final E data;
	
	public GraphParameter(GraphProperty type, E data) {
		this.type = type;
		this.data = data;
	}

	public GraphProperty getType() {
		return type;
	}

	public E getData() {
		return data;
	}
	
	public static GraphProperty[] getPropertiesAsArray(ArrayList<GraphParameter<?>> list) {
		GraphProperty[] array = new GraphProperty[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i).getType();
		}
		return array;
	}
	
	public static Object[] getDataAsArray(ArrayList<GraphParameter<?>> list) {
		Object[] array = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i).getData();
		}
		return array;
	}
	
}
