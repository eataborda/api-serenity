package com.github.eataborda.api.features.booker;

import com.github.eataborda.api.enums.StatusCode;
import com.github.eataborda.api.steps.BookingSteps;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTagValuesOf;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
@WithTagValuesOf({"regression", "smoke", "get_method"})
public class GetBookingIdListTest {

    @Steps
    BookingSteps apiSteps;

    @Test
    public void getAllBookingIdList() {
        Response response = apiSteps.getAllBookingIds();
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), response);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        apiSteps.validateGetAllBookingIdListResponseBodyHasExpectedFields(response);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersHasExpectedFields(response);
    }
}
