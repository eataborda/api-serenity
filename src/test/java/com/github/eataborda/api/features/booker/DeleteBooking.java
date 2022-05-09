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
public class DeleteBooking {
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
    public void deleteBooking(){
        Response response = apiSteps.deleteBooking(bookingId,token);
        apiSteps.validateStatusCode(StatusCode.SC_201.getValue(), response);
        //Dejar comentario del status code que debería ser
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersHasExpectedFields(response);
        //Validaciones post delete, se espera que el booking no exista y retorne otra cosa
        Response responseAfterDeleteingBooking = apiSteps.getBookingById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_404.getValue(), responseAfterDeleteingBooking);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterDeleteingBooking);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterDeleteingBooking);
        apiSteps.validateResponseHeadersHasExpectedFields(responseAfterDeleteingBooking);
    }
}
