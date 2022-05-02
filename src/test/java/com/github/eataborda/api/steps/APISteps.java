package com.github.eataborda.api.steps;

import com.github.eataborda.api.common.Logger;
import com.github.eataborda.api.enums.HeaderValues;
import com.github.eataborda.api.enums.HeadersNames;
import com.github.eataborda.api.enums.BodyNames;
import com.github.eataborda.api.service.ServiceManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Response getAllBookingIds() {
        return manager.getAllBookingIds();
    }

    public int getARandomExistingBookingId(Response response) {
        List<Map<String, Integer>> bookingIdList = response.jsonPath().getList("$");
        List<Integer> existingBookingIds = new ArrayList<>();
        for (Map<String, Integer> bookingIdElement : bookingIdList) {
            existingBookingIds.add(bookingIdElement.get("bookingid"));
        }
        return existingBookingIds.get(new Random().nextInt(existingBookingIds.size() - 1));
    }

    @Step("Validate expected status code: {0}")
    public void validateStatusCode(int statusCode, Response response) {
        Assert.assertEquals("Not expected status code:", statusCode, response.getStatusCode());
    }

    @Step("Validate response body content is not null and not empty")
    public void validateResponseBodyIsNotNullAndNotEmpty(Response response) {
        var body = response.getBody();
        int bodyLength = body.asString().length();
        assertion = new SoftAssertions();
        assertion.assertThat(body).as("Response body is null").isNotNull();
        assertion.assertThat(bodyLength > 2).as("Response body is empty, length = " + bodyLength).isTrue();
        assertion.assertAll();
    }

    @Step("Validate response header content is not null and not empty")
    public void validateResponseHeadersAreNotNullAndNotEmpty(Response response) {
        Headers headers = response.getHeaders();
        assertion = new SoftAssertions();
        assertion.assertThat(headers).as("Headers are null").isNotNull();
        assertion.assertThat(headers.asList().toString()).as("Headers are empty").isNotEmpty();
        assertion.assertAll();
    }

    @Step("Validate expected response body fields")
    public void validateGetAllBookingIdListResponseBodyHasExpectedFields(Response response) {
        assertion = new SoftAssertions();
        List<Map<String, Integer>> bookingIdList = response.jsonPath().getList("$");

        for (Map<String, Integer> bookingIdElement : bookingIdList) {
            validateResponseBodyBookingIdField(String.valueOf(bookingIdElement.get("bookingid")));
        }
        assertion.assertAll();
    }

    @Step("Validate field value: bookingid = {0} is not null and only has digits")
    public void validateResponseBodyBookingIdField(String bookingId) {
        assertion.assertThat(bookingId).as("BookingId field is null").isNotNull();
        assertion.assertThat(bookingId.matches("[0-9.]+")).isTrue();
    }

    @Step("Validate expected response body fields")
    public void validateGetBookingInformationByIdResponseBodyHasExpectedFields(Response response) {
        assertion = new SoftAssertions();
        validateResponseBodyExpectedFieldIsNotNullAndEmpty("", BodyNames.FIRST_NAME.getValue(), response);
        validateResponseBodyExpectedFieldIsNotNullAndEmpty("", BodyNames.LAST_NAME.getValue(), response);
        validateResponseBodyExpectedFieldValueInteger("", BodyNames.TOTAL_PRICE.getValue(), response);
        validateResponseBodyExpectedFieldValueBoolean("", BodyNames.DEPOSIT_PAID.getValue(), response);
        validateResponseBodyExpectedFieldValueDate(BodyNames.BOOKING_DATES.getValue() + ".", BodyNames.CHECKIN.getValue(), response);
        validateResponseBodyExpectedFieldValueDate(BodyNames.BOOKING_DATES.getValue() + ".", BodyNames.CHECKOUT.getValue(), response);
        if (response.getBody().jsonPath().getString(BodyNames.ADDITIONAL_NEEDS.getValue()) != null) {
            validateResponseBodyExpectedFieldIsNotNullAndEmpty("", BodyNames.ADDITIONAL_NEEDS.getValue(), response);
        }
        assertion.assertAll();
    }

    @Step("Response body - Validate field {1} is not null and not empty")
    public void validateResponseBodyExpectedFieldIsNotNullAndEmpty(String path, String fieldName, Response response) {
        String fieldValue = response.getBody().jsonPath().getString(path + fieldName);
        int fieldValueLength = fieldValue.length();
        assertion.assertThat(fieldValue).as("Field " + fieldName + " is null").isNotNull();
        assertion.assertThat(fieldValueLength).as("Field " + fieldName + " is empty, length = " + fieldValueLength).isNotZero();
    }

    @Step("Response body - Validate field {1} is not null and only has digits")
    public void validateResponseBodyExpectedFieldValueInteger(String path, String fieldName, Response response) {
        Integer intValue = response.getBody().jsonPath().getInt(path + fieldName);
        assertion.assertThat(intValue).as("Field " + fieldName + " is null").isNotNull();
        assertion.assertThat(String.valueOf(intValue).matches("[0-9.]+")).as(fieldName + " field doesn't have the expected value: " + intValue).isTrue();
    }

    @Step("Response body - Validate field {1} is not null and is a boolean")
    public void validateResponseBodyExpectedFieldValueBoolean(String path, String fieldName, Response response) {
        Boolean booleanValue = response.getBody().jsonPath().getBoolean(path + fieldName);
        Pattern pattern = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(String.valueOf(booleanValue));
        assertion.assertThat(booleanValue).as("Field " + fieldName + " is null").isNotNull();
        assertion.assertThat(matcher.matches()).as(fieldName + " field doesn't have the expected value: " + booleanValue).isTrue();
    }

    @Step("Response body - Validate field {1} is not null, not empty and is a valid date")
    public void validateResponseBodyExpectedFieldValueDate(String path, String fieldName, Response response) {
        String fieldValue = response.getBody().jsonPath().getString(path + fieldName);
        int fieldValueLength = fieldValue.length();
        assertion.assertThat(fieldValue).as("Field " + fieldName + " is null").isNotNull();
        assertion.assertThat(fieldValueLength).as("Field " + fieldName + " is empty, length = " + fieldValueLength).isNotZero();
        assertion.assertThat(isValidResponseBodyDate(fieldValue)).as(fieldName + " value doesn't have the correct format: " + fieldValue).isTrue();
    }


    @Step("Validate expected response header fields")
    public void validateResponseHeadersHasExpectedFields(Response response) {
        assertion = new SoftAssertions();
        validateHeaderSize(response.getHeaders().size(), 8);
        validateResponseHeaderExpectedFieldValueString(HeadersNames.SERVER.getValue(), HeaderValues.COWBOY.getValue(), response);
        validateResponseHeaderExpectedFieldValueString(HeadersNames.CONNECTION.getValue(), HeaderValues.KEEP_ALIVE.getValue(), response);
        validateResponseHeaderExpectedFieldValueString(HeadersNames.X_POWERED_BY.getValue(), HeaderValues.EXPRESS.getValue(), response);
        validateResponseHeaderExpectedFieldValueString(HeadersNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON_CHARSET_UTF_8.getValue(), response);
        validateResponseHeaderExpectedFieldValueInteger(HeadersNames.CONTENT_LENGTH.getValue(), response.getHeader(HeadersNames.CONTENT_LENGTH.getValue()));
        validateResponseHeaderExpectedFieldValueNotNull(HeadersNames.ETAG.getValue(), response.getHeader(HeadersNames.ETAG.getValue()), response);
        validateResponseHeaderExpectedFieldValueDate(HeadersNames.DATE.getValue(), response.getHeader(HeadersNames.DATE.getValue()));
        validateResponseHeaderExpectedFieldValueString(HeadersNames.VIA.getValue(), HeaderValues.VEGUR_1_1.getValue(), response);
        assertion.assertAll();
    }

    @Step("Header - Validate expected size = {0}")
    public void validateHeaderSize(int currentSize, int expectedSize) {
        assertion.assertThat(currentSize).as("Response headers doesn't have the expected number of fields: " + currentSize).isEqualTo(expectedSize);
    }

    @Step("Header - Validate field {0} = {1}")
    public void validateResponseHeaderExpectedFieldValueString(String fieldName, String expectedValue, Response response) {
        String currentValue = response.getHeader(fieldName);
        assertion.assertThat(currentValue).as("Field " + fieldName + " is null").isNotNull();
        assertion.assertThat(currentValue).as(fieldName + " field doesn't have the expected value: " + currentValue).isEqualTo(expectedValue);
    }

    @Step("Header - Validate field {0} = {1}, and only has digits")
    public void validateResponseHeaderExpectedFieldValueInteger(String fieldName, String currentValue) {
        assertion.assertThat(currentValue).as("Field " + fieldName + " is null").isNotNull();
        assertion.assertThat(currentValue.matches("[0-9.]+")).as(fieldName + " field doesn't have the expected value: " + currentValue).isTrue();
    }

    @Step("Header - Validate field {0} = {1}, and is a valid date")
    public void validateResponseHeaderExpectedFieldValueDate(String fieldName, String currentValue) {
        assertion.assertThat(currentValue).as("Field " + fieldName + " is null").isNotNull();
        assertion.assertThat(isValidResponseHeaderDate(currentValue)).as(fieldName + " value doesn't have the correct format: " + currentValue).isTrue();
    }

    @Step("Header - Validate field {0} = {1}")
    public void validateResponseHeaderExpectedFieldValueNotNull(String fieldName, String expectedValue, Response response) {
        String currentValue = response.getHeader(fieldName);
        assertion.assertThat(currentValue).as(fieldName + " field doesn't have the expected value: " + currentValue).isNotNull();
    }

    public boolean isValidResponseHeaderDate(String dateString) {
        try {
            LocalDate.parse(dateString, DateTimeFormatter.RFC_1123_DATE_TIME);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public boolean isValidResponseBodyDate(String dateString) {
        try {
            LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Step("Get booking information by id = {0}")
    public Response getBookingInformationById(int id) {
        return manager.getBookingInformationById(id);
    }

    @Step("Create booking")
    public Response postCreateBooking() {
        return manager.postCreateBooking();
    }

    @Step("Validate expected response body fields")
    public void validatePostCreateBookingResponseBodyHasExpectedFields(Response response) {
        assertion = new SoftAssertions();
        validateResponseBodyExpectedFieldValueInteger("", BodyNames.BOOKING_ID.getValue(), response);
        validateResponseBodyExpectedFieldIsNotNullAndEmpty(BodyNames.BOOKING.getValue().concat("."), BodyNames.FIRST_NAME.getValue(), response);
        validateResponseBodyExpectedFieldIsNotNullAndEmpty(BodyNames.BOOKING.getValue().concat("."), BodyNames.LAST_NAME.getValue(), response);
        validateResponseBodyExpectedFieldValueInteger(BodyNames.BOOKING.getValue().concat("."), BodyNames.TOTAL_PRICE.getValue(), response);
        validateResponseBodyExpectedFieldValueBoolean(BodyNames.BOOKING.getValue().concat("."), BodyNames.DEPOSIT_PAID.getValue(), response);
        validateResponseBodyExpectedFieldValueDate(BodyNames.BOOKING.getValue().concat(".").concat(BodyNames.BOOKING_DATES.getValue()).concat("."), BodyNames.CHECKIN.getValue(), response);
        validateResponseBodyExpectedFieldValueDate(BodyNames.BOOKING.getValue().concat(".").concat(BodyNames.BOOKING_DATES.getValue()).concat("."), BodyNames.CHECKOUT.getValue(), response);
        if (response.getBody().jsonPath().getString(BodyNames.ADDITIONAL_NEEDS.getValue()) != null) {
            validateResponseBodyExpectedFieldIsNotNullAndEmpty(BodyNames.BOOKING.getValue().concat("."), BodyNames.ADDITIONAL_NEEDS.getValue(), response);
        }
        assertion.assertAll();
    }

    @Step("Validate response body has the same values used on request body")
    public void validateGetBookingInformationByIdResponseBodyHasTheSameFieldValuesUsedOnRequestBody(Response response) {
        Map<String, Object> requestBody = manager.getRequestBody();
        ResponseBody responseBody = response.getBody();
        assertion = new SoftAssertions();
        validateResponseBodyFieldIsEqualsToRequestBodyField(BodyNames.FIRST_NAME.getValue(), responseBody.jsonPath().getString(BodyNames.FIRST_NAME.getValue()), requestBody.get(BodyNames.FIRST_NAME.getValue()).toString());
        validateResponseBodyFieldIsEqualsToRequestBodyField(BodyNames.LAST_NAME.getValue(), responseBody.jsonPath().getString(BodyNames.LAST_NAME.getValue()), requestBody.get(BodyNames.LAST_NAME.getValue()).toString());
        validateResponseBodyFieldIsEqualsToRequestBodyField(BodyNames.TOTAL_PRICE.getValue(), responseBody.jsonPath().getInt(BodyNames.TOTAL_PRICE.getValue()), requestBody.get(BodyNames.TOTAL_PRICE.getValue()));
        validateResponseBodyFieldIsEqualsToRequestBodyField(BodyNames.DEPOSIT_PAID.getValue(), responseBody.jsonPath().getBoolean(BodyNames.DEPOSIT_PAID.getValue()), requestBody.get(BodyNames.DEPOSIT_PAID.getValue()));
        Map<String,String> bookingDates = new Gson().fromJson(requestBody.get(BodyNames.BOOKING_DATES.getValue()).toString(), new TypeToken<HashMap<String,String>>(){}.getType()) ;
        validateResponseBodyFieldIsEqualsToRequestBodyField(BodyNames.CHECKIN.getValue(), responseBody.jsonPath().getString(BodyNames.BOOKING_DATES.getValue().concat(".").concat(BodyNames.CHECKIN.getValue())), bookingDates.get(BodyNames.CHECKIN.getValue()));
        validateResponseBodyFieldIsEqualsToRequestBodyField(BodyNames.CHECKOUT.getValue(), responseBody.jsonPath().getString(BodyNames.BOOKING_DATES.getValue().concat(".").concat(BodyNames.CHECKOUT.getValue())), bookingDates.get(BodyNames.CHECKOUT.getValue()));
        validateResponseBodyFieldIsEqualsToRequestBodyField(BodyNames.ADDITIONAL_NEEDS.getValue(), responseBody.jsonPath().getString(BodyNames.ADDITIONAL_NEEDS.getValue()), requestBody.get(BodyNames.ADDITIONAL_NEEDS.getValue()).toString());
        assertion.assertAll();
    }

    @Step("Body - Validate {0} field value on response body equals to request body: {1} = {2}")
    public void validateResponseBodyFieldIsEqualsToRequestBodyField(String fieldName, Object currentValue, Object expectedValue) {
        assertion.assertThat(currentValue).as("Field: " + fieldName + "Doesn't have the same value used on the request body, Current= " + currentValue + " Expected = " + expectedValue).isEqualTo(expectedValue);
    }

    public int getIdFromCreatedBooking(Response response) {
        return response.getBody().jsonPath().getInt(BodyNames.BOOKING_ID.getValue());
    }
}
