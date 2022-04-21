package com.github.eataborda.api.service;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

public class ExampleServiceManager {

    public Response getSpecificBrandComputer(String computerBrand, String computerCompany){
        RequestSpecification request = SerenityRest.given();
        request.headers(
                "Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
                "Accept-Encoding","gzip, deflate",
                "Accept-Language","en-US,en;q=0.5",
                "Content-Type","application/json",
                "User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");
        request.queryParams("f",computerBrand);
        return request.get("http://computer-database.gatling.io/computers");
    }
}
