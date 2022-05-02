package com.github.eataborda.api.service;

import com.github.eataborda.api.enums.BodyNames;
import com.github.eataborda.api.enums.HeaderValues;
import com.github.eataborda.api.enums.HeadersNames;
import com.github.eataborda.api.enums.URLs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;
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

    public Response getAllBookingIds() {
        RequestSpecification request = SerenityRest.given();
        request.headers(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        return request.get(URLs.BASE.getValue());
    }

    public Response getBookingInformationById(int bookingId) {
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
        reqBody.put(BodyNames.ADDITIONAL_NEEDS.getValue(), "Adventure!");
        reqBody.put(BodyNames.BOOKING_DATES.getValue(), bookingDates);

        return reqBody;
    }

    public Response putUpdateBooking(int bookingId) {
        RequestSpecification request = SerenityRest.given();
        request.headers(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue(), HeadersNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue(),HeadersNames.AUTHORIZATION.getValue(),"Basic YWRtaW46cGFzc3dvcmQxMjM=");
        request.body(getUpdatedRequestBody());
        request.pathParams("id", bookingId);
        return request.put(URLs.BASE.getValue()+ "/{id}");
    }

    public Map<String, Object> getUpdatedRequestBody() {
        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put(BodyNames.CHECKIN.getValue(), "2022-05-04");
        bookingDates.put(BodyNames.CHECKOUT.getValue(), "2022-06-05");
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put(BodyNames.FIRST_NAME.getValue(), "Edward");
        reqBody.put(BodyNames.LAST_NAME.getValue(), "Hunter");
        reqBody.put(BodyNames.TOTAL_PRICE.getValue(), 190);
        reqBody.put(BodyNames.DEPOSIT_PAID.getValue(), true);
        reqBody.put(BodyNames.ADDITIONAL_NEEDS.getValue(), "Adventure Time!");
        reqBody.put(BodyNames.BOOKING_DATES.getValue(), bookingDates);

        return reqBody;
    }
}
