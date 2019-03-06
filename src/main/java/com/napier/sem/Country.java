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
        return valueOf(strContinent.replaceAll("\\s", "_").toUpperCase());
    }
}

public class Country {
    // Country's Code CHAR(3)
    String code;
    // Country's Name
    String name;
    // Continent where the Country is
    Continent continent;
    // Region where the Country is
    String region;
    // Surface Area of the country in km2
    Float surfaceArea;
    // Year in which they got their independence
    int indepYear;
    // Population of the country
    int population;
    // Country's inhabitants life expectancy in country in years
    double lifeExpectancy;
    // Country's Gross National Product in million Euros
    Float gnp;
    // Country's previous Gross National Product in million Euros
    Float gnpOld;
    // Country's local name
    String localName;
    // Country's type of government
    String governmentForm;
    // Country's head of state
    String headOfState;
    // Country's capital city ID
    int capital;
    // Country's Code CHAR(2)
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
