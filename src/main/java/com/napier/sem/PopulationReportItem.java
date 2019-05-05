package com.napier.sem;

public class PopulationReportItem {
    // Territory name
    public String name;
    // Population of the territory
    public long population;
    // Population living in cities
    public long inCities;
    // Percentage of population living in cities
    public String inCitiesPct;
    //Population not living in cities
    public long nonCities;
    // Percentage of the population not living in cities
    public String nonCitiesPct;

    /**
     * No-args constructor
     */
    public PopulationReportItem() {
    }

    /**
     * All-args constructor
     */
    public PopulationReportItem(
            String name,
            long population,
            long inCities) {

        this.name = name;
        this.population = population;
        this.inCities = inCities;
        this.inCitiesPct = String.format("%.2f", (double) inCities / population * 100);
        this.nonCities = population - inCities;
        this.nonCitiesPct = String.format("%.2f", (double) (population - inCities) / population * 100);
    }
}
