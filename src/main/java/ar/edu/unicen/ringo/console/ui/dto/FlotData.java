package ar.edu.unicen.ringo.console.ui.dto;

import java.util.List;


public class FlotData {

    private String label;
    private List<?> data;
    
    public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	   
}
