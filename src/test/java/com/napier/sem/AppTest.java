package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void printCitiesTestNull()
    {
        app.printCities(null);
    }

    @Test
    void printCities()
    {
        ArrayList<City> cities = new ArrayList<City>();
        City city = new City();
        city.name = "Yaren";
        city.population = 559;
        city.countryCode = "test";
        city.district = "test";
        city.id = 123;
        app.printCities(cities);
    }
}