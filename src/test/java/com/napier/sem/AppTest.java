package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App();
    }

    @Test
    void printprintCountriesReportTestNull() {
        app.printCountriesReport(null, "Test report");
    }

    @Test
    void printCountriesReportTestEmpty() {
        ArrayList<Country> countries = new ArrayList<Country>();
        app.printCountriesReport(countries, "Test report");
    }

    @Test
    void printCountriesReportTestContainsNull() {
        ArrayList<Country> countries = new ArrayList<Country>();
        countries.add(null);
        app.printCountriesReport(countries, "Test report");
    }

    @Test
    void printCountriesReport() {
        ArrayList<Country> countries = app.getCountriesByContinent("north america");
        app.printCountriesReport(countries, "Test report");
    }
}