package com.github.eataborda.api.features.exampleapi;

import com.github.eataborda.api.enums.StatusCode;
import com.github.eataborda.api.steps.APISteps;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.TestData;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SerenityParameterizedRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class GetBoookingTest {
    private static String token;
    private static APISteps tokenStep = new APISteps();
    private String computerBrand;
    private String computerCompany;

    @Steps
    APISteps apiSteps;

    @Qualifier
    public String qualifier(){ return "- Computer Brand - "+computerBrand;}

    @TestData(columnNames = "computerBrand, computerCompany")
    public static List<Object[]> testData(){
        return getTestData();
    }

    @BeforeClass
    public static void setupValues(){
        token = tokenStep.getSessionToken();
    }

    public GetBoookingTest(String computerBrand, String computerCompany){
        this.computerBrand = computerBrand;
        this.computerCompany = computerCompany;
    }

    @Test
    public void getAllBookingIds(){
        Response response = apiSteps.getAllBookingIds(token);
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(),response);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        apiSteps.validateGetAllBookingIdsResponseBodyHasExpectedFields(response);
        apiSteps.validateGetAllBookingIdsResponseHeaders(response);
    }

    public static List<Object[]> getTestData(){
        List<String> computerBrandList = new ArrayList<>(Arrays.asList("asus","ibm","thinkpad"));
        List<List<String>> testData = new ArrayList<>();
        for(String computerBrand:computerBrandList){
            testData.add(Arrays.asList(computerBrand,computerBrand));
        }
        return testData.stream().map(List::toArray).collect(Collectors.toList());
    }
}
