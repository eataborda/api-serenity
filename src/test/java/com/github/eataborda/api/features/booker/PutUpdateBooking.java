package com.github.eataborda.api.features.booker;

import com.github.eataborda.api.enums.StatusCode;
import com.github.eataborda.api.steps.BookingSteps;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PutUpdateBooking {
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
    public void putUpdateBooking() {
        Response response = apiSteps.putUpdateBooking(bookingId,token);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), response);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        apiSteps.validateResponseBodyHasExpectedFields(response);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersHasExpectedFields(response);
        //Validaciones despues de actualizar
        Response responseAfterUpdateBooking = apiSteps.getBookingById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterUpdateBooking);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterUpdateBooking);
        apiSteps.validateResponseBodyHasExpectedFields(responseAfterUpdateBooking);
        apiSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterUpdateBooking,"update");
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterUpdateBooking);
        apiSteps.validateResponseHeadersHasExpectedFields(responseAfterUpdateBooking);
    }
}
