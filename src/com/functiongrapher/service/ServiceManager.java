package com.functiongrapher.service;

public class ServiceManager {

	private static IPropertyService service;
	
	public static void init() {
		service = new PropertyServiceImpl();
	}
	
	public static IPropertyService getService() {
		return service;
	}
	
}
