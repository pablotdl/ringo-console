package ar.edu.unicen.ringo.console.ui.support;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Represents a menu structure.
 * @author psaavedra
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Menu {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String styleClass;

    @XmlElementWrapper(name = "items")
    private List<Item> item = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public List<Item> getItems() {
        return item;
    }

    public void setItems(List<Item> items) {
        this.item = items;
    }
}
