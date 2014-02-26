package ar.edu.unicen.ringo.console.ui.dto;



public class FlotData<T> {

    private String label;
    private String color;    
    private T data;
    
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
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	   
}
