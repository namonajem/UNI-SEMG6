package com.napier.sem;

public class CountryLanguage {
    // Code of the country which the city belongs to
    protected String countryCode;
    // Language spoken in this country
    protected String language;
    // T or F, if language is official for said country
    protected Boolean isOfficial;
    // Percentage of speakers in said country
    protected double percentage;

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
