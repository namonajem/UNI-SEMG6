package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    public static void main(String[] args) {

        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // TEST IMPLEMENTATION
        //ArrayList<City> myList = a.getTopNCapitalCitiesByRegion(25,"california");
        //a.printCitiesReport(myList, "TOP 25 CAPITAL CITIES OF CARIBBEAN");
        Country unitedStates = a.getCountryByCode("USA");
        System.out.println(unitedStates.toString());

        // Disconnect from database
        a.disconnect();
    } // METHOD main()

    /**
     * Connect to the MySQL database.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    } // METHOD connect()

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            }
            catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Convert string to title case
     * @param str string to convert
     * @return A string converted into title case
     */
    public String toTitleCase(String str) {
        if (str.length() == 0) {
            return "";
        } else if(str.length() == 1) {
            return str.toUpperCase();
        } else {
            String part1 = str.substring(0, 1);
            String part2 = str.substring(1);
            str = part1.toUpperCase() + part2.toLowerCase();
        }
        return str;
    }
    // CITIES METHODS -----------------------------------------------------------------------------------

    /**
     * Gets all the cities from the world MySQL database.
     * @return A list of all cities in database, or null if there is an error.
     */
    public ArrayList<City> getAllCities() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                        + "FROM city "
                        + "ORDER BY Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a List for the cities
            ArrayList<City> cities = new ArrayList<>();
            // While there are more cities in the result set
            while (rset.next()) {
                // Create a new city with the values in the result set
                City myCity = new City(
                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")
                );
                // Add city to the list
                cities.add(myCity);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get cities");
            return null;
        }
    } // METHOD getAllCities()

    /**
     * Gets the city from the world MySQL database which has a given id code.
     * @param id the code of the city we want to get.
     * @return The City.
     */
    public City getCityByID(int id) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM city "
                            + "WHERE ID = '" + id + "'";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a new city with the result values
            City myCity = new City();
            if(rset.next()) {
                myCity.id = rset.getInt("ID");
                myCity.name = rset.getString("Name");
                myCity.countryCode = rset.getString("CountryCode");
                myCity.district = rset.getString("District");
                myCity.population = rset.getInt("Population");
            }

            return myCity;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city by ID");
            return null;
        }
    }   // METHOD getCityByID()

    /**
     * Prints a all values of a single City.
     * @param city The city to print.
     */
    private void displayCity(City city) {
        // For each city in the list
        System.out.println(city.toString());
    }

    /**
     * Prints a list of cities.
     * @param cities The list of cities to print.
     */
    private void printCitiesReport(ArrayList<City> cities, String reportTitle) {
        if(cities.isEmpty()) {
            System.out.println("Failed to print " + reportTitle +" report: city list is empty.");
        } else {
            // Print report header
            System.out.println("LIST OF " + reportTitle);
            System.out.printf("%-5s %-35s %-52s %-11s\n",
                    "No", "Name", "Country","Population");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------"
            );
            int i = 1;
            // Print each city in the list
            for (City c : cities) {
                System.out.printf("%-5s %-35s %-52s %-11d\n",
                        i + ".", c.name, getCountryByCode(c.countryCode).name, c.population
                );
                i++;
            }
        }
    }

    // CAPITAL CITIES METHODS ---------------------------------------------------------------------------

    /**
     * Gets all the capital cities from the world MySQL database.
     * @return A list of all capital cities in database, or null if there is an error.
     */
    public ArrayList<City> getAllCapitalCities() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM city, country "
                            + "WHERE city.ID = country.Capital "
                            + "ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create list for capital cities
            ArrayList<City> capitalCities = new ArrayList<>();
            while(rset.next()) {
                City myCity = new City(
                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")
                );
                capitalCities.add(myCity);
            }
            return capitalCities;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital cities");
            return null;
        }
    } // METHOD getAllCapitalCities()

    /**
     * Gets all the capital cities from countries in a given continent.
     * @return A list of all capital cities in a continent, or null if there is an error.
     */
    public ArrayList<City> getCapitalCitiesByContinent(String continent) {
        try {

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM city, country "
                            + "WHERE country.Continent = '" + continent +  "' "
                            + "AND city.ID = country.Capital "
                            + "ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create list for capital cities
            ArrayList<City> capitalCities = new ArrayList<>();
            while(rset.next()) {
                City myCity = new City(
                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")
                );
                capitalCities.add(myCity);
            }
            return capitalCities;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital cities by continent");
            return null;
        }
    } // METHOD getCapitalCitiesByContinent()

    /**
     * Gets all the capital cities from countries in a given region.
     * @return A list of all capital cities in a region, or null if there is an error.
     */
    public ArrayList<City> getCapitalCitiesByRegion(String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM city, country "
                            + "WHERE country.Region = '" + region +  "' "
                            + "AND city.ID = country.Capital "
                            + "ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create list for capital cities
            ArrayList<City> capitalCities = new ArrayList<>();
            while(rset.next()) {
                City myCity = new City(
                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")
                );
                capitalCities.add(myCity);
            }
            return capitalCities;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital cities by region");
            return null;
        }
    } // METHOD getCapitalCitiesByRegion()

    /**
     * Gets the top N populated capital cities from the world MySQL database.
     * @return A list of the top N populated in database, or null if there is an error.
     */
    public ArrayList<City> getTopNCapitalCities(int n) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM city, country "
                            + "WHERE city.ID = country.Capital "
                            + "ORDER BY city.Population DESC "
                            + "LIMIT " + n;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create list for capital cities
            ArrayList<City> capitalCities = new ArrayList<>();
            while(rset.next()) {
                City myCity = new City(
                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")
                );
                capitalCities.add(myCity);
            }
            return capitalCities;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get top N capital cities");
            return null;
        }
    } // METHOD getTopNCapitalCities()

    /**
     * Gets the top N populated in a given continent.
     * @return A list of the top N populated in a given continent, or null if there is an error.
     */
    public ArrayList<City> getTopNCapitalCitiesByContinent(int n, String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM city, country "
                            + "WHERE country.Continent = '" + continent +  "' "
                            + "AND city.ID = country.Capital "
                            + "ORDER BY city.Population DESC "
                            + "LIMIT " + n;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create list for capital cities
            ArrayList<City> capitalCities = new ArrayList<>();
            while(rset.next()) {
                City myCity = new City(
                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")
                );
                capitalCities.add(myCity);
            }
            return capitalCities;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get top N capital cities by continent");
            return null;
        }
    } // METHOD getTopNCapitalCitiesByContinent()

    /**
     * Gets the top N populated in a given region.
     * @return A list of the top N populated in a given region, or null if there is an error.
     */
    public ArrayList<City> getTopNCapitalCitiesByRegion(int n, String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM city, country "
                            + "WHERE country.Region = '" + region +  "' "
                            + "AND city.ID = country.Capital "
                            + "ORDER BY city.Population DESC "
                            + "LIMIT " + n;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create list for capital cities
            ArrayList<City> capitalCities = new ArrayList<>();
            while(rset.next()) {
                City myCity = new City(
                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")
                );
                capitalCities.add(myCity);
            }
            return capitalCities;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get top N capital cities by region");
            return null;
        }
    } // METHOD getTopNCapitalCitiesByRegion()

    // COUNTRIES METHODS -----------------------------------------------------------------------------

    /**
     * Gets all the countries from the world MySQL database.
     * @return A list of all countries in database, or null if there is an error.
     */
    public ArrayList<Country> getAllCountries() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM country "
                            + "ORDER BY Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a List for the countries
            ArrayList<Country> countries = new ArrayList<>();
            // While there are more countries in the result set
            while (rset.next()) {
                // Create a new country with the values in the result set
                Country myCountry = new Country(
                        rset.getString("Code"),
                        rset.getString("Name"),
                        rset.getString("Continent"),
                        rset.getString("Region"),
                        rset.getFloat("SurfaceArea"),
                        rset.getInt("IndepYear"),
                        rset.getInt("Population"),
                        rset.getDouble("LifeExpectancy"),
                        rset.getFloat("GNP"),
                        rset.getFloat("GNPOld"),
                        rset.getString("LocalName"),
                        rset.getString("GovernmentForm"),
                        rset.getString("HeadOfState"),
                        rset.getInt("Capital"),
                        rset.getString("Code2")
                );
                // Add country to the list
                countries.add(myCountry);
            }
            return countries;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries");
            return null;
        }
    } // METHOD getAllCountries()

    /**
     * Gets all the countries in a given continent.
     * @param continent A string which contains the name of the continent.
     * @return A list of all countries in a continent, or null if there is an error.
     */
    public ArrayList<Country> getCountriesByContinent(String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM country "
                            + "WHERE Continent = '" + continent +  "' "
                            + "ORDER BY Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a List for the countries
            ArrayList<Country> countries = new ArrayList<>();
            while(rset.next()) {
                Country myCountry = new Country(
                        rset.getString("Code"),
                        rset.getString("Name"),
                        rset.getString("Continent"),
                        rset.getString("Region"),
                        rset.getFloat("SurfaceArea"),
                        rset.getInt("IndepYear"),
                        rset.getInt("Population"),
                        rset.getDouble("LifeExpectancy"),
                        rset.getFloat("GNP"),
                        rset.getFloat("GNPOld"),
                        rset.getString("LocalName"),
                        rset.getString("GovernmentForm"),
                        rset.getString("HeadOfState"),
                        rset.getInt("Capital"),
                        rset.getString("Code2")
                );
                // Add country to the list
                countries.add(myCountry);
            }
            return countries;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries by continent");
            return null;
        }
    } // METHOD getCountriesByContinent()

    /**
     * Gets the Country from the world MySQL database which has a given code.
     * @param code the code of the Country we want to get.
     * @return The Country.
     */
    public Country getCountryByCode(String code) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM country "
                            + "WHERE Code = '" + code + "'";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a new country with the result values
            Country myCountry = new Country();
            if(rset.next()) {
                myCountry.code = rset.getString("Code");
                myCountry.name = rset.getString("Name");
                myCountry.continent = Continent.toContinent(rset.getString("Continent"));
                myCountry.region = rset.getString("Region");
                myCountry.surfaceArea = rset.getFloat("SurfaceArea");
                myCountry.indepYear = rset.getInt("IndepYear");
                myCountry.population = rset.getInt("Population");
                myCountry.lifeExpectancy = rset.getDouble("LifeExpectancy");
                myCountry.gnp = rset.getFloat("GNP");
                myCountry.gnpOld = rset.getFloat("GNPOld");
                myCountry.localName = rset.getString("LocalName");
                myCountry.governmentForm = rset.getString("GovernmentForm");
                myCountry.headOfState = rset.getString("HeadOfState");
                myCountry.capital = rset.getInt("Capital");
                myCountry.code2 = rset.getString("Code2");
            }
            return myCountry;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country by code");
            return null;
        }
    } // METHOD getCountryByCode()

    /**
     * Gets the Country Code from the world MySQL database which has a given Name.
     * @param name the name of the Country we want to get the code of.
     * @return The code.
     */
    public String getCountryCodeByName(String name) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Code "
                            + "FROM country "
                            + "WHERE Name = '" + name + "'";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create the result var
            String code = null;
            if(rset.next()) {
                code = rset.getString("Code");
            }
            return code;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country code by name");
            return null;
        }
    } // METHOD getCountryCodeByName()

    /**
     * Prints a all values of a single Country.
     * @param country The country to print.
     */
    private void displayCountry(Country country) {
        // For each city in the list
        System.out.println(country.toString());
    }

    /**
     * Prints a list of countries.
     * @param countries The list of cities to print.
     */
    private void printCountries(ArrayList<Country> countries) {
        // For each city in the list
        for (Country c : countries) {
            System.out.println(String.format("%s %s %d", c.name, c.continent, c.population));
        }
    }

} // CLASS App