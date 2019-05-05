package com.napier.sem;

public class CitiesReportItem {
    // City name
    public String name;
    // Code of the country which the city belongs to
    public String country;
    // Name of the district which the city belongs to
    public String district;
    // Population of the city
    public int population;

    /**
     * No-args constructor
     */
    public CitiesReportItem() {
    }

    /**
     * All-args constructor
     */
    public CitiesReportItem(
            String name,
            String country,
            String district,
            int population) {

        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
    }
}
