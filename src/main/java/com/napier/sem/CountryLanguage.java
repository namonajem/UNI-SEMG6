package com.napier.sem;

public class CountryLanguage {
    // Code of the country which the city belongs to
    String countryCode;
    // Language spoken in this country
    String language;
    // T or F, if language is official for said country
    Boolean isOfficial;
    // Percentage of speakers in said country
    Float percentage;

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
            Float percentage) {

        this.countryCode = countryCode;
        this.language = language;
        this.isOfficial = isOfficial;
        this.percentage = percentage;
    }
}
