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
        app.connect("localhost::33060");
    }

    /*
    //gets all countries from database
    //checks size of arraylist
    //check first and last entries
    */
    @Test
    void testGetAllCountriesByPop()
    {
        ArrayList<Country> countries;
        countries = app.getAllCountriesByPop();

        assertEquals(countries.size(), 238); /* check arraylist size against actual*/

        //check first entry matches DB
        assertEquals((countries.get(0)).name,"Mumbai (Bombay)");
        assertEquals((countries.get(0)).population,10500000);
        //check last entry matches DB
        assertEquals(((countries.get((countries.size())-1)).name),"Adamstown");
        assertEquals(((countries.get((countries.size())-1)).population),42);
    }

    @Test
    void testGetCountriesInContinentByPop(){
        ArrayList<Country> countries;
        countries = app.getCountriesInContinentByPop("Africa");

        assertEquals(countries.size(), 58); /* check arraylist size against actual*/

        //check first entry matches DB
        assertEquals((countries.get(0)).name,"Nigeria");
        assertEquals((countries.get(0)).population,111506000);
        //check last entry matches DB
        assertEquals(((countries.get((countries.size())-1)).name),"British Indian Ocean Territory");
        assertEquals(((countries.get((countries.size())-1)).population),0);
    }

    @Test
    void testGetCountriesInRegionByPop(){
    ArrayList<Country> countries;
    countries = app.getCountriesInRegionByPop("Central Africa");

    assertEquals(countries.size(), 9); /* check arraylist size against actual*/

    //check first entry matches DB
    assertEquals((countries.get(0)).name,"Congo, The Democratic Republic of the");
    assertEquals((countries.get(0)).population,51654000);
    //check last entry matches DB
    assertEquals(((countries.get((countries.size())-1)).name),"Sao Tome and Principe");
    assertEquals(((countries.get((countries.size())-1)).population),147000);
    }
/*

    @Test
    void TestGetTopCountriesByPop(){
        ArrayList<Country> countries;
        countries = app.getTopCountriesByPop(5);

        assertEquals(countries.size(), 5); /* check arraylist size against actual*/

        //check first entry matches DB
        //assertEquals((countries.get(0)).name,"Congo, The Democratic Republic of the");
        //assertEquals((countries.get(0)).population,51654000);
        //check last entry matches DB
        //assertEquals(((countries.get((countries.size())-1)).name),"Central African Republic");
        //assertEquals(((countries.get((countries.size())-1)).population),3615000);
    //}

    @Test
void testGetTopCountriesInContinentByPop(){
    ArrayList<Country> countries;
    countries = app.getTopCountriesInContinentByPop("Africa", 5);

    assertEquals(countries.size(), 5); /* check arraylist size against actual*/

    //check first entry matches DB
    assertEquals((countries.get(0)).name,"Nigeria");
    assertEquals((countries.get(0)).population,111506000);
    //check last entry matches DB
    assertEquals(((countries.get((countries.size())-1)).name),"South Africa");
    assertEquals(((countries.get((countries.size())-1)).population),40377000);
}

    @Test
    void testGetTopCountriesInRegionByPop(){
        ArrayList<Country> countries;
        countries = app.getTopCountriesInRegionByPop("Central Africa", 5);

        assertEquals(countries.size(), 5); /* check arraylist size against actual*/

        //check first entry matches DB
        assertEquals((countries.get(0)).name,"Nigeria");
        assertEquals((countries.get(0)).population,111506000);
        //check last entry matches DB
        assertEquals(((countries.get((countries.size())-1)).name),"South Africa");
        assertEquals(((countries.get((countries.size())-1)).population),40377000);
    }
}