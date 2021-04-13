package com.chinasofti.testing.core.enums;


public enum ContentType {
    JSON("application/json"),
    XML("application/xml");
	
    private String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
