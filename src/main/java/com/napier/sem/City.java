package com.napier.sem;

/**
 * Represents a City
 */
public class City {
    // City ID number
    int ID;
    // City name
    String Name;
    // Code of the country which the city belongs to
    String CountryCode;
    // Name of the district which the city belongs to
    String District;
    // Population of the city
    int Population;

    /**
     * No-args constructor
     */
    public City() {
    }

    /**
     * All-args constructor
     */
    public City(int ID, String Name, String CountryCode, String District, int Population) {
        this.ID = ID;
        this.Name = Name;
        this.CountryCode = CountryCode;
        this.District = District;
        this.Population = Population;
    }
}
