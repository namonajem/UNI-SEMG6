package com.napier.sem;

public class CountriesReportItem {
    // Country's Code CHAR(3)
    public String code;
    // Country's Name
    public String name;
    // Continent where the Country is
    public String continent;
    // Region where the Country is
    public String region;
    // Population of the country
    public int population;
    // Name of the capital
    public String capital;

    /**
     * No-args constructor
     */
    public CountriesReportItem() {
    }

    /**
     * All-args constructor
     */
    public CountriesReportItem(
            String code,
            String name,
            String continent,
            String region,
            int population,
            String capital) {

        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capital = capital;
    }
}
