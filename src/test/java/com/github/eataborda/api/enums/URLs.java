package com.github.eataborda.api.enums;

public enum URLs {
    AUTH("https://restful-booker.herokuapp.com/auth"),
    BASE("https://restful-booker.herokuapp.com/booking");

    private final String value;

    URLs(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
