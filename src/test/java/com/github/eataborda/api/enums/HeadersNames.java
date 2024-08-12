package com.github.eataborda.api.enums;

public enum HeadersNames {
    AUTHORIZATION("Authorization"),
    ACCEPT("Accept"),
    ACCEPT_ENCODING("Accept-Encoding"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    SERVER("Server"),
    NEL("Nel"),
    CONNECTION("Connection"),
    X_POWERED_BY("X-Powered-By"),
    ETAG("Etag"),
    DATE("Date"),
    VIA("Via"),
    COOKIE("Cookie");

    private final String value;

    HeadersNames(String value){this.value = value;}

    public String getValue(){return value;}
}
