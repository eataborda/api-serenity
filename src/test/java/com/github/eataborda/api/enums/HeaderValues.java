package com.github.eataborda.api.enums;

public enum HeaderValues {
    APPLICATION_JSON("application/json"),
    CHARSET_UTF_8("charset=utf-8"),
    COWBOY("Cowboy"),
    APPLICATION_JSON_CHARSET_UTF_8("application/json; charset=utf-8"),
    TEXT_PLAIN_CHARSET_UTF_8("text/plain; charset=utf-8"),
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
