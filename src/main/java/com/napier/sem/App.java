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

        // Get all cities in database
        //ArrayList<City> cities = a.getAllCities();
        // Print results
        //a.printCities(cities);

        ArrayList<CountryLanguage> languages = a.getLanguageOrdered();

        for (CountryLanguage c : languages) {
            System.out.println(String.format("%s, %f", c.language, c.percentage));
        }

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
     * Gets all the cities from the world MySQL database.
     * @return A list of all cities in database, or null if there is an error.
     */
    public ArrayList<City> getAllCities() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Population "
                        + "FROM city "
                        + "ORDER BY Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a List for the cities
            ArrayList<City> cities = new ArrayList<>();
            // While there are more cities in the result set
            while (rset.next()) {
                // Create a new city
                City myCity = new City();
                // Initialize with the values in the result set
                myCity.name = rset.getString("Name");
                myCity.population = rset.getInt("Population");
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

    public ArrayList<CountryLanguage> getLanguageOrdered() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Language, Percentage "
                            + "FROM countrylanguage "
                            + "ORDER BY Percentage DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a List for the languages
            ArrayList<CountryLanguage> languages = new ArrayList<>();
            // While there are more languages in the result set
            while (rset.next()) {
                // Create a new CountryLanguage
                CountryLanguage myLanguage = new CountryLanguage();
                // Initialize with the values in the result set
                myLanguage.language = rset.getString("Language");
                myLanguage.percentage = rset.getFloat("Percentage");
                // Add language to the list
                languages.add(myLanguage);
            }
            return languages;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Languages");
            return null;
        }
    } // METHOD getLanguageOrdered()

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

} // CLASS App