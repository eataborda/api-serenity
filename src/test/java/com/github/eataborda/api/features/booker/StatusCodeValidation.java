package com.github.eataborda.api.features.booker;

import com.github.eataborda.api.common.Logger;
import com.github.eataborda.api.enums.Comments;
import com.github.eataborda.api.enums.StatusCode;
import com.github.eataborda.api.steps.BookingSteps;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTagValuesOf;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WithTagValuesOf({"regression", "status_code:all"})
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
    @WithTag("status_code:200")
    public void statusCode200Validation() {
        Response statusCodeResponse = apiSteps.getAllBookingIds();
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), statusCodeResponse);
    }

    @Test
    @WithTag("status_code:201")
    public void statusCode201Validation() {
        Response statusCodeResponse = apiSteps.postCreateBooking();
        apiSteps.validateStatusCode(StatusCode.SC_201.getValue(), statusCodeResponse);
        l.log(Comments.SUCCESS_POST.getValue());
    }

    @Test
    @WithTag("status_code:400")
    public void statusCode400Validation() {
        Response response = apiSteps.postCreateBookingWithMalformedBody(StatusCode.SC_400.getValue());
        apiSteps.validateStatusCode(StatusCode.SC_400.getValue(), response);
    }

    @Test
    @WithTag("status_code:403")
    public void statusCode403Validation() {
        Response response = apiSteps.putUpdateBookingAuthenticationHeader(bookingId, token);
        apiSteps.validateStatusCode(StatusCode.SC_403.getValue(), response);
    }

    @Test
    @WithTag("status_code:404")
    public void statusCode404Validation() {
        Response response = apiSteps.getBookingById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_404.getValue(), response);
    }

    @Test
    @WithTag("status_code:405")
    public void statusCode405Validation() {
        Response response = apiSteps.putUpdateBooking(bookingId, token);
        apiSteps.validateStatusCode(StatusCode.SC_405.getValue(), response);
    }

    @Test
    @WithTag("status_code:500")
    public void statusCode500Validation() {
        Response response = apiSteps.postCreateBookingWithMalformedBody(StatusCode.SC_500.getValue());
        apiSteps.validateStatusCode(StatusCode.SC_500.getValue(), response);
    }
}
