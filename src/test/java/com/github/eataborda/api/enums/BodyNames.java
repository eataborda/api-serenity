package com.github.eataborda.api.enums;

public enum BodyNames {
    FIRST_NAME("firstname"),
    LAST_NAME("lastname"),
    TOTAL_PRICE("totalprice"),
    DEPOSIT_PAID("depositpaid"),
    BOOKING_DATES("bookingdates"),
    CHECKIN("checkin"),
    CHECKOUT("checkout"),
    ADDITIONAL_NEEDS("additionalneeds"),
    BOOKING("booking"),
    BOOKING_ID("bookingid");

    private final String value;

    BodyNames(String value){this.value = value;}

    public String getValue(){return value;}

}
