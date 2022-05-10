package com.github.eataborda.api.features.booker;

import com.github.eataborda.api.common.Logger;
import com.github.eataborda.api.enums.Comments;
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
@WithTagValuesOf({"regression", "workflow"})
public class WorkFlow {
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
    }

    @Test
    public void workflow() {
        //Ping
        Response pingResponse = apiSteps.getHealthCheck();
        apiSteps.validateStatusCode(StatusCode.SC_201.getValue(), pingResponse);
        l.log(Comments.SUCCESS_HEALTH_CHECK.getValue());
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(pingResponse);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(pingResponse);
        apiSteps.validateResponseHeadersHasExpectedFields(pingResponse);
        //Create booking
        Response createBookingResponse = apiSteps.postCreateBooking();
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), createBookingResponse);
        l.log(Comments.SUCCESS_POST.getValue());
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(createBookingResponse);
        apiSteps.validatePostCreateBookingResponseBodyHasExpectedFields(createBookingResponse);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(createBookingResponse);
        apiSteps.validateResponseHeadersHasExpectedFields(createBookingResponse);
        //Validate booking exists
        bookingId = apiSteps.getIdFromCreatedBooking(createBookingResponse);
        Response responseAfterCreateBooking = apiSteps.getBookingById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterCreateBooking);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterCreateBooking);
        apiSteps.validateResponseBodyHasExpectedFields(responseAfterCreateBooking);
        apiSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterCreateBooking, "create");
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterCreateBooking);
        apiSteps.validateResponseHeadersHasExpectedFields(responseAfterCreateBooking);
        //Update booking
        Response updateBookingResponse = apiSteps.putUpdateBooking(bookingId, token);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), updateBookingResponse);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(updateBookingResponse);
        apiSteps.validateResponseBodyHasExpectedFields(updateBookingResponse);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(updateBookingResponse);
        apiSteps.validateResponseHeadersHasExpectedFields(updateBookingResponse);
        //Validate updated fields
        Response responseAfterUpdateBooking = apiSteps.getBookingById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterUpdateBooking);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterUpdateBooking);
        apiSteps.validateResponseBodyHasExpectedFields(responseAfterUpdateBooking);
        apiSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterUpdateBooking, "update");
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterUpdateBooking);
        apiSteps.validateResponseHeadersHasExpectedFields(responseAfterUpdateBooking);
        //Partial update booking
        Response partialUpdateResponse = apiSteps.patchPartialUpdateBooking(bookingId, token);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), partialUpdateResponse);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(partialUpdateResponse);
        apiSteps.validateResponseBodyHasExpectedFields(partialUpdateResponse);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(partialUpdateResponse);
        apiSteps.validateResponseHeadersHasExpectedFields(partialUpdateResponse);
        //Validate partial updated fields
        Response responseAfterPartialUpdateBooking = apiSteps.getBookingById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterPartialUpdateBooking);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterPartialUpdateBooking);
        apiSteps.validateResponseBodyHasExpectedFields(responseAfterPartialUpdateBooking);
        apiSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterPartialUpdateBooking, "partialUpdate");
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterPartialUpdateBooking);
        apiSteps.validateResponseHeadersHasExpectedFields(responseAfterPartialUpdateBooking);
        //Delete booking
        Response deleteBookingResponse = apiSteps.deleteBooking(bookingId, token);
        apiSteps.validateStatusCode(StatusCode.SC_201.getValue(), deleteBookingResponse);
        l.log(Comments.SUCCESS_DELETE.getValue());
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(deleteBookingResponse);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(deleteBookingResponse);
        apiSteps.validateResponseHeadersHasExpectedFields(deleteBookingResponse);
        //Validate booking not found
        Response responseAfterDeleteBooking = apiSteps.getBookingById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_404.getValue(), responseAfterDeleteBooking);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterDeleteBooking);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterDeleteBooking);
        apiSteps.validateResponseHeadersHasExpectedFields(responseAfterDeleteBooking);
    }
}
