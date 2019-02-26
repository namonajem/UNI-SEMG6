package com.napier.sem;

/**
 * Represents a City
 */
public class City {
    // City ID number
    int id;
    // City name
    String name;
    // Code of the country which the city belongs to
    String countryCode;
    // Name of the district which the city belongs to
    String district;
    // Population of the city
    int population;


    /**
     * No-args constructor
     */
    public City() {
    }

    /**
     * All-args constructor
     */
    public City(
            int id,
            String name,
            String countryCode,
            String district,
            int population) {

        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.district = district;
        this.population = population;
    }
}
