package com.chinasofti.testing.core.enums;


public enum HttpType {
    POST("post"),
    GET("get"),
    DELETE("delete"),
	PUT("put");
	
    private String value;

    HttpType(String value) {
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
