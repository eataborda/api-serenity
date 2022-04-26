package com.github.eataborda.api.service;

import com.github.eataborda.api.enums.HeaderValues;
import com.github.eataborda.api.enums.HeadersNames;
import com.github.eataborda.api.enums.URLs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

import java.util.Map;

public class ServiceManager {
    private final String user = "admin";
    private final String password = "password123";

    public String getSessionToken() {
        RequestSpecification request = SerenityRest.given();
        Map<String, String> requestBody = Map.of("user", user, "password", password);
        request.header(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.body(requestBody);
        Response response = request.post(URLs.AUTH.getValue());
        return response.jsonPath().getString("token");
    }

    public Response getAllBookingIds(String token) {
        RequestSpecification request = SerenityRest.given();
        request.header(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        return request.get(URLs.BASE.getValue());
    }
}
