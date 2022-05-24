package com.github.eataborda.api.service;

import com.github.eataborda.api.enums.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
    private final String user = getEnvironmentVariable("USER");
    private final String password = getEnvironmentVariable("PASSWORD");
    private final String authorization = getEnvironmentVariable("AUTHORIZATION");

    public String getSessionToken() {
        RequestSpecification request = SerenityRest.given();
        Map<String, String> requestBody = Map.of(BodyNames.USERNAME.getValue(), user, BodyNames.PASSWORD.getValue(), password);
        request.header(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.body(requestBody);
        Response response = request.post(URLs.AUTH.getValue());
        return response.jsonPath().getString("token");
    }

    public Response getAllBookingIds() {
        RequestSpecification request = SerenityRest.given();
        request.headers(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        return request.get(URLs.BASE.getValue());
    }

    public Response getBookingById(int bookingId) {
        RequestSpecification request = SerenityRest.given();
        request.headers(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.pathParams("id", bookingId);
        return request.get(URLs.BASE.getValue() + "/{id}");
    }

    public Response postCreateBooking() {
        RequestSpecification request = SerenityRest.given();
        request.headers(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.body(getRequestBody());
        return request.post(URLs.BASE.getValue());
    }

    public Map<String, Object> getRequestBody() {
        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put(BodyNames.CHECKIN.getValue(), "2022-05-02");
        bookingDates.put(BodyNames.CHECKOUT.getValue(), "2022-06-02");
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put(BodyNames.FIRST_NAME.getValue(), "Edwin");
        reqBody.put(BodyNames.LAST_NAME.getValue(), "Hubble");
        reqBody.put(BodyNames.TOTAL_PRICE.getValue(), 170);
        reqBody.put(BodyNames.DEPOSIT_PAID.getValue(), true);
        reqBody.put(BodyNames.ADDITIONAL_NEEDS.getValue(), "Learning and sharing knowledge!");
        reqBody.put(BodyNames.BOOKING_DATES.getValue(), bookingDates);

        return reqBody;
    }

    public Response putUpdateBooking(int bookingId, String sessionToken) {
        Map<String, String> authenticationHeader = getAuthenticationHeader(sessionToken);
        RequestSpecification request = SerenityRest.given();
        request.headers(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue(), authenticationHeader.get("key"), authenticationHeader.get("value"));
        request.body(getUpdatedRequestBody());
        request.pathParams("id", bookingId);
        return request.put(URLs.BASE.getValue() + "/{id}");
    }

    public Map<String, Object> getUpdatedRequestBody() {
        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put(BodyNames.CHECKIN.getValue(), "2022-05-04");
        bookingDates.put(BodyNames.CHECKOUT.getValue(), "2022-06-05");
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put(BodyNames.FIRST_NAME.getValue(), "Albert");
        reqBody.put(BodyNames.LAST_NAME.getValue(), "Einstein");
        reqBody.put(BodyNames.TOTAL_PRICE.getValue(), 190);
        reqBody.put(BodyNames.DEPOSIT_PAID.getValue(), true);
        reqBody.put(BodyNames.ADDITIONAL_NEEDS.getValue(), "Understand the nature of things!");
        reqBody.put(BodyNames.BOOKING_DATES.getValue(), bookingDates);

        return reqBody;
    }

    public Map<String, String> getAuthenticationHeader(String sessionToken) {
        Map<String, String> authenticationHeader = new HashMap<>();
        if (sessionToken.length() != 0) {
            authenticationHeader.put("key", HeadersNames.COOKIE.getValue());
            authenticationHeader.put("value", "token=" + sessionToken);
        } else {
            authenticationHeader.put("key", HeadersNames.AUTHORIZATION.getValue());
            authenticationHeader.put("value", "Basic " + authorization);
        }
        return authenticationHeader;
    }

    public Response putUpdateBookingAuthenticationHeader(int bookingId, String sessionToken) {
        RequestSpecification request = SerenityRest.given();
        request.headers(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.body(getUpdatedRequestBody());
        request.pathParams("id", bookingId);
        return request.put(URLs.BASE.getValue() + "/{id}");
    }

    public Response patchPartialUpdateBooking(int bookingId, String sessionToken) {
        Map<String, String> authenticationHeader = getAuthenticationHeader(sessionToken);
        RequestSpecification request = SerenityRest.given();
        request.headers(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue(), authenticationHeader.get("key"), authenticationHeader.get("value"));
        request.body(getPartialyUpdatedRequestBody());
        request.pathParams("id", bookingId);
        return request.patch(URLs.BASE.getValue() + "/{id}");
    }

    public Map<String, Object> getPartialyUpdatedRequestBody() {
        Map<String, Object> bookingValuesToUpdate = new HashMap<>();
        bookingValuesToUpdate.put(BodyNames.FIRST_NAME.getValue(), "Nikola");
        bookingValuesToUpdate.put(BodyNames.LAST_NAME.getValue(), "Tesla");
        return bookingValuesToUpdate;
    }

    public Response deleteBooking(int bookingId, String sessionToken) {
        Map<String, String> authenticationHeader = getAuthenticationHeader(sessionToken);
        RequestSpecification request = SerenityRest.given();
        request.headers(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue(), authenticationHeader.get("key"), authenticationHeader.get("value"));
        request.pathParams("id", bookingId);
        return request.delete(URLs.BASE.getValue() + "/{id}");
    }

    public Response getHealthCheck() {
        RequestSpecification request = SerenityRest.given();
        request.headers(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        return request.get(URLs.PING.getValue());
    }

    public Response postCreateBookingWithMalformedBody(int expectedStatusCode) {
        RequestSpecification request = SerenityRest.given();
        request.headers(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        if (expectedStatusCode == StatusCode.SC_400.getValue()) {
            request.body(getMalformedRequestBodyStatusCode400());
        } else if (expectedStatusCode == StatusCode.SC_500.getValue()) {
            request.body(getMalformedRequestBodyStatusCode500());
        }
        return request.post(URLs.BASE.getValue());
    }

    public Map<String, Object> getMalformedRequestBodyStatusCode500() {
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put(BodyNames.FIRST_NAME.getValue(), "George");
        reqBody.put(BodyNames.LAST_NAME.getValue(), "Fisher");
        reqBody.put(BodyNames.TOTAL_PRICE.getValue(), 190);
        reqBody.put(BodyNames.DEPOSIT_PAID.getValue(), true);
        reqBody.put(BodyNames.ADDITIONAL_NEEDS.getValue(), "Fish all day!");
        return reqBody;
    }

    public String getMalformedRequestBodyStatusCode400() {
        return new String("{\"firstname\":\"George\",\"lastname\":\"Fisher\",\"totalprice\":420,\"depositpaid\":true,\"bookingdates\":{\"checkin\":\"2022-04-24\",\"checkout\":\"2023-04-24\"},\"additionalneeds\":%}");
    }

    private String getEnvironmentVariable(String key) {
        return System.getenv(key);
    }
}
