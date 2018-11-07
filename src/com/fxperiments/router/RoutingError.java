package com.fxperiments.router;


public class RoutingError extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private final String message;
	
	public RoutingError(String message) {
		this.message = message;
	}


	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return message;
	}
}
