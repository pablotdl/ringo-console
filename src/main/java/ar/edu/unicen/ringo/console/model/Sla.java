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
public class Sla {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement
    private boolean enabled = true;
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
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Sla [name=").append(name).append(", description=")
                .append(description).append(", enabled=").append(enabled).append("]");
        return builder.toString();
    }

}
