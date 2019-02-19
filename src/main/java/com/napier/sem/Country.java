package com.napier.sem;

public class Country {
    // Country Code
    String Code;
    // Country Name
    String Name;
    // Continent that this country belongs to
    String Continent; //is enum in DataBase check it
    // Region that country is in
    String Region;
    // Surface Area of the country
    Float SurfaceArea;
    // ***************** ADD
    int IndepYear;
    // Population of the country
    int Population;
    // Life expectancy in country
    int LifeExpectancy;
    // Gross National Product of Country
    Float GNP;
    // GNP ID for country
    Float GNPOId;
    // Local name for country
    String LocalName;
    // ************** ADD
    String GovernmentForm;
    // Head of state of country
    String HeadOfState;
    // Capital of the country
    int Capital;
    // ID of capital city
    String Code2;

    /**
     * No-args constructor
     */
    public Country() {
    }

    /**
     * All-args constructor
     */
    public Country(String Code, String Name, String Continent, String Region, Float SurfaceArea, int IndepYear, int Population, int LifeExpectency, Float GNP, Float GNPOID, String LocalName, String GovernmentForm, String HeadOfState, int Capital, String Code2) {
        this.Code = Code;
        this.Name = Name;
        this.Continent = Continent;
        this.Region = Region;
        this.SurfaceArea = SurfaceArea;
        this.IndepYear = IndepYear;
        this.Population = Population;
        this.LifeExpectancy = LifeExpectency;
        this.GNP = GNP;
        this.GNPOId = GNPOID;
        this.LocalName = LocalName;
        this.GovernmentForm = GovernmentForm;
        this.HeadOfState = HeadOfState;
        this.Capital = Capital;
        this.Code2 = Code2;


    }
}
