package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;
    //simple newline character for printing java strings
    public static String newLine = System.getProperty("line.separator");

    public static void main(String[] args) {


        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Get all cities in database
        ArrayList<City> cities = a.getAllCities();
        // Print results for all cities
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine+
                "The following is a list of all cities in the database"+
                newLine+
                "-------------------------------------------------------------------------------"+
                newLine);
        a.printCities(cities);
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine);

        // Get all countries in database
        ArrayList<Country> countries = a.getAllCountries();
        // Print results for all countries
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine+
                "The following is a list of all countries in the database"+
                newLine+
                "-------------------------------------------------------------------------------"+
                newLine);
        a.printCountries(countries);
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine);

        //Get all countries by Continent
        ArrayList<Country> countriesByContinent = a.getCountriesByContinent();
        //print results for all countries organsied by continent
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine+
                "The following is a list of all countries in the database organised by continent "+
                "in ascending alphabetical order"+
                newLine+
                "-------------------------------------------------------------------------------"+
                newLine);
        a.printCountriesByContinent(countriesByContinent);
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine);




        //Get all countries by Population
        ArrayList<Country> countriesByPop = a.getCountriesByPop();
        //print results for all countries organsied by population
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of all countries in the database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine);
        a.printCountriesByPop(countriesByPop);
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);




        //Get all countries by Region
        ArrayList<Country> countriesByRegion = a.getCountriesByRegion();
        //print results for all countries organsied by Region
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine+
                "The following is a list of all countries in the database organised by Region "+
                "in ascending alphabetical order"+
                newLine+
                "-------------------------------------------------------------------------------"+
                newLine);
        a.printCountriesByRegion(countriesByRegion);
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine);


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
                    "SELECT Name "
                        + "FROM city ";

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
     * Prints a list of cities.
     * @param cities The list of cities to print.
     */
    private void printCities(ArrayList<City> cities) {
        // For each city in the list
        for (City c : cities) {
            System.out.println(String.format("%s", c.name));
        }
    }

    /* Gets all the countries from the world MySQL database.

     * @return A list of all countries in database, or null if there is an error.

     */
    //method to get a list of all countries in the database
    public ArrayList<Country> getAllCountries()
    {
        try
        {
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT Name FROM country";
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the countries

            ArrayList<Country> countries = new ArrayList<>();

            // While there are more countries in the result set

            while (rset.next())
            {

                // Create a new country

                Country myCountry = new Country();

                // Initialize with the values in the result set

                myCountry.name = rset.getString("Name");

                // Add country to the list

                countries.add(myCountry);

            }

            return countries;

        }

        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get countries");

            return null;

        }

    } // METHOD getAllCountries()

    /* Prints a list of countries.
        /** @param countries The list of countries to print.
        /**/
    private void printCountries(ArrayList<Country> countries)
    {
        // For each country in the list
        for (Country co : countries)
        {
            System.out.println(String.format("%s", co.name));

        }
    }


    /* Gets all the countries from the world MySQL database organised by highest population to lowest.

     * @return A list of all countries in database, or null if there is an error.

     */
    //method to get a list of all countries in the database from highest population to lowest
    public ArrayList<Country> getCountriesByPop()
    {
        try
        {
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT Name, Population "
                        + "FROM country "
                        + "ORDER BY Population DESC";

            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the countries

            ArrayList<Country> countries = new ArrayList<>();

            // While there are more countries in the result set

            while (rset.next())
            {

                // Create a new country

                Country myCountry = new Country();

                // Initialize with the values in the result set

                myCountry.name = rset.getString("Name");
                myCountry.population = rset.getInt("Population");

                // Add country to the list

                countries.add(myCountry);

            }

            return countries;

        }

        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get countries ordered by population");

            return null;

        }

    } // METHOD countriesByPop()

    /* Prints a list of countries ordered by Population.
    /** @param countries The list of countries to print.
    /**/
    private void printCountriesByPop(ArrayList<Country> countries)
    {
        // For each country in the list
        for (Country co : countries)
        {
            //%-40s
            System.out.println(String.format("%-50s%s", co.name,co.population));

        }
    }

    /* Gets all the countries by continent from the world MySQL database.

     * @return A list of all countries in database, organised by continent, or null if there is an error.

     */
    //method to get a list of all countries and Continents in the database


    public ArrayList<Country> getCountriesByContinent()
    {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Continent  "
                            + "FROM country "
                            + "ORDER BY Continent ASC, Name ASC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a Lists for results
            ArrayList<Country> countries = new ArrayList<>();

            // While there are more results in set
            while (rset.next()) {

                // Create a new country
                Country myCountry = new Country();

                // Initialize with the values in the result set
                myCountry.name = rset.getString("Name");
                myCountry.continent = rset.getString("Continent");
                // Add country to the list
                countries.add(myCountry);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(
                    "-------------------------------------------------------------------------------"+
                    newLine+
                    "*********Failed to get countries ordered by continent*********"+
                    newLine+
                    "-------------------------------------------------------------------------------"
            );
            return null;
        }
    }

    /* Prints a list of countries ordered by Continent.
    /** @param countries The list of countries to print.
    /**/
    private void printCountriesByContinent(ArrayList<Country> countries)
    {
        // For each country in the list
        for (Country co : countries)
        {
            //%-40s
            System.out.println(String.format("%-50s%s", co.continent,co.name));

        }
    }


    /* Gets all the countries by Region from the world MySQL database.

     * @return A list of all countries in database, organised by region, or null if there is an error.

     */
    //method to get a list of all countries and Continents in the database
    public ArrayList<Country> getCountriesByRegion()
    {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Region  "
                            + "FROM country "
                            + "ORDER BY Region ASC, Name ASC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a Lists for results
            ArrayList<Country> countries = new ArrayList<>();

            // While there are more results in set
            while (rset.next()) {

                // Create a new country
                Country myCountry = new Country();

                // Initialize with the values in the result set
                myCountry.name = rset.getString("Name");
                myCountry.region = rset.getString("Region");
                // Add country to the list
                countries.add(myCountry);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(
                    "-------------------------------------------------------------------------------"+
                    newLine+
                    "*********Failed to get countries ordered by Region*********"+
                    newLine+
                    "-------------------------------------------------------------------------------"
            );
            return null;
        }
    }

    /* Prints a list of countries ordered by Region.
    /** @param countries The list of countries to print.
    /**/
    private void printCountriesByRegion(ArrayList<Country> countries)
    {
        // For each country in the list
        for (Country co : countries)
        {
            //%-40s
            System.out.println(String.format("%-50s%s", co.region,co.name));

        }
    }




} // CLASS App