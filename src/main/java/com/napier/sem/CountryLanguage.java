package com.napier.sem;

public class CountryLanguage {
    // Code of the country which the city belongs to
    public String countryCode;
    // Language spoken in this country
    public String language;
    // T or F, if language is official for said country
    public Boolean isOfficial;
    // Percentage of speakers in said country
    public double percentage;

    /**
     * No-args constructor
     */
    public CountryLanguage() {
    }

    /**
     * All-args constructor
     */
    public CountryLanguage(
            String countryCode,
            String language,
            Boolean isOfficial,
            double percentage) {

        this.countryCode = countryCode;
        this.language = language;
        this.isOfficial = isOfficial;
        this.percentage = percentage;
    }
}
