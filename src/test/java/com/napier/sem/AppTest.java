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

    /*
    //More robust testing is needed
    //consider adding tests for methods with more values being passed
    */

    //countries by pop tests
    @Test
    void printCountriesByPopTest(){
        ArrayList<Country> countries = new ArrayList<>();

        Country cntry = new Country(
                "CHL","Chile","South America",
                "South America",756626.00f,1810,
                15211000,75.7,72949.00f,
                75780.00f,"Chile","Republic",
                "Ricardo Lagos Escobar",554,"CL"
        );

        countries.add(cntry);


        Country cntry1 = new Country(
                "BHR","Bahrain","Asia",
                "Middle East",694.00f,1971,
                617000,73.0f,6366.00f,
                6097.00f,"Al-Bahrayn","Monarchy (Emirate)",
                "Hamad ibn Isa al-Khalifa",149,"BH"
        );

        countries.add(cntry1);

        Country cntry2 = new Country(
                "ATG","Antigua and Barbuda","North America",
                "Caribbean",442.00f,1981,
                68000,70.5f,612.00f,
                584.00f,"Antigua and Barbuda","Constitutional Monarchy",
                "Elisabeth II",63,"AG"
        );

        countries.add(cntry2);

        app.printCountriesByPop(countries);
    }

    @Test
    void printCountriesByPopTestEmpty(){
        ArrayList<Country> countries = new ArrayList<>();
        app.printCountriesByPop(countries);
    }

    @Test
    void printCountriesByPopTestContainsNull(){
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.printCountriesByPop(countries);
    }

    //countries in continent by pop tests
    @Test
    void printCountriesInContinentByPopTest(){
        ArrayList<Country> countries = new ArrayList<>();

        Country cntry = new Country(
                "CHL","Chile","South America",
                "South America",756626.00f,1810,
                15211000,75.7,72949.00f,
                75780.00f,"Chile","Republic",
                "Ricardo Lagos Escobar",554,"CL"
        );

        countries.add(cntry);

        app.printCountriesInContinentByPop(countries, "South America");
    }

    @Test
    void printCountriesInContinentByPopTestEmpty(){
        ArrayList<Country> countries = new ArrayList<>();
        app.printCountriesInContinentByPop(countries, "South America");

    }

    @Test
    void printCountriesInContinentByPopTestContainsNull(){
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.printCountriesInContinentByPop(countries, "South America");
    }


    //countries in region by pop tests
    @Test
    void printCountriesInRegionByPopTest(){
        ArrayList<Country> countries = new ArrayList<>();

        Country cntry = new Country(
                "CHL","Chile","South America",
                "South America",756626.00f,1810,
                15211000,75.7,72949.00f,
                75780.00f,"Chile","Republic",
                "Ricardo Lagos Escobar",554,"CL"
        );

        countries.add(cntry);

        app.printCountriesInRegionByPop(countries,"South America");
    }

    @Test
    void printCountriesInRegionByPopTestEmpty(){
        ArrayList<Country> countries = new ArrayList<>();
        app.printCountriesInRegionByPop(countries,"Middle East");
    }

    @Test
    void printCountriesInRegionByPopTestContainsNull(){
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.printCountriesInRegionByPop(countries,"Middle East");
    }


    //top country by population tests
    @Test
    void printTopCountriesByPopTest(){
        ArrayList<Country> countries = new ArrayList<>();

        Country cntry = new Country(
                "CHL","Chile","South America",
                "South America",756626.00f,1810,
                15211000,75.7,72949.00f,
                75780.00f,"Chile","Republic",
                "Ricardo Lagos Escobar",554,"CL"
        );

        countries.add(cntry);

        app.printTopCountriesByPop(countries, 5);
    }

    @Test
    void printTopCountriesByPopTestEmpty(){
        ArrayList<Country> countries = new ArrayList<>();
        app.printTopCountriesByPop(countries, 5);
    }

    @Test
    void printTopCountriesByPopTestContainsNull(){
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.printTopCountriesByPop(countries, 5);
    }


    //top pop countries in continent tests
    @Test
    void printTopCountriesInContinentByPopTest(){
        ArrayList<Country> countries = new ArrayList<>();

        Country cntry = new Country(
                "CHL","Chile","South America",
                "South America",756626.00f,1810,
                15211000,75.7,72949.00f,
                75780.00f,"Chile","Republic",
                "Ricardo Lagos Escobar",554,"CL"
        );

        countries.add(cntry);

        app.printTopCountriesInContinentByPop(countries,"Asia", 5);
    }

    @Test
    void printTopCountriesInContinentByPopTestEmpty(){
        ArrayList<Country> countries = new ArrayList<>();
        app.printTopCountriesInContinentByPop(countries,"Asia", 5);
    }

    @Test
    void printTopCountriesInContinentByPopTestContainsNull(){
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.printTopCountriesInContinentByPop(countries,"Asia", 5);
    }


    //top pop countries in region tests
    @Test
    void printTopCountriesInRegionByPopTest(){
        ArrayList<Country> countries = new ArrayList<>();

        Country cntry = new Country(
                "CHL","Chile", "South America",
                "South America",756626.00f, 1810,
                15211000, 75.7, 72949.00f,
                75780.00f, "Chile", "Republic",
                "Ricardo Lagos Escobar", 554, "CL"
        );

        countries.add(cntry);

        app.printTopCountriesInRegionByPop(countries,"Carribean", 5);
    }

    @Test
    void printTopCountriesInRegionByPopTestEmpty(){
        ArrayList<Country> countries = new ArrayList<>();
        app.printTopCountriesInRegionByPop(countries,"Carribean", 5);
    }

    @Test
    void printTopCountriesInRegionByPopTestContainsNull(){
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.printTopCountriesInRegionByPop(countries,"Carribean", 5);
    }


    //all cities by pop tests
    @Test
    void printAllCitiesByPopTest(){
        ArrayList<City> cities = new ArrayList<>();

        City city = new City(460,"Edinburgh","GBR","Scotland",450180);

        cities.add(city);
        app.printAllCitiesByPop(cities);
    }

    @Test
    void printAllCitiesByPopTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.printAllCitiesByPop(cities);
    }

    @Test
    void printAllCitiesByPopTestContainsNull(){
        ArrayList<City> cities = new ArrayList<>();

        cities.add(null);

        app.printAllCitiesByPop(cities);

    }


    //all cities in continent by pop tests
    @Test
    void printAllCitiesInContinentByPopTest(){
        ArrayList<City> cities = new ArrayList<>();

        City city = new City(460,"Edinburgh","GBR","Scotland",450180);

        cities.add(city);
        app.printAllCitiesInContinentByPop(cities, "Europe");
    }
    @Test
    void printAllCitiesInContinentByPopTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();

        app.printAllCitiesInContinentByPop(cities, "Europe");
    }
    @Test
    void printAllCitiesInContinentByPopTestContainsNull(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);

        app.printAllCitiesInContinentByPop(cities, "Europe");

    }

    //top cities in region by pop tests
    @Test
    void printAllCitiesInRegionByPopTest(){
        ArrayList<City> cities = new ArrayList<>();

        City city = new City(460,"Edinburgh","GBR","Scotland",450180);

        cities.add(city);
        app.printAllCitiesInRegionByPop(cities,"British Islands");
    }
    @Test
    void printAllCitiesInRegionByPopTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();

        app.printAllCitiesInRegionByPop(cities,"British Islands");
    }
    @Test
    void printAllCitiesInRegionByPopTestContainsNull(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        app.printAllCitiesInRegionByPop(cities,"British Islands");
    }

    //all cities in a given country by pop tests
    @Test
    void printAllCitiesInCountryByPopTest(){
        ArrayList<City> cities = new ArrayList<>();

        City city = new City(460,"Edinburgh","GBR","Scotland",450180);

        cities.add(city);
        app.printAllCitiesInCountryByPop(cities, "GBR");
    }
    @Test
    void printAllCitiesInCountryByPopTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.printAllCitiesInCountryByPop(cities, "GBR");
    }
    @Test
    void printAllCitiesInCountryByPopTestContainsNull(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        app.printAllCitiesInCountryByPop(cities, "GBR");
    }


    //all cities in district by population tests
    @Test
    void printAllCitiesInDistrictByPopTest(){
        ArrayList<City> cities = new ArrayList<>();

        City city = new City(460,"Edinburgh","GBR","Scotland",450180);

        cities.add(city);
        app.printAllCitiesInDistrictByPop(cities, "Scotland");
    }
    @Test
    void printAllCitiesInDistrictByPopTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.printAllCitiesInDistrictByPop(cities, "Scotland");
    }
    @Test
    void printAllCitiesInDistrictByPopTestContainsNull(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        app.printAllCitiesInDistrictByPop(cities, "Scotland");
    }


    //top cities by pop tests
    @Test
    void printTopCitiesByPopTest(){
        ArrayList<City> cities = new ArrayList<>();

        City city = new City(460,"Edinburgh","GBR","Scotland",450180);

        cities.add(city);
        app.printTopCitiesByPop(cities, 5);
    }
    @Test
    void printTopCitiesByPopTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.printTopCitiesByPop(cities, 5);
    }
    @Test
    void printTopCitiesByPopTestContainsNull(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        app.printTopCitiesByPop(cities, 5);
    }


    //top cities in continent by pop tests
    @Test
    void printTopCitiesInContinentByPopTest(){
        ArrayList<City> cities = new ArrayList<>();

        City city = new City(460,"Edinburgh","GBR","Scotland",450180);

        cities.add(city);
        app.printTopCitiesInContinentByPop(cities, "Europe", 5);
    }
    @Test
    void printTopCitiesInContinentByPopTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.printTopCitiesInContinentByPop(cities, "Europe", 5);
    }
    @Test
    void printTopCitiesInContinentByPopTestContainsNull(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        app.printTopCitiesInContinentByPop(cities, "Europe", 5);
    }


    //top cities in region by pop tests
    @Test
    void printTopCitiesInRegionByPopTest(){
        ArrayList<City> cities = new ArrayList<>();

        City city = new City(460,"Edinburgh","GBR","Scotland",450180);

        cities.add(city);
        app.printTopCitiesInRegionByPop(cities, "British Islands", 5);
    }
    @Test
    void printTopCitiesInRegionByPopTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.printTopCitiesInRegionByPop(cities, "British Islands", 5);
    }
    @Test
    void printTopCitiesInRegionByPopTestContainsNull(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        app.printTopCitiesInRegionByPop(cities, "British Islands", 5);
    }

    //top pop cities in given country tests
    @Test
    void printTopCitiesInCountryByPopTest(){
        ArrayList<City> cities = new ArrayList<>();

        City city = new City(460,"Edinburgh","GBR","Scotland",450180);

        cities.add(city);
        app.printTopCitiesInCountryByPop(cities, "GBR", 5);
    }
    @Test
    void printTopCitiesInCountryByPopTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.printTopCitiesInCountryByPop(cities, "GBR", 5);
    }
    @Test
    void printTopCitiesInCountryByPopTestContainsNull(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        app.printTopCitiesInCountryByPop(cities, "GBR", 5);
    }


    //top cities in district by pop tests
    @Test
    void printTopCitiesInDistrictByPopTest(){
        ArrayList<City> cities = new ArrayList<>();

        City city = new City(460,"Edinburgh","GBR","Scotland",450180);

        cities.add(city);
        app.printTopCitiesInDistrictByPop(cities, "Scotland", 5);
    }
    @Test
    void printTopCitiesInDistrictByPopTestEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.printTopCitiesInDistrictByPop(cities, "Scotland", 5);
    }
    @Test
    void printTopCitiesInDistrictByPopTestContainsNull(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        app.printTopCitiesInDistrictByPop(cities, "Scotland", 5);
    }

}
