package com.napier.sem;


import java.sql.*;
import java.util.ArrayList;



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
        String RegionInput = "Central Africa";
        Integer TopNCountries = 5;
        Integer TopNCitites = 10;
        String CountryInput = "Germany";
        String DistrictInput = "Buenos Aires";

        //Get all countries by Population
        ArrayList<Country> countriesByPop = a.getAllCountriesByPop();

        //print results for all countries organised by population
        a.printCountriesByPop(countriesByPop);


        //Get all countries in a continent by Population
        ArrayList<Country> countriesInContinentByPop = a.getCountriesInContinentByPop(ContinentInput);

        //print results for all countries in a continent organised by population
        a.printCountriesInContinentByPop(countriesInContinentByPop, ContinentInput);



        //Get all countries in Region by population
        ArrayList<Country> countriesInRegionByPop = a.getCountriesInRegionByPop(RegionInput);

        //print results for all countries in Region organised by population
        a.printCountriesInRegionByPop(countriesInRegionByPop, RegionInput);


        //Get top N countries by population
        //ArrayList<Country> topCountriesByPop = a.getTopCountriesByPop(TopNCountries);

        //print results for top n countries by population
        //a.printTopCountriesByPop(topCountriesByPop, TopNCountries);


        //Get top N countries in a continent by population
        ArrayList<Country> topCountriesInContinentByPop = a.getTopCountriesInContinentByPop(ContinentInput, TopNCountries);

        //print results for top n countries in Continent organised by population
        a.printTopCountriesInContinentByPop(topCountriesInContinentByPop, ContinentInput, TopNCountries);


        //Get top N countries in a region by population
        ArrayList<Country> topCountriesInRegionByPop = a.getTopCountriesInRegionByPop(RegionInput, TopNCountries);

        //print results for top n countries in Region organised by population
        a.printTopCountriesInRegionByPop(topCountriesInRegionByPop, RegionInput, TopNCountries);

        //Get all cities by Population
        ArrayList<City> allCitiesByPop = a.getAllCitiesByPop();

        //print results for all cities organised by population
        a.printAllCitiesByPop(allCitiesByPop);


        //Get all cities in a continent by Population
        ArrayList<City> allCitiesInContinentByPop = a.getAllCitiesInContinentByPop(ContinentInput);

        //print results for all cities organised by population
        a.printAllCitiesInContinentByPop(allCitiesInContinentByPop, ContinentInput);


        //Get all cities in a continent by Population
        ArrayList<City> allCitiesInRegionByPop = a.getAllCitiesInRegionByPop(RegionInput);

        //print results for all cities organised by population
        a.printAllCitiesInRegionByPop(allCitiesInRegionByPop, RegionInput);

        //Get all cities in a country by Population
        ArrayList<City> allCitiesInCountryByPop = a.getAllCitiesInCountryByPop(CountryInput);

        //print results for all cities in a country organised by population
        a.printAllCitiesInCountryByPop(allCitiesInCountryByPop, CountryInput);


        //Get all cities in a District by Population
        ArrayList<City> allCitiesInDistrictByPop = a.getAllCitiesInDistrictByPop(DistrictInput);

        //print results for all cities in a country organised by population
        a.printAllCitiesInDistrictByPop(allCitiesInDistrictByPop, DistrictInput);

        //Get top n cities by Population
        ArrayList<City> topCitiesByPop = a.getTopCitiesByPop(TopNCitites);

        //print results for top n cities organised by population
        a.printTopCitiesByPop(topCitiesByPop, TopNCitites);


        //Get top n cities in a continent by Population
        ArrayList<City> topCitiesInContinentByPop = a.getTopCitiesInContinentByPop(ContinentInput, TopNCitites);

        //print results for top n cities organised by population
        a.printTopCitiesInContinentByPop(topCitiesInContinentByPop, ContinentInput, TopNCitites);


        //Get top n cities in a continent by Population
        ArrayList<City> topCitiesInRegionByPop = a.getTopCitiesInRegionByPop(RegionInput, TopNCitites);

        //print results for top n cities organised by population
        a.printTopCitiesInRegionByPop(topCitiesInRegionByPop, RegionInput, TopNCitites);

        //Get top n cities in a country by Population
        ArrayList<City> topCitiesInCountryByPop = a.getTopCitiesInCountryByPop(CountryInput, TopNCitites);

        //print results for top n cities in a country organised by population
        a.printTopCitiesInCountryByPop(topCitiesInCountryByPop, CountryInput, TopNCitites);


        //Get top n cities in a District by Population
        ArrayList<City> topCitiesInDistrictByPop = a.getTopCitiesInDistrictByPop(DistrictInput, TopNCitites);

        //print results for top n cities in a country organised by population
        a.printTopCitiesInDistrictByPop(topCitiesInDistrictByPop, DistrictInput, TopNCitites);

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
    void printCountriesByPop(ArrayList<Country> countries)
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


    /* Prints a list of countries in a continent
       /** @param countries The list of countries to print, string the Continent to search.
       /**/
    void printCountriesInContinentByPop(ArrayList<Country> countries, String ContinentInput)
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

//-----------------------------------------------------------------------------------------------------------

    /* Gets all the countries in a Region ordered by population from the world MySQL database.

     * @return A list of all countries in database, organised by region, or null if there is an error.

     */
    //method to get a list of all countries ina region in the database
    public ArrayList<Country> getCountriesInRegionByPop(String region)
    {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM country "
                            + "WHERE Region = '"+region+"' "
                            + "ORDER BY Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a Lists for results
            ArrayList<Country> countries = new ArrayList<>();

            // While there are more results in set
            while (rset.next())
            {
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

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine+
                "*********Failed to get countries in a region ordered by population*********"+
                newLine+
                "-------------------------------------------------------------------------------"
            );
            return null;
        }
    }

    void printCountriesInRegionByPop(ArrayList<Country> countries, String RegionInput)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                newLine+
                "The following is a list of all countries in a region in the database organised by population "+
                "in descending numerical order"+
                newLine+
                "-------------------------------------------------------------------------------"+
                newLine+
                "Countries in "+ RegionInput + ":"+
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

    /* Gets top n countries from the world MySQL database and orders by population.

     * @return A list of top n countries  in the database, or null if there is an error.
     * @param Integer for top N countries

     */
    //method to get a list of countries in a continent ordered by population
    public ArrayList<Country> getTopCountriesByPop( Integer TopNCountries)
    {
        try
        {
            //create an sql statement
            Statement stmt = con.createStatement();

            //SQL Statement
            String strSelect =
                    "SELECT * "
                            +"FROM country "
                            +"ORDER BY Population DESC "
                            +"LIMIT "+ TopNCountries;
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

            System.out.println("Failed to get  top "+ TopNCountries +" countries  by population");

            return null;

        }

    }


    /* Prints a list of top n countries in world
       /** @param countries The list of countries to print, Integer for number of top Countries.
       /**/
    void printTopCountriesByPop(ArrayList<Country> countries, Integer TopNCountries)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of the top "+ TopNCountries+" countries in the database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "The top "+TopNCountries+" populated countries are:"+
                        newLine);

        Integer topCount = 0;
        // For each country in the list
        for (Country co : countries)
        {
            topCount++;
            System.out.println(String.format("%d %-80s%-60d", topCount, co.name, co.population));

        }
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }

    //-----------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------


    /* Gets top n countries from a continent in the world MySQL database and order by population.

     * @return A list of top n countries in a continent in the database, or null if there is an error.
     * @param Continent to find top n countries for only selected continent

     */
    //method to get a list of countries in a continent ordered by population
    public ArrayList<Country> getTopCountriesInContinentByPop(String continent, Integer TopNCountries)
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
                            +"ORDER BY Population DESC "
                            +"LIMIT "+ TopNCountries;
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

            System.out.println("Failed to get  top "+ TopNCountries +" countries in continent by population");

            return null;

        }

    }


    /* Prints a list of countries in a continent
       /** @param countries The list of countries to print, string the Continent to search.
       /**/
    void printTopCountriesInContinentByPop(ArrayList<Country> countries, String ContinentInput, Integer TopNCountries)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of the top "+ TopNCountries+" countries in "+ContinentInput+" in the database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "The top "+TopNCountries+" populated countries in "+ContinentInput+" are:"+
                        newLine);

        Integer topCount = 0;
        // For each country in the list

        for (Country co : countries)
        {
            topCount++;
            System.out.println(String.format("%d %-60s%d", topCount, co.name, co.population));

        }
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }

    //-----------------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------------------------

    /* Gets top n countries from a region in the world MySQL database and order by population.

     * @return A list of top n countries in a region in the database, or null if there is an error.
     * @param Continent to find top n countries for only selected continent

     */
    //method to get a list of countries in a region ordered by population
    public ArrayList<Country> getTopCountriesInRegionByPop(String myRegion, Integer TopNCountries)
    {
        try
        {
            //create an sql statement
            Statement stmt = con.createStatement();

            //SQL Statement
            String strSelect =
                    "SELECT * "
                            +"FROM country "
                            +"WHERE Region = '" +myRegion+ "' "
                            +"ORDER BY Population DESC "
                            +"LIMIT "+ TopNCountries;
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

            System.out.println("Failed to get  top "+ TopNCountries +" countries in Region-"+ myRegion+ " by population");

            return null;

        }

    }


    /* Prints a list of countries in a region
       /** @param countries The list of countries to print, string the Region to search.
       /**/
    void printTopCountriesInRegionByPop(ArrayList<Country> countries, String RegionInput, Integer TopNCountries)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of the top "+ TopNCountries+" countries in Region-"+RegionInput+" in the database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "The top "+TopNCountries+" populated countries in "+RegionInput+" are:"+
                        newLine);

        Integer topCount = 0;
        // For each country in the list
        for (Country co : countries)
        {
            topCount++;
            System.out.println(String.format("%d %-60s%d", topCount, co.name, co.population));

        }
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }

    //-----------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------
    /* Gets all the cities from the world MySQL database.

     * @return A list of all cities in database, or null if there is an error.

     */
    //method to get a list of all cities in the database
    public ArrayList<City> getAllCitiesByPop()
    {
        try {
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT * "
                            +"FROM city "
                            +"ORDER BY Population DESC";
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities

            ArrayList<City> cities = new ArrayList<>();

            // While there are more cities in the result set

            while (rset.next()) {

                // Create a new country

                City myCity = new City(

                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")

                );
                //add city to list
                cities.add(myCity);
            }
            return cities;
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get cities by population");

            return null;

        }

    } // METHOD getAllCitiesByPop()

    /* Prints a list of cities.
        /** @param cities The list of cities to print.
        /**/
    void printAllCitiesByPop(ArrayList<City> cities)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of all cities in the database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine);

        // For each country in the list
        for (City ci : cities)
        {
            System.out.println(String.format("%-60s%d", ci.name, ci.population));
        }

        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }
//-----------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------
    /* Gets all the cities in a continent from the world MySQL database.

     * @return A list of all cities in database, or null if there is an error.

     */
    //method to get a list of all cities in a continent in the database
    public ArrayList<City> getAllCitiesInContinentByPop(String continentInput)
    {
        try {
            //Transform parameter into Continent object
            Continent myContinent = Continent.toContinent(continentInput);
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT city.*, country.Continent "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode=country.Code "
                            +"WHERE country.Continent = '"+myContinent+"' "
                            +"ORDER BY city.Population DESC";
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities

            ArrayList<City> cities = new ArrayList<>();

            // While there are more cities in the result set

            while (rset.next()) {

                // Create a new country

                City myCity = new City(

                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")

                );
                //add city to list
                cities.add(myCity);
            }
            return cities;
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get cities in continent by population");

            return null;

        }

    } // METHOD getAllCitiesInContinentByPop()

    /* Prints a list of cities.
        /** @param cities The list of cities to print.
        /**/
    void printAllCitiesInContinentByPop(ArrayList<City> cities, String continentInput)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of all cities in "+continentInput+" from the world database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "These are the cities in "+continentInput+" organised from highest population to lowest:    "+
                        newLine
        );

        // For each country in the list
        for (City ci : cities)
        {
            System.out.println(String.format("%-60s%d", ci.name, ci.population));
        }

        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }
//-----------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------
    /* Gets all the cities from a Region in the world MySQL database.

     * @return A list of all cities in a Region in the database, or null if there is an error.

     */
    //method to get a list of all cities in the Region in the database
    public ArrayList<City> getAllCitiesInRegionByPop(String regionInput)
    {
        try {
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT city.*, country.Region "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode=country.Code "
                            +"WHERE country.Region = '"+regionInput+"' "
                            +"ORDER BY city.Population DESC";
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities

            ArrayList<City> cities = new ArrayList<>();

            // While there are more cities in the result set

            while (rset.next()) {

                // Create a new country

                City myCity = new City(

                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")

                );
                //add city to list
                cities.add(myCity);
            }
            return cities;
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get cities in Region by population");

            return null;

        }

    } // METHOD getAllCitiesInRegionByPop()

    /* Prints a list of cities.
        /** @param cities The list of cities to print.
        /**/
    void printAllCitiesInRegionByPop(ArrayList<City> cities, String regionInput)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of all cities in region-"+regionInput+" from the world database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "These are the cities in region-"+regionInput+" organised from highest population to lowest:    "+
                        newLine
        );

        // For each country in the list
        for (City ci : cities)
        {
            System.out.println(String.format("%-60s%d", ci.name, ci.population));
        }

        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }
//-----------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------
    /* Gets all the cities from a country in the world MySQL database.

     * @return A list of all cities in a country in the database, or null if there is an error.

     */
    //method to get a list of all cities in the country in the database
    public ArrayList<City> getAllCitiesInCountryByPop(String countryInput)
    {
        try {
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT city.*, country.Name "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode=country.Code "
                            +"WHERE country.Name = '"+countryInput+"' "
                            +"ORDER BY city.Population DESC";
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities

            ArrayList<City> cities = new ArrayList<>();

            // While there are more cities in the result set

            while (rset.next()) {

                // Create a new country

                City myCity = new City(

                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")

                );
                //add city to list
                cities.add(myCity);
            }
            return cities;
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get cities in country "+countryInput+" by population");

            return null;

        }

    } // METHOD getAllCitiesInCountryByPop()

    /* Prints a list of cities.
        /** @param cities The list of cities to print.
        /**/
    void printAllCitiesInCountryByPop(ArrayList<City> cities, String countryInput)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of all cities in country-"+countryInput+" from the world database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "These are the cities in country-"+countryInput+" organised from highest population to lowest:    "+
                        newLine
        );

        // For each country in the list
        for (City ci : cities)
        {
            System.out.println(String.format("%-60s%d", ci.name, ci.population));
        }

        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }
//-----------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------
    /* Gets all the cities in a district from the world MySQL database.

     * @return A list of all cities in a district in database, or null if there is an error.

     */
    //method to get a list of all cities in the database
    public ArrayList<City> getAllCitiesInDistrictByPop(String districtInput)
    {
        try {
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT * "
                            +"FROM city "
                            +"WHERE District = '"+districtInput+"' "
                            +"ORDER BY Population DESC";
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities

            ArrayList<City> cities = new ArrayList<>();

            // While there are more cities in the result set

            while (rset.next()) {

                // Create a new country

                City myCity = new City(

                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")

                );
                //add city to list
                cities.add(myCity);
            }
            return cities;
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get cities in district - "+districtInput+" by population");

            return null;

        }

    } // METHOD getAllCitiesInDistrictByPop()

    /* Prints a list of cities.
        /** @param cities The list of cities to print.
        /**/
    void printAllCitiesInDistrictByPop(ArrayList<City> cities, String districtInput)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of all cities in a district in the database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "These are the cities in district - "+districtInput+" organised from highest population to lowest:    "
        );

        // For each country in the list
        for (City ci : cities)
        {
            System.out.println(String.format("%-60s%d", ci.name, ci.population));
        }

        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }
//-----------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------
    /* Gets the top n cities from the world MySQL database.

     * @return A list of the top n cities in database, or null if there is an error.

     */
    //method to get a list of the top cities in the database
    public ArrayList<City> getTopCitiesByPop(Integer numResults)
    {
        try {
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT * "
                            +"FROM city "
                            +"ORDER BY Population DESC "
                            +"LIMIT "+ numResults;
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities

            ArrayList<City> cities = new ArrayList<>();

            // While there are more cities in the result set

            while (rset.next()) {

                // Create a new country

                City myCity = new City(

                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")

                );
                //add city to list
                cities.add(myCity);
            }
            return cities;
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get top "+numResults+" cities in the world by population");

            return null;

        }

    } // METHOD getTopCitiesByPop()

    /* Prints a list of cities.
        /** @param cities The list of cities to print.
        /**/
    void printTopCitiesByPop(ArrayList<City> cities,Integer numResults)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of the top "+numResults+" cities in the database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "Top "+numResults+" cities in the world: "
        );

        Integer topCount = 0;
        // For each country in the list
        for (City ci : cities)
        {
            topCount++;
            System.out.println(String.format("%d %-60s%d",topCount, ci.name, ci.population));
        }

        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }
//-----------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------
    /* Gets the top n the cities in a continent from the world MySQL database.

     * @return A list of the top n cities in database, or null if there is an error.

     */
    //method to get a list of the top n cities in a continent in the database
    public ArrayList<City> getTopCitiesInContinentByPop(String continentInput,Integer numResults)
    {
        try {
            //Transform parameter into Continent object
            Continent myContinent = Continent.toContinent(continentInput);
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT city.*, country.Continent "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode=country.Code "
                            +"WHERE country.Continent = '"+myContinent+"' "
                            +"ORDER BY city.Population DESC "
                            +"LIMIT "+ numResults;
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities

            ArrayList<City> cities = new ArrayList<>();

            // While there are more cities in the result set

            while (rset.next()) {

                // Create a new country

                City myCity = new City(

                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")

                );
                //add city to list
                cities.add(myCity);
            }
            return cities;
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get top N cities in continent by population");

            return null;

        }

    } // METHOD getTopCitiesInContinentByPop()

    /* Prints a list of cities.
        /** @param cities The list of cities to print.
        /**/
    void printTopCitiesInContinentByPop(ArrayList<City> cities, String continentInput, Integer numResults)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of the top "+numResults+" cities in "+continentInput+" from the world database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "These are the top "+numResults+" cities in "+continentInput+" organised from highest population to lowest:    "+
                        newLine
        );

        Integer topCount = 0;
        // For each country in the list
        for (City ci : cities)
        {
            topCount++;
            System.out.println(String.format("%d %-60s%d",topCount, ci.name, ci.population));
        }

        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }
//-----------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------
    /* Gets the top n the cities from a Region in the world MySQL database.

     * @return A list of the top n cities in a Region in the database, or null if there is an error.

     */
    //method to get a list of the top n cities in the Region in the database
    public ArrayList<City> getTopCitiesInRegionByPop(String regionInput,Integer numResults)
    {
        try {
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT city.*, country.Region "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode=country.Code "
                            +"WHERE country.Region = '"+regionInput+"' "
                            +"ORDER BY city.Population DESC "
                            +"LIMIT "+numResults;
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities

            ArrayList<City> cities = new ArrayList<>();

            // While there are more cities in the result set

            while (rset.next()) {

                // Create a new country

                City myCity = new City(

                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")

                );
                //add city to list
                cities.add(myCity);
            }
            return cities;
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get top "+numResults+" cities in Region by population");

            return null;

        }

    } // METHOD getTopCitiesInRegionByPop()

    /* Prints a list of cities.
        /** @param cities The list of cities to print.
        /**/
    void printTopCitiesInRegionByPop(ArrayList<City> cities, String regionInput, Integer numResults)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of the top "+numResults+" cities in region-"+regionInput+" from the world database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "These are the top "+numResults+" cities in region-"+regionInput+" organised from highest population to lowest:    "+
                        newLine
        );

        Integer topCount = 0;
        // For each country in the list
        for (City ci : cities)
        {
            topCount++;
            System.out.println(String.format("%d %-60s%d",topCount, ci.name, ci.population));
        }

        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }
//-----------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------
    /* Gets the top n the cities from a country in the world MySQL database.

     * @return A list of the top n cities in a country in the database, or null if there is an error.

     */
    //method to get a list of the top n cities in the country in the database
    public ArrayList<City> getTopCitiesInCountryByPop(String countryInput,Integer numResults)
    {
        try {
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT city.*, country.Name "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode=country.Code "
                            +"WHERE country.Name = '"+countryInput+"' "
                            +"ORDER BY city.Population DESC "
                            +"LIMIT "+numResults;
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities

            ArrayList<City> cities = new ArrayList<>();

            // While there are more cities in the result set

            while (rset.next()) {

                // Create a new country

                City myCity = new City(

                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")

                );
                //add city to list
                cities.add(myCity);
            }
            return cities;
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get top " +numResults+ " cities in country "+countryInput+" by population");

            return null;

        }

    } // METHOD getTopCitiesInCountryByPop()

    /* Prints a list of cities.
        /** @param cities The list of cities to print.
        /**/
    void printTopCitiesInCountryByPop(ArrayList<City> cities, String countryInput, Integer numResults)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of top "+numResults+" cities in country-"+countryInput+" from the world database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "These are the top "+numResults+" cities in country-"+countryInput+" organised from highest population to lowest:    "+
                        newLine
        );

        Integer topCount = 0;
        // For each country in the list
        for (City ci : cities)
        {
            topCount++;
            System.out.println(String.format("%d %-60s%d", topCount,ci.name, ci.population));
        }

        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }
//-----------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------
    /* Gets the top n the cities in a district from the world MySQL database.

     * @return A list of the top n cities in a district in database, or null if there is an error.

     */
//method to get a list of the top n cities in the database
    public ArrayList<City> getTopCitiesInDistrictByPop(String districtInput,Integer numResults)
    {
        try {
            //Create an SQL Statement
            Statement stmt = con.createStatement();

            //SQL statement
            String strSelect =
                    "SELECT * "
                            +"FROM city "
                            +"WHERE District = '"+districtInput+"' "
                            +"ORDER BY Population DESC "
                            +"LIMIT "+numResults;
            // Execute SQL statement

            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities

            ArrayList<City> cities = new ArrayList<>();

            // While there are more cities in the result set

            while (rset.next()) {

                // Create a new country

                City myCity = new City(

                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")

                );
                //add city to list
                cities.add(myCity);
            }
            return cities;
        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

            System.out.println("Failed to get top "+numResults+" cities in district - "+districtInput+" by population");

            return null;

        }

    } // METHOD getTopCitiesInDistrictByPop()

    /* Prints a list of cities.
        /** @param cities The list of cities to print.
        /**/
    void printTopCitiesInDistrictByPop(ArrayList<City> cities, String districtInput,Integer numResults)
    {
        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine+
                        "The following is a list of top "+numResults+" cities in a district in the database organised by population "+
                        "in descending numerical order"+
                        newLine+
                        "-------------------------------------------------------------------------------"+
                        newLine+
                        "These are the top "+numResults+" cities in district - "+districtInput+" organised from highest population to lowest:    "
        );

        Integer topCount = 0;
        // For each country in the list
        for (City ci : cities)
        {
            topCount++;
            System.out.println(String.format("%d %-60s%d", topCount, ci.name, ci.population));
        }

        System.out.println(
                "-------------------------------------------------------------------------------"+
                        newLine);
    }
//-----------------------------------------------------------------------------------------------------------

} // CLASS App