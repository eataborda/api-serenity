package com.github.eataborda.api.steps;

import com.github.eataborda.api.common.Logger;
import com.github.eataborda.api.service.ExampleServiceManager;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;

public class ExampleResponseSteps {
    private ExampleServiceManager manager = new ExampleServiceManager();
    private SoftAssertions assertion;

    @Steps
    Logger l;

    @Step("Get Asus Computers")
    public Response getAsusComputers(String computerBrand, String computerCompany) {
        return manager.getSpecificBrandComputer(computerBrand, computerCompany);
    }

    @Step("Validate expected status code: {0}")
    public void validateStatusCode(int statusCode, Response response) {
        Assert.assertEquals("Not expected status code:", statusCode, response.getStatusCode());
    }
}
