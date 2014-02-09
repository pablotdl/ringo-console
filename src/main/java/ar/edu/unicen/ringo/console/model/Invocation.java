package ar.edu.unicen.ringo.console.model;

import java.util.Date;

public class Invocation implements Identificable {

    private String id;
    private long execution_time;
    private String method;
    private String node;
    private String sla;
    private Date timestamp = new Date();

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }


    public void setSla(String sla) {
        this.sla = sla;
    }

    public long getExecution_time() {
		return execution_time;
	}

	public void setExecution_time(long execution_time) {
		this.execution_time = execution_time;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getSla() {
        return this.sla;
    }

    public void loadFrom(Invocation invocation) {
        this.id = invocation.id;
        this.sla = invocation.sla;
        this.node = invocation.node;
        this.method = invocation.method;
        this.execution_time = invocation.execution_time;
        this.timestamp = invocation.timestamp;        
    }

}
