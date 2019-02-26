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
        // Get all capital cities in database
        ArrayList<City> cities = a.getAllCapitalCities();
        // Print results
        a.printCities(cities);

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

    // CITIES METHODS

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
     *
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
     * Prints a list of cities.
     * @param cities The list of cities to print.
     */
    private void printCities(ArrayList<City> cities) {
        // For each city in the list
        for (City c : cities) {
            System.out.println(String.format("%s %d", c.name, c.population));
        }
    }

    // CAPITAL CITIES METHODS

    /**
     * Gets all the capital cities from the world MySQL database.
     *
     * @return A list of all capital cities in database, or null if there is an error.
     */
    public ArrayList<City> getAllCapitalCities() {
        try {
            ArrayList<Country> countries = getAllCountries();
            ArrayList<City> capitalCities = new ArrayList<>();

            for(Country c : countries) {
                capitalCities.add(
                        getCityByID(c.getCapital())
                );
            }
            return capitalCities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital cities");
            return null;
        }
    } // METHOD getAllCapitalCities()

    // COUNTRIES METHODS

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
     * Gets the Country from the world MySQL database which has a given code.
     *
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

            // Print query
            System.out.println(strSelect);
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a new country with the result values
            Country myCountry = new Country();
            if(rset.next()) {
                myCountry.code = rset.getString("Code");
                myCountry.name = rset.getString("Name");
                myCountry.continent = rset.getString("Continent");
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

} // CLASS App