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

        String ContinentInput = "AFRICA";

        //Get all countries by Population
        //ArrayList<Country> countriesByPop = a.getAllCountriesByPop();

        //print results for all countries organised by population
        //a.printCountriesByPop(countriesByPop);


        //Get all countries in a continent by Population
        ArrayList<Country> countriesInContinentByPop = a.getCountriesInContinentByPop(ContinentInput);

        //print results for all countries in a continent organised by population
        a.printCountriesInContinentByPop(countriesInContinentByPop, ContinentInput);



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

    //-----------------------------------------------------------------------------------------------------------
    /* Gets all the countries from the world MySQL database.

     * @return A list of all countries in database, or null if there is an error.

     */
    //method to get a list of all countries in the database
    public ArrayList<Country> getAllCountriesByPop()
    {
        try {
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT * "
                            +"FROM country "
                            +"WHERE Continent = *"
                            +"ORDER BY Population DESC";
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the countries

            ArrayList<Country> countries = new ArrayList<>();

            // While there are more countries in the result set

            while (rset.next()) {

                // Create a new country

                Country myCountry = new Country(

                        // Initialize with the values in the result set

                rset.getString("Code"),
                rset.getString("Name"),
                rset.getString("Continent"),
                rset.getString("Region"),
                rset.getFloat("SurfaceArea"),
                rset.getInt("IndepYear"),
                rset.getInt("Population"),
                rset.getInt("LifeExpectancy"),
                rset.getFloat("GNP"),
                rset.getFloat("GNPOld"),
                rset.getString("LocalName"),
                rset.getString("GovernmentForm"),
                rset.getString("HeadOfState"),
                rset.getInt("Capital"),
                rset.getString("Code2")
                );
                //add country to list
                countries.add(myCountry);
            }
            return countries;
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get countries by population");

            return null;

        }

    } // METHOD getAllCountriesByPop()

    /* Prints a list of countries.
        /** @param countries The list of countries to print.
        /**/
    private void printCountriesByPop(ArrayList<Country> countries)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine+
                "The following is a list of all countries in the database organised by population "+
                "in descending numerical order"+
                newLine+
                "-------------------------------------------------------------------------------"+
                newLine);

        // For each country in the list
        for (Country co : countries)
        {
            System.out.println(String.format("%-60s%d", co.name, co.population));
        }

        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine);
    }
//-----------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------
    /* Gets all the countries from a continent in the world MySQL database and order by population.

     * @return A list of all countries in database, or null if there is an error.
     * @param Continent to find countries for only selected continent

     */
    //method to get a list of countries in a continent ordered by population
    public ArrayList<Country> getCountriesInContinentByPop(String continent)
    {
        try
        {
            //Transform parameter into Continent object
            Continent myContinent = Continent.toContinent(continent);

            //create an sql statement
            Statement stmt = con.createStatement();

            //SQL Statement
            String strSelect =
                    "SELECT * "
                    +"FROM country "
                    +"WHERE Continent = '" +myContinent.getName() + "' "
                    +"ORDER BY Population DESC";
            //Execute statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the countries

            ArrayList<Country> countries = new ArrayList<>();

            // While there are more countries in the result set

            while (rset.next()) {

                // Create a new country

                Country myCountry = new Country(

                        // Initialize with the values in the result set

                        rset.getString("Code"),
                        rset.getString("Name"),
                        rset.getString("Continent"),
                        rset.getString("Region"),
                        rset.getFloat("SurfaceArea"),
                        rset.getInt("IndepYear"),
                        rset.getInt("Population"),
                        rset.getInt("LifeExpectancy"),
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
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get countries in continent by population");

            return null;

        }

    }


    /* Prints a list of countries.
       /** @param countries The list of countries to print.
       /**/
    private void printCountriesInContinentByPop(ArrayList<Country> countries, String ContinentInput)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine+
                "The following is a list of all countries in a continent in the database organised by population "+
                "in descending numerical order"+
                newLine+
                "-------------------------------------------------------------------------------"+
                newLine+
                "Countries in "+ContinentInput+ ":"+
                newLine);

        // For each country in the list
        for (Country co : countries)
        {
            System.out.println(String.format("%-60s%d", co.name, co.population));

        }
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine);
    }
//-----------------------------------------------------------------------------------------------------------





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




} // CLASS App