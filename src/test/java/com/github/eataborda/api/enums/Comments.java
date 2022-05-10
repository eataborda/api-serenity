package com.github.eataborda.api.enums;

public enum Comments {
    SUCCESS_POST("Successful post request should return 201 according to the theory, please consult the following source: https://developer.mozilla.org/es/docs/Web/HTTP/Status/201"),
    SUCCESS_HEALTH_CHECK("Successful get request without payload should return 204 according to the theory, please consult the following source: https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/204"),
    SUCCESS_DELETE("Successful delete request should return 204 according to the theory, please consult the following source: https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/DELETE");

    private final String value;

    Comments(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
