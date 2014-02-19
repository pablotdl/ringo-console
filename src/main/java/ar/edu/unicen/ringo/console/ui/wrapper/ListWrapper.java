package ar.edu.unicen.ringo.console.ui.wrapper;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class that contains a list in the format that datatables.net requires it.
 * @author psaavedra
 */
@XmlRootElement
public class ListWrapper {

    private List<?> aaData;

    public ListWrapper(){}

    public ListWrapper(List<?> aaData) {
        this.aaData = aaData;
    }

    public List<?> getAaData() {
        return aaData;
    }

    public void setAaData(List<?> aaData) {
        this.aaData = aaData;
    }

    
}
