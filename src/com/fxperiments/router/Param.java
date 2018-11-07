package com.fxperiments.router;


public class Param {
	private String name;

	private Param(String name) {
		this.name = name;
	}
	
	public static Param name(String name){
		return new Param(name);
	}
	
	public InjectableParam inject(Object obj){
		return new InjectableParam(name, obj);
	}
}
