package com.github.eataborda.api.enums;

public enum HeaderValues {
    APPLICATION_JSON("application/json"),
    COWBOY("Cowboy"),
    CHARSET_UTF_8("charset=utf-8"),
    KEEP_ALIVE("keep-alive"),
    EXPRESS("Express"),
    VEGUR_1_1("1.1 vegur");

    private final String value;

    HeaderValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
