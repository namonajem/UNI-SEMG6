package com.napier.sem;

public class CountryLanguage {
    // Code of the country which the city belongs to
    String CountryCode;
    // Language spoken in this country
    String Language;
    // T or F, if language is official for said country
    Boolean IsOfficial;
    // Percentage of speakers in said country
    Float Percentage;

    /**
     * No-args constructor
     */
    public CountryLanguage() {
    }

    /**
     * All-args constructor
     */
    public CountryLanguage(String CountryCode, String Language, Boolean IsOfficial, Float Percentage) {
        this.CountryCode = CountryCode;
        this.Language = Language;
        this.IsOfficial = IsOfficial;
        this.Percentage = Percentage;
    }
}
