package com.chinasofti.testing.core.enums;

public enum ExpectResultType {

	STATUS("status"),
    CONTENT("content");
	
    private String value;

    ExpectResultType(String value) {
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
