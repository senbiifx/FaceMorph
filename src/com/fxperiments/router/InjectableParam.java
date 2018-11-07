package com.fxperiments.router;


public class InjectableParam {
	private String name;
	private Object object;
	public InjectableParam(String name, Object object) {
		this.name = name;
		this.object = object;
	}
	public String getName() {
		return name;
	}
	public Object getObject() {
		return object;
	}
	
}
