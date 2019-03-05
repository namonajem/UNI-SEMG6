package com.napier.sem;

enum Continent {
    ASIA ("Asia"),
    EUROPE ("Europe"),
    NORTH_AMERICA ("North America"),
    AFRICA ("Africa"),
    OCEANIA ("Oceania"),
    ANTARCTICA ("Antarctica"),
    SOUTH_AMERICA ("South America");

    private final String name;

    Continent(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Continent toContinent(String strContinent) {
        return valueOf(strContinent.toUpperCase());
    }
}

public class Country {
    // Country Code CHAR(3)
    String code;
    // Country Name
    String name;
    // Continent that this country belongs to
    Continent continent;
    // Region that country is in
    String region;
    // Surface Area of the country
    Float surfaceArea;
    // Year in which they got their independency
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
    // Type of government the country has
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
            double lifeExpectancy,
            Float gnp,
            Float gnpOld,
            String localName,
            String governmentForm,
            String headOfState,
            int capital,
            String code2) {

        this.code = code;
        this.name = name;
        this.continent = Continent.toContinent(continent);
        this.region = region;
        this.surfaceArea = surfaceArea;
        this.indepYear = indepYear;
        this.population = population;
        this.lifeExpectancy = lifeExpectancy;
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

    /**
     * @return a String representation of the country.
     */
    @Override
    public String toString() {
        return "Code: " + this.code + "\n"
                + "Name: " + this.name + "\n"
                + "Continent: " + this.continent.getName() + "\n"
                + "Region: " + this.region + "\n"
                + "Surface Area: " + this.surfaceArea + "\n"
                + "Independence year: " + this.indepYear + "\n"
                + "Population: " + this.population + "\n"
                + "Life expectancy: " + this.lifeExpectancy + "\n"
                + "GNP: " + this.gnp + "\n"
                + "Old GNP: " + this.gnpOld + "\n"
                + "Local name: " + this.localName+ "\n"
                + "Government form: " + this.governmentForm + "\n"
                + "Head of state: " + this.headOfState + "\n"
                + "Capital ID: " + this.capital + "\n"
                + "Code 2: " + this.code2 + "\n";
    }
}
