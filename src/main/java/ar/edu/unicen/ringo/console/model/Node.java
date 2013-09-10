package ar.edu.unicen.ringo.console.model;

/**
 * Represents a node in an SLA.
 * @author psaavedra
 */
public class Node implements Identificable {

    private String id;
    private String name;
    private String sla;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSla(String sla) {
        this.sla = sla;
    }

    public String getSla() {
        return this.sla;
    }

    public void loadFrom(Node node) {
        this.id = node.id;
        this.name = node.name;
        this.sla = node.sla;
    }

}
