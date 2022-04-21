package com.github.eataborda.api.features.exampleapi;

import com.github.eataborda.api.steps.ExampleResponseSteps;
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

public class ExampleTest {
    private String computerBrand;
    private String computerCompany;

    @Steps
    ExampleResponseSteps exampleResponseSteps;

    @Qualifier
    public String qualifier(){ return "- Computer Brand - "+computerBrand;}

    @TestData(columnNames = "computerBrand, computerCompany")
    public static List<Object[]> testData(){
        return getTestData();
    }

    @BeforeClass
    public static void setupValues(){

    }

    public ExampleTest(String computerBrand, String computerCompany){
        this.computerBrand = computerBrand;
        this.computerCompany = computerCompany;
    }

    @Test
    public void getAsusComputers(){
        Response response = exampleResponseSteps.getAsusComputers(computerBrand,computerCompany);
        exampleResponseSteps.validateStatusCode(200,response);
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
