package com.github.eataborda.api.features.booker;

import com.github.eataborda.api.enums.StatusCode;
import com.github.eataborda.api.steps.BookingSteps;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTagValuesOf;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
@WithTagValuesOf({"regression", "patch_method"})
public class PatchPartialUpdateBooking {
    private static String token;
    private static int bookingId;
    private static BookingSteps setupApiSteps = new BookingSteps();

    @Steps
    BookingSteps apiSteps;

    @BeforeClass
    public static void setupValues() {
        Response response = setupApiSteps.postCreateBooking();
        bookingId = setupApiSteps.getIdFromCreatedBooking(response);
        token = setupApiSteps.getSessionToken();
    }

    @Test
    public void patchPartialUpdateBooking() {
        Response response = apiSteps.patchPartialUpdateBooking(bookingId, token);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), response);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        apiSteps.validateResponseBodyHasExpectedFields(response);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersHasExpectedFields(response);
        //Verify that the changes take effect after the partial update
        Response responseAfterPartialUpdateBooking = apiSteps.getBookingById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterPartialUpdateBooking);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterPartialUpdateBooking);
        apiSteps.validateResponseBodyHasExpectedFields(responseAfterPartialUpdateBooking);
        apiSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterPartialUpdateBooking, "partialUpdate");
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterPartialUpdateBooking);
        apiSteps.validateResponseHeadersHasExpectedFields(responseAfterPartialUpdateBooking);
    }

}
