package edu.duke.cloudnote.entity;

import java.io.Serializable;


/**
 * wrapper class for return message
 *
 */
public class Response implements Serializable{

	private static final long serialVersionUID = -8670328647557340122L;
	private int status = 1;  	// -1 failure 1 success
	private Object resource;	// data
	private String message;		// message
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Object getResource() {
		return resource;
	}
	public void setResource(Object resource) {
		this.resource = resource;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
