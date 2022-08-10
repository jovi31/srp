package edu.ifes.ci.si.les.srp.services.exceptions;

public class ObjectAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ObjectAlreadyExistsException(String msg) {
		super(msg);
	}
	
	public ObjectAlreadyExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
