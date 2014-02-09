package ar.edu.unicen.ringo.console.model;

import java.util.Date;

public class Generate {
	
	private int invocations;
	private String method;
    private Date timestamp = new Date();
    
    
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
    
    public int getInvocations() {
		return invocations;
	}
	public void setInvocations(int invocations) {
		this.invocations = invocations;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}    
}
