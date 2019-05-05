package com.napier.sem;

public class CapitalCitiesReportItem {
    // City name
    public String name;
    // Name of the country which the city belongs to
    public String country;
    // Population of the city
    public int population;

    /**
     * No-args constructor
     */
    public CapitalCitiesReportItem() {
    }

    /**
     * All-args constructor
     */
    public CapitalCitiesReportItem(
            String name,
            String country,
            int population) {

        this.name = name;
        this.country = country;
        this.population = population;
    }
}
