package com.napier.sem;

/**
 * Represents a City
 */
public class City {
    // City ID number
    protected int id;
    // City name
    protected String name;
    // Code of the country which the city belongs to
    protected String countryCode;
    // Name of the district which the city belongs to
    protected String district;
    // Population of the city
    protected int population;


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

    /**
     * @return a String representation of the city.
     */
    @Override
    public String toString() {
        return "ID: " + this.id + "\n"
                + "Name: " + this.name + "\n"
                + "Country code: " + this.countryCode + "\n"
                + "District: " + this.district + "\n"
                + "Population: " + this.population + "\n";
    }
}
