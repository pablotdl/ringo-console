package ar.edu.unicen.ringo.console.ui.support;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A menu item.
 * @author psaavedra
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String label;

    @XmlAttribute
    private String styleClass;

    @XmlAttribute
    private String href;
    

    @XmlElementWrapper(name = "submenu")
    private List<Item> item = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> items) {
        this.item = items;
    }

    public boolean isActive(String uri) {
        if (this.item.isEmpty()) {
            if ("/".equals(href) && "/".equals(uri)) {
                return true;
            }
            return !"/".equals(href) && href != null && uri.startsWith(href) ;
        }
        for (Item i : item) {
            if (i.isActive(uri)) {
                return true;
            }
        }
        return false;
    }

}
