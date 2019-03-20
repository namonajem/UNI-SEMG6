package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App();
        app.connect("db");
    }

    /*
    //gets all countries from database
    //checks size of arraylist
    //check first and last entries
    */
    @Test
    void testGetAllCountriesByPop()
    {
        ArrayList<Country> countries = new ArrayList<>();
        countries = app.getAllCountriesByPop();

        assertEquals(countries.size(), 238); /* check arraylist size against actual*/

        //check first entry matches DB
        assertEquals((countries.get(0)).name,"Mumbai (Bombay)");
        assertEquals((countries.get(0)).population,10500000);
        //check last entry matches DB
        assertEquals(((countries.get((countries.size())-1)).name),"Adamstown");
        assertEquals(((countries.get((countries.size())-1)).population),42);
    }

}