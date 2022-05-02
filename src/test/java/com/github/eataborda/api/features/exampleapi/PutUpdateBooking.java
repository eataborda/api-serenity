package com.github.eataborda.api.features.exampleapi;

import com.github.eataborda.api.common.Logger;
import com.github.eataborda.api.enums.StatusCode;
import com.github.eataborda.api.steps.APISteps;
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
    private static int bookingId;
    private static final APISteps setupApiSteps = new APISteps();

    @Steps
    APISteps apiSteps;

    @BeforeClass
    public static void setupValues() {
        Response response = setupApiSteps.postCreateBooking();
        bookingId = setupApiSteps.getIdFromCreatedBooking(response);
    }

    @Test
    public void putUpdateBooking() {
        Response response = apiSteps.putUpdateBooking(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), response);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        apiSteps.validateResponseBodyHasExpectedFields(response);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersHasExpectedFields(response);
        //Validaciones por consulta
        Response responseAfterUpdateBooking = apiSteps.getBookingInformationById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterUpdateBooking);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterUpdateBooking);
        apiSteps.validateResponseBodyHasExpectedFields(responseAfterUpdateBooking);
        apiSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterUpdateBooking,false);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterUpdateBooking);
        apiSteps.validateResponseHeadersHasExpectedFields(responseAfterUpdateBooking);
    }
}
