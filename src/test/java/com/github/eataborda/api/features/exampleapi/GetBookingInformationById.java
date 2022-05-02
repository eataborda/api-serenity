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
public class GetBookingInformationById {
    private static int bookingId;
    private static String value;
    private static APISteps setupApiSteps = new APISteps();

    @Steps
    APISteps apiSteps;

    @BeforeClass
    public static void setupValues() {
        Response response = setupApiSteps.getAllBookingIds();
        bookingId = setupApiSteps.getARandomExistingBookingId(response);
    }

    @Test
    public void getBookingInformationById() {
        Response response = apiSteps.getBookingInformationById(bookingId);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), response);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        apiSteps.validateGetBookingInformationByIdResponseBodyHasExpectedFields(response);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersHasExpectedFields(response);
    }
}
