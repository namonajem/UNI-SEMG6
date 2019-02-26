package com.napier.sem;

public class Country {
    // Country Code CHAR(3)
    String code;
    // Country Name
    String name;
    // Continent that this country belongs to
    String continent; //is enum in DataBase check it
    // Region that country is in
    String region;
    // Surface Area of the country
    Float surfaceArea;
    // ***************** ADD
    int indepYear;
    // Population of the country
    int population;
    // Life expectancy in country
    double lifeExpectancy;
    // Gross National Product of Country
    Float gnp;
    // GNP ID for country
    Float gnpOld;
    // Local name for country
    String localName;
    // ************** ADD
    String governmentForm;
    // Head of state of country
    String headOfState;
    // ID of the capital city
    int capital;
    // Country Code CHAR(2)
    String code2;

    /**
     * No-args constructor
     */
    public Country() {
    }

    /**
     * All-args constructor
     */
    public Country(
            String code,
            String name,
            String continent,
            String region,
            Float surfaceArea,
            int indepYear,
            int population,
            double lifeExpectency,
            Float gnp,
            Float gnpOld,
            String localName,
            String governmentForm,
            String headOfState,
            int capital,
            String code2) {

        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.surfaceArea = surfaceArea;
        this.indepYear = indepYear;
        this.population = population;
        this.lifeExpectancy = lifeExpectency;
        this.gnp = gnp;
        this.gnpOld = gnpOld;
        this.localName = localName;
        this.governmentForm = governmentForm;
        this.headOfState = headOfState;
        this.capital = capital;
        this.code2 = code2;
    }

    public int getCapital() {
        return capital;
    }
}
