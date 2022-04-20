package com.github.eataborda.api.features.exampleapi;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.WithTagValuesOf;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SerenityParameterizedRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WithTagValuesOf({"tag1", "tag2", "tag3"})

public class ExampleTest {
    private static String value1;
    private static String value2;
}
