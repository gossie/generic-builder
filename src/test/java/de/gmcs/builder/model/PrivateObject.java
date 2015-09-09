package de.gmcs.builder.model;

public class PrivateObject {

    private String attribute;

    private PrivateObject() {
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
