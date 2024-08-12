package com.github.eataborda.api.features.booker;

import com.github.eataborda.api.common.Logger;
import com.github.eataborda.api.enums.Comments;
import com.github.eataborda.api.enums.StatusCode;
import com.github.eataborda.api.steps.BookingSteps;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
@WithTagValuesOf({"health_check"})
public class GetHealthCheck {

    @Steps
    BookingSteps apiSteps;

    @Steps
    Logger l;

    @Test
    public void getHealthCheck() {
        Response response = apiSteps.getHealthCheck();
        apiSteps.validateStatusCode(StatusCode.SC_201.getValue(), response);
        l.log(Comments.SUCCESS_HEALTH_CHECK.getValue());
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersHasExpectedFields(response);
    }
}
