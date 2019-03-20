package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

public class CityTest {
    static City city;

    @BeforeAll
    static void init() {
        city = new City();
    }

/*
//all args - not null
no args
partial args
incorrect args
 */
@Test
public void constructorTest(){
    city = new City(460,"Edinburgh","GBR","Scotland",450180);
    assertNotNull(city);
    assertNotNull(city.id);
    assertNotNull(city.name);
    assertNotNull(city.countryCode);
    assertNotNull(city.district);
    assertNotNull(city.population);
}

    @Test
    public void IllegalArgConstructorTest(){
        city = new City();
        city = new City(-460,"Edinburgh","GBR","Scotland",-450180);
        assertNotNull(city.id);
        assertNotNull(city.name);
        assertNotNull(city.countryCode);
        assertNotNull(city.district);
        assertNotNull(city.population);


    }




}