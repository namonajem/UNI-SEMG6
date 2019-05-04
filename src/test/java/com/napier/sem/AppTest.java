package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.*;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App();
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> REPORTS TEST

    @Test
    void printCountriesReportTestNull() {
        app.printCountriesReport(null, "Test report");
    }

    @Test
    void printCountriesReportTestEmpty() {
        List<Country> countries = new ArrayList<>();
        app.printCountriesReport(countries, "Test report");
    }

    @Test
    void printCountriesReportTestContainsNull() {
        List<Country> countries = new ArrayList<>();
        countries.add(null);
        app.printCountriesReport(countries, "Test report");
    }

    @Test
    void printCitiesReportTestNull() {
        app.printCitiesReport(null, "Test report");
    }

    @Test
    void printCitiesReportTestEmpty() {
        List<City> cities = new ArrayList<>();
        app.printCitiesReport(cities, "Test report");
    }

    @Test
    void printCitiesReportTestContainsNull() {
        List<City> cities = new ArrayList<>();
        cities.add(null);
        app.printCitiesReport(cities, "Test report");
    }

    @Test
    void printCapitalCitiesReportTestNull() {
        app.printCapitalCitiesReport(null, "Test report");
    }

    @Test
    void printCapitalCitiesReportTestEmpty() {
        List<City> cities = new ArrayList<>();
        app.printCapitalCitiesReport(cities, "Test report");
    }

    @Test
    void printCapitalCitiesReportTestContainsNull() {
        List<City> cities = new ArrayList<>();
        cities.add(null);
        app.printCapitalCitiesReport(cities, "Test report");
    }
}