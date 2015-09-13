package de.gmcs.builder.model;

import java.util.HashMap;
import java.util.Map;

public class DomainObject {

    private String attribute;
    private String unsettableAttribute;
    private int performCounter;
    private Map<String, Integer> properties = new HashMap<>();

    public DomainObject() {
        this("");
    }

    public DomainObject(String attribute) {
        this.attribute = attribute;
        this.performCounter = 0;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setProperty(String key, Integer value) {
        properties.put(key, value);
    }

    public Integer getProperty(String key) {
        return properties.get(key);
    }

    public void perform() {
        ++performCounter;
    }

    public int getPerformCounter() {
        return performCounter;
    }

    public String getUnsettableAttribute() {
        return unsettableAttribute;
    }
}
