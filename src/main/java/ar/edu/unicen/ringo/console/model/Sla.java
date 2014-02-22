package ar.edu.unicen.ringo.console.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Represents an SLA.
 * @author psaavedra
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Sla implements Identificable {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement
    private boolean enabled = false;
    @XmlElement
    private String color;    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }    
    public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	/* (non-Javadoc)
     * @see ar.edu.unicen.ringo.console.model.Identificable#getId()
     */
    @Override
    public String getId() {
        return id;
    }
    /* (non-Javadoc)
     * @see ar.edu.unicen.ringo.console.model.Identificable#setId(java.lang.String)
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Sla [name=").append(name).append(", description=")
                .append(description).append(", enabled=").append(enabled).append(", color=")
                .append(color).append("]");
        return builder.toString();
    }

    public void loadFrom(Sla other) {
        id = other.id;
        name = other.name;
        description = other.description;
        enabled = other.enabled;
        color= other.color;
    }

}
