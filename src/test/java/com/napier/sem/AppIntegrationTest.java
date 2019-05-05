package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060");
    }

    @Test
    void testGetAllContinents() {
        List<Continent> continents = app.getAllContinents();
        assertEquals(7, continents.size());
    }

    @Test
    void testGetAllRegions() {
        List<String> regions = app.getAllRegions();
        assertTrue(7 < regions.size() && regions.size() < 239);
    }

    @Test
    void testGetCountryByCode() {
        Country country = app.getCountryByCode("GBR");
        assertEquals("United Kingdom", country.name);
    }

    @Test
    void testGetCountryByName() {
        Country country = app.getCountryByName("United Kingdom");
        assertEquals("GBR", country.code);
    }

    @Test
    void testGetCountryCodeByName() {
        String code = app.getCountryCodeByName("United Kingdom");
        assertEquals("GBR", code);
    }

    @Test
    void testGetAllCountries() {
        List<Country> countries = app.getAllCountries();
        assertEquals(239, countries.size());
    }

    @Test
    void testGetCountriesByContinent() {
        List<Country> countries = app.getCountriesByContinent("Europe");
        assertEquals("Europe", countries.get(0).continent.getName());
    }

    @Test
    void testGetCountriesByRegion() {
        List<Country> countries = app.getCountriesByRegion("British Islands");
        assertEquals("British Islands", countries.get(0).region);
    }

    @Test
    void testGetTopNCountriesLength() {
        List<Country> countries = app.getTopNCountries("10");
        assertTrue(10 >= countries.size());
    }

    @Test
    void testGetTopNCountriesByContinent() {
        List<Country> countries = app.getTopNCountriesByContinent("10","Europe");
        assertEquals("Europe", countries.get(0).continent.getName());
    }

    @Test
    void testGetTopNCountriesByContinentLength() {
        List<Country> countries = app.getTopNCountriesByContinent("10","Europe");
        assertTrue(10 >= countries.size());
    }

    @Test
    void testGetTopNCountriesByRegion() {
        List<Country> countries = app.getTopNCountriesByRegion("10","British Islands");
        assertEquals("British Islands", countries.get(0).region);
    }

    @Test
    void testGetTopNCountriesByRegionLength() {
        List<Country> countries = app.getTopNCountriesByRegion("10","British Islands");
        assertTrue(10 >= countries.size());
    }

    @Test
    void testGetAllCities() {
        List<City> cities = app.getAllCities();
        assertEquals(4079, cities.size());
    }

    @Test
    void testGetCitiesByContinent() {
        List<City> cities = app.getCitiesByContinent("Europe");
        assertEquals("Europe", app.getCountryByCode(
                cities.get(0).countryCode
        ).continent.getName());
    }

    @Test
    void testGetCitiesByRegion() {
        List<City> cities = app.getCitiesByRegion("British Islands");
        assertEquals("British Islands", app.getCountryByCode(
                cities.get(0).countryCode
        ).region);
    }

    @Test
    void testGetCitiesByCountry() {
        List<City> cities = app.getCitiesByCountry("United Kingdom");
        assertEquals("United Kingdom", app.getCountryByCode(
                cities.get(0).countryCode
        ).name);
    }

    @Test
    void testGetCitiesByDistrict() {
        List<City> cities = app.getCitiesByDistrict("Scotland");
        assertEquals("Scotland", cities.get(0).district);
    }

    @Test
    void testGetTopNCitiesLength() {
        List<City> cities = app.getTopNCities("10");
        assertTrue(10 >= cities.size());
    }

    @Test
    void testGetTopNCitiesByContinent() {
        List<City> cities = app.getTopNCitiesByContinent("10", "Europe");
        assertEquals("Europe", app.getCountryByCode(
                cities.get(0).countryCode
        ).continent.getName());
    }

    @Test
    void testGetTopNCitiesByContinentLength() {
        List<City> cities = app.getTopNCitiesByContinent("10","Europe");
        assertTrue(10 >= cities.size());
    }

    @Test
    void testGetTopNCitiesByRegion() {
        List<City> cities = app.getTopNCitiesByRegion("10", "British Islands");
        assertEquals("British Islands", app.getCountryByCode(
                cities.get(0).countryCode
        ).region);
    }

    @Test
    void testGetTopNCitiesByRegionLength() {
        List<City> cities = app.getTopNCitiesByRegion("10","British Islands");
        assertTrue(10 >= cities.size());
    }

    @Test
    void testGetTopNCitiesByCountry() {
        List<City> cities = app.getTopNCitiesByCountry("10", "United Kingdom");
        assertEquals("United Kingdom", app.getCountryByCode(
                cities.get(0).countryCode
        ).name);
    }

    @Test
    void testGetTopNCitiesByCountryLength() {
        List<City> cities = app.getTopNCitiesByCountry("10","United Kingdom");
        assertTrue(10 >= cities.size());
    }

    @Test
    void testGetTopNCitiesByDistrict() {
        List<City> cities = app.getTopNCitiesByDistrict("10", "Scotland");
        assertEquals("Scotland", cities.get(0).district);
    }

    @Test
    void testGetTopNCitiesByDistrictLength() {
        List<City> cities = app.getTopNCitiesByDistrict("10","Scotland");
        assertTrue(10 >= cities.size());
    }

    @Test
    void testGetAllCapitalCities() {
        List<City> cities = app.getAllCapitalCities();
        assertTrue(4079 >= cities.size());
    }

    @Test
    void testGetCapitalCitiesByContinent() {
        List<City> cities = app.getCapitalCitiesByContinent("Europe");
        assertEquals("Europe", app.getCountryByCode(
                cities.get(0).countryCode
        ).continent.getName());
    }

    @Test
    void testGetCapitalCitiesByRegion() {
        List<City> cities = app.getCapitalCitiesByRegion("British Islands");
        assertEquals("British Islands", app.getCountryByCode(
                cities.get(0).countryCode
        ).region);
    }

    @Test
    void testGetTopNCapitalCitiesLength() {
        List<City> cities = app.getTopNCapitalCities("10");
        assertTrue(10 >= cities.size());
    }

    @Test
    void testGetTopNCapitalCitiesByContinent() {
        List<City> cities = app.getTopNCapitalCitiesByContinent("10", "Europe");
        assertEquals("Europe", app.getCountryByCode(
                cities.get(0).countryCode
        ).continent.getName());
    }

    @Test
    void testGetTopNCapitalCitiesByContinentLength() {
        List<City> cities = app.getTopNCapitalCitiesByContinent("10","Europe");
        assertTrue(10 >= cities.size());
    }

    @Test
    void testGetTopNCapitalCitiesByRegion() {
        List<City> cities = app.getTopNCapitalCitiesByRegion("10", "British Islands");
        assertEquals("British Islands", app.getCountryByCode(
                cities.get(0).countryCode
        ).region);
    }

    @Test
    void testGetTopNCapitalCitiesByRegionLength() {
        List<City> cities = app.getTopNCapitalCitiesByRegion("10","British Islands");
        assertTrue(10 >= cities.size());
    }

    @Test
    void printCountriesReport() {
        List<Country> countries = app.getAllCountries();
        app.printCountriesReport(countries, "Test Report");
    }

    @Test
    void printCitiesReport() {
        List<City> cities = app.getAllCities();
        app.printCitiesReport(cities, "Test Report");
    }

    @Test
    void printCapitalCitiesReport() {
        List<City> cities = app.getAllCapitalCities();
        app.printCitiesReport(cities, "Test Report");
    }

    @Test
    void printPopulationInContinentsReport() {
        app.printPopulationInContinentsReport("Test Report");
    }

    @Test
    void printPopulationInRegionsReport() {
        app.printPopulationInRegionsReport("Test Report");
    }

    @Test
    void printPopulationInCountriesReport() {
        app.printPopulationInCountriesReport("Test Report");
    }

    @Test
    void printPopulationSpeakingLanguagesReportTest() {
        app.printPopulationSpeakingLanguagesReport();
    }
}