package de.gmcs.builder.model;

public class PrivateObject {

    private String attribute;

    private PrivateObject() {
    }

    public static PrivateObject getInstance() {
        return new PrivateObject();
    }

    public static PrivateObject getInstance(String attribute) {
        PrivateObject object = new PrivateObject();
        object.setAttribute(attribute);
        return object;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
