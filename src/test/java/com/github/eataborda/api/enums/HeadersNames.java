package com.github.eataborda.api.enums;

public enum HeadersNames {
    AUTHORIZATION("Authorization"),
    ACCEPT("Accept"),
    ACCEPT_ENCODING("Accept-Encoding"),
    CONTENT_TYPE("Content-Type"),
    SERVER("Server"),
    CONNECTION("Connection"),
    X_POWERED_BY("X-Powered-By"),
    DATE("Date"),
    VIA("Via");

    private final String value;

    HeadersNames(String value){this.value = value;}

    public String getValue(){return value;}
}
