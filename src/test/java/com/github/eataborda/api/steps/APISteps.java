package com.github.eataborda.api.steps;

import com.github.eataborda.api.common.Logger;
import com.github.eataborda.api.enums.HeaderValues;
import com.github.eataborda.api.enums.HeadersNames;
import com.github.eataborda.api.service.ServiceManager;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;

import java.util.*;

public class APISteps {
    private ServiceManager manager = new ServiceManager();
    private SoftAssertions assertion;

    @Steps
    Logger l;

    @Step("Get session token")
    public String getSessionToken() {
        return manager.getSessionToken();
    }

    @Step("Get all booking Ids")
    public Response getAllBookingIds(String token) {
        return manager.getAllBookingIds(token);
    }

    @Step("Validate expected status code: {0}")
    public void validateStatusCode(int statusCode, Response response) {
        Assert.assertEquals("Not expected status code:", statusCode, response.getStatusCode());
    }

    @Step("Validate response body content is not null and not empty")
    public void validateResponseBodyIsNotNullAndNotEmpty(Response response) {
        Assert.assertNotNull("Body content is null:", response.getBody());
        Assert.assertTrue(response.jsonPath().getList("$").size() >= 1);
    }

    @Step("Validate expected response body fields")
    public void validateGetAllBookingIdsResponseBodyHasExpectedFields(Response response) {
        assertion = new SoftAssertions();
        List<Map<String, String>> bookingIdList = response.jsonPath().getList("$");

        for (Map<String, String> bookingIdElement : bookingIdList) {
            String bookingId = String.valueOf(bookingIdElement.get("bookingid"));
            assertion.assertThat(bookingId).as("BookingId field is null").isNotNull();
            assertion.assertThat(bookingId.matches("[0-9.]+")).isTrue();
            l.log("bookingid = " + bookingId);
        }
        assertion.assertAll();
    }

    @Step("Validate expeted response headers")
    public void validateGetAllBookingIdsResponseHeaders(Response response) {
        assertion = new SoftAssertions();
        Headers headers = response.getHeaders();
        for (Header header : headers) {
            String fieldName = header.getName();
            String fieldValue = header.getValue();
            l.log(fieldName+" "+fieldValue);
        }
        //assertion.assertAll();
    }
}
