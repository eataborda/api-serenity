package com.github.eataborda.api.features.booker;

import com.github.eataborda.api.common.Logger;
import com.github.eataborda.api.enums.Comments;
import com.github.eataborda.api.enums.StatusCode;
import com.github.eataborda.api.steps.BookingSteps;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
@WithTagValuesOf({"regression", "smoke", "delete_method"})
public class DeleteBooking {
    private static String token;
    private static int bookingId;
    private static BookingSteps setupApiSteps = new BookingSteps();

    @Steps
    BookingSteps apiSteps;

    @Steps
    Logger l;

    @BeforeClass
    public static void setupValues() {
        Response response = setupApiSteps.postCreateBooking();
        bookingId = setupApiSteps.getIdFromCreatedBooking(response);
        token = setupApiSteps.getSessionToken();
    }

    @Test
    public void deleteBooking() {
        Response response = apiSteps.deleteBooking(bookingId, token);
        apiSteps.validateStatusCode(StatusCode.SC_201.getValue(), response);
        l.log(Comments.SUCCESS_DELETE.getValue());
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersHasExpectedFields(response);
        ////Verify that the booking does not exist after deletion u
        Response responseAfterDeletingBooking = apiSteps.getBookingById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_404.getValue(), responseAfterDeletingBooking);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterDeletingBooking);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterDeletingBooking);
        apiSteps.validateResponseHeadersHasExpectedFields(responseAfterDeletingBooking);
    }
}
