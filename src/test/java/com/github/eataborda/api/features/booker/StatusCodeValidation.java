package com.github.eataborda.api.features.booker;

import com.github.eataborda.api.common.Logger;
import com.github.eataborda.api.enums.StatusCode;
import com.github.eataborda.api.steps.BookingSteps;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class StatusCodeValidation {
    private static String token;
    private static int bookingId;
    private static BookingSteps setupApiSteps = new BookingSteps();

    @Steps
    BookingSteps apiSteps;

    @Steps
    Logger l;

    @BeforeClass
    public static void setupValues() {
        token = setupApiSteps.getSessionToken();
        Response responseCreateBooking = setupApiSteps.postCreateBooking();
        bookingId = setupApiSteps.getIdFromCreatedBooking(responseCreateBooking);
        setupApiSteps.deleteBooking(bookingId, token);
    }

    @Test
    public void statusCode200Validation() {
        Response statusCodeResponse = apiSteps.getAllBookingIds();
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), statusCodeResponse);
    }

    @Test
    public void statusCode201Validation() {
        Response statusCodeResponse = apiSteps.postCreateBooking();
        apiSteps.validateStatusCode(StatusCode.SC_201.getValue(), statusCodeResponse);
        l.log("A successful post request should return status code 201 according to the following source: https://developer.mozilla.org/es/docs/Web/HTTP/Status/201");
    }

    @Test
    public void statusCode400Validation() {
        Response response = apiSteps.postCreateBookingWithMalformedBody(StatusCode.SC_400.getValue());
        apiSteps.validateStatusCode(StatusCode.SC_400.getValue(), response);
    }

    @Test
    public void statusCode403Validation() {
        Response response = apiSteps.putUpdateBookingAuthenticationHeader(bookingId, token);
        apiSteps.validateStatusCode(StatusCode.SC_403.getValue(), response);
    }

    @Test
    public void statusCode404Validation() {
        Response response = apiSteps.getBookingById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_404.getValue(), response);
    }

    @Test
    public void statusCode405Validation() {
        Response response = apiSteps.putUpdateBooking(bookingId, token);
        apiSteps.validateStatusCode(StatusCode.SC_405.getValue(), response);
    }

    @Test
    public void statusCode500Validation() {
        Response response = apiSteps.postCreateBookingWithMalformedBody(StatusCode.SC_500.getValue());
        apiSteps.validateStatusCode(StatusCode.SC_500.getValue(), response);
    }
}
