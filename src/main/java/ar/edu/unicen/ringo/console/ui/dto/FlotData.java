package ar.edu.unicen.ringo.console.ui.dto;

import java.util.List;


public class FlotData {

    private String label;
    private String color;    
    private List<?> data;
    
    public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	   
}
