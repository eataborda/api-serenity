package com.github.eataborda.api.features.exampleapi;

import com.github.eataborda.api.enums.StatusCode;
import com.github.eataborda.api.steps.APISteps;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class PostCreateBooking {

    @Steps
    APISteps apiSteps;

    @Test
    public void postCreateBooking(){
        Response response = apiSteps.postCreateBooking();
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), response);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        apiSteps.validatePostCreateBookingResponseBodyHasExpectedFields(response);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersHasExpectedFields(response);
        //Steps after verify the post call
        Response responseAfterCreateBooking = apiSteps.getBookingInformationById(apiSteps.getIdFromCreatedBooking(response));
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterCreateBooking);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterCreateBooking);
        apiSteps.validateResponseBodyHasExpectedFields(responseAfterCreateBooking);
        apiSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterCreateBooking,true);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterCreateBooking);
        apiSteps.validateResponseHeadersHasExpectedFields(responseAfterCreateBooking);
    }
}
