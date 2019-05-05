package com.napier.sem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;
import java.util.*;

@SpringBootApplication
@RestController
public class App {

    /**
     * Connection to MySQL database.
     */
    private static Connection con = null;

    public static void main(String[] args) {
        // Connect to database
        if (args.length < 1)
        {
            connect("localhost:33060");
        }
        else
        {
            connect(args[0]);
        }

        SpringApplication.run(App.class, args);

    } // METHOD main()

    /**
     * Connect to the MySQL database.
     */
    /**
     * Connect to the MySQL database.
     */
    public static void connect(String location) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
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
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                //con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
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
    public static void disconnect() {
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

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> COUNTRIES METHODS

    /**
     * Gets all the continents from the world MySQL database sorted by population.
     * @return A list of all continents in database, or null if there is an error.
     */
    @RequestMapping("get_all_continents")
    public ArrayList<Continent> getAllContinents() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Continent, SUM(Population) AS sumPopulations "
                            + "FROM country "
                            + "GROUP BY country.Continent "
                            + "ORDER BY sumPopulations DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a List for the countries
            ArrayList<Continent> continents = new ArrayList<>();
            // While there are more countries in the result set
            while (rset.next()) {
                // Create a new country with the values in the result set
                Continent myContinent = Continent.toContinent(
                        rset.getString("Continent")
                );
                // Add country to the list
                continents.add(myContinent);
            }
            return continents;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get continents");
            return null;
        }
    }

    /**
     * Gets all the regions from the world MySQL database sorted by population.
     * @return A list of all regions in database, or null if there is an error.
     */
    @RequestMapping("get_all_regions")
    public ArrayList<String> getAllRegions() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Region, SUM(Population) AS sumPopulations "
                            + "FROM country "
                            + "GROUP BY country.Region "
                            + "ORDER BY sumPopulations DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a List for the countries
            ArrayList<String> regions = new ArrayList<>();
            // While there are more countries in the result set
            while (rset.next()) {
                // Create a new country with the values in the result set
                String myString = rset.getString("Region");
                // Add country to the list
                regions.add(myString);
            }
            return regions;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get regions");
            return null;
        }
    }

    /**
     * Gets all the countries from the world MySQL database.
     * @return A list of all countries in database, or null if there is an error.
     */
    @RequestMapping("get_all_countries")
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
    @RequestMapping("get_countries_by_continent")
    public ArrayList<Country> getCountriesByContinent(@RequestParam(value = "continent") String continent) {
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
     * Gets all the countries in a given region.
     * @param region A string which contains the name of the region.
     * @return A list of all countries in a region, or null if there is an error.
     */
    @RequestMapping("get_countries_by_region")
    public ArrayList<Country> getCountriesByRegion(@RequestParam(value = "region") String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM country "
                            + "WHERE Region = '" + region +  "' "
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
            System.out.println("Failed to get countries by region");
            return null;
        }
    } // METHOD getCountriesByRegion()

    /**
     * Gets the top N populated countries from the world MySQL database.
     * @return A list of the top N populated in database, or null if there is an error.
     */
    @RequestMapping("get_top_n_countries")
    public List<Country> getTopNCountries(@RequestParam(value = "n") String n) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM country "
                            + "ORDER BY Population DESC "
                            + "LIMIT " + n;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create list for countries
            List<Country> countries = new ArrayList<>();
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
            System.out.println("Failed to get top N countries");
            return null;
        }
    } // METHOD getTopNCountries()

    /**
     * Gets the top N populated countries in a given continent.
     * @return A list of the top N populated countries in a given continent, or null if there is an error.
     */
    @RequestMapping("get_top_n_countries_by_continent")
    public List<Country> getTopNCountriesByContinent(@RequestParam(value = "n") String n,
                                                     @RequestParam(value = "continent") String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM country "
                            + "WHERE Continent = '" + continent +  "' "
                            + "ORDER BY Population DESC "
                            + "LIMIT " + n;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create list for countries
            List<Country> countries = new ArrayList<>();
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
            System.out.println("Failed to get top N countries by continent");
            return null;
        }
    } // METHOD getTopNCountriesByContinent()

    /**
     * Gets the top N populated countries in a given continent.
     * @return A list of the top N populated countries in a given continent, or null if there is an error.
     */
    @RequestMapping("get_top_n_countries_by_region")
    public List<Country> getTopNCountriesByRegion(@RequestParam(value = "n") String n,
                                                  @RequestParam(value = "region") String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM country "
                            + "WHERE Region = '" + region +  "' "
                            + "ORDER BY Population DESC "
                            + "LIMIT " + n;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create list for countries
            List<Country> countries = new ArrayList<>();
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
            System.out.println("Failed to get top N countries by region");
            return null;
        }
    } // METHOD getTopNCountriesByRegion()

    /**
     * Gets the Country from the world MySQL database which has a given code.
     * @param code the code of the Country we want to get.
     * @return The Country.
     */
    @RequestMapping("get_country_by_code")
    public Country getCountryByCode(@RequestParam(value = "code") String code) {
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
     * Gets the Country from the world MySQL database which has a given name.
     * @param name the name of the Country we want to get.
     * @return The Country.
     */
    @RequestMapping("get_country_by_name")
    public Country getCountryByName(@RequestParam(value = "name") String name) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM country "
                            + "WHERE Name = '" + name + "'";

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
            System.out.println("Failed to get country by name");
            return null;
        }
    } // METHOD getCountryByName()

    /**
     * Gets the Country Code from the world MySQL database which has a given Name.
     * @param name the name of the Country we want to get the code of.
     * @return The code.
     */
    @RequestMapping("get_country_code_by_name")
    public String getCountryCodeByName(@RequestParam(value = "name") String name) {
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
     * Prints a report of countries.
     * @param countries The list of countries to print.
     */
    public void printCountriesReport(List<Country> countries, String reportTitle) {
        // Check list not empty nor null
        if(countries == null || countries.isEmpty()) {
            System.out.println("Failed to print " + reportTitle +" report.");
            return;
        } else {
            // Print report header
            System.out.println("REPORT ON " + reportTitle);
            System.out.printf("%-5s %-5s %-52s %-15s %-26s %-11s %-11s\n",
                    "No", "Code", "Name", "Continent", "Region", "Population", "Capital");
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------------"
            );
            int i = 1;
            // Print each city in the list
            for (Country c : countries) {
                if(c == null) {
                    continue;
                } else {
                    System.out.printf("%-5s %-5s %-52s %-15s %-26s %-11s %-11s\n",
                            i + ".", c.code, c.name, c.continent.getName(), c.region, c.population, getCityByID(String.valueOf(c.capital)).name
                    );
                    i++;
                }
            }
        }
    }

    /**
     * Generates a list which is a report of countries.
     * @param countries The list of countries to print.
     */
    @RequestMapping("countries_report")
    public List<CountriesReportItem> generateCountriesReport(@RequestParam(value = "list") List<Country> countries) {
        // Check list not empty nor null
        if(countries == null || countries.isEmpty()) {
            System.out.println("Failed to generate report.");
            return null;
        } else {
            // Create a list where store report items
            List<CountriesReportItem> report = new ArrayList<>();
            // Generate a report item for each country and add to report
            for(Country c : countries) {
                if(c == null) {
                    continue;
                } else {
                    CountriesReportItem item = new CountriesReportItem(
                            c.code,
                            c.name,
                            c.continent.getName(),
                            c.region,
                            c.population,
                            getCityByID(Integer.toString(c.capital)).name
                    );
                    report.add(item);
                }
            }
            return report;
        }
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> CITIES METHODS

    /**
     * Gets all the cities from the world MySQL database.
     * @return A list of all cities in database, or null if there is an error.
     */
    @RequestMapping("get_all_cities")
    public List<City> getAllCities() {
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
            List<City> cities = new ArrayList<>();
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
     * Gets all the cities in a given continent.
     * @param continent A string which contains the name of the continent.
     * @return A list of all cities in a continent, or null if there is an error.
     */
    @RequestMapping("get_cities_by_continent")
    public List<City> getCitiesByContinent(@RequestParam(value = "continent") String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.*, country.Continent "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode = country.Code "
                            +"WHERE country.Continent = '" + continent + "' "
                            +"ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a List for the cities
            List<City> cities = new ArrayList<>();
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
            System.out.println("Failed to get cities by continent");
            return null;
        }
    } // METHOD getCitiesByContinent()

    /**
     * Gets all the cities in a given region.
     * @param region A string which contains the name of the region.
     * @return A list of all cities in a region, or null if there is an error.
     */
    @RequestMapping("get_cities_by_region")
    public List<City> getCitiesByRegion(@RequestParam(value = "region") String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.*, country.Region "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode = country.Code "
                            +"WHERE country.Region = '" + region + "' "
                            +"ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a List for the cities
            List<City> cities = new ArrayList<>();
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
            System.out.println("Failed to get cities by region");
            return null;
        }
    } // METHOD getCitiesByRegion()

    /**
     * Gets all the cities in a given country.
     * @param country A string which contains the name of the country.
     * @return A list of all cities in a country, or null if there is an error.
     */
    @RequestMapping("get_cities_by_country")
    public List<City> getCitiesByCountry(@RequestParam(value = "country") String country) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.*, country.Region "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode = country.Code "
                            +"WHERE country.Name = '" + country + "' "
                            +"ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a List for the cities
            List<City> cities = new ArrayList<>();
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
            System.out.println("Failed to get cities by country");
            return null;
        }
    } // METHOD getCitiesByCountry()

    /**
     * Gets all the cities in a given district.
     * @param district A string which contains the name of the district.
     * @return A list of all cities in a district, or null if there is an error.
     */
    @RequestMapping("get_cities_by_district")
    public List<City> getCitiesByDistrict(@RequestParam(value = "district") String district) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            +"FROM city "
                            +"WHERE District = '" + district + "' "
                            +"ORDER BY Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Create a List for the cities
            List<City> cities = new ArrayList<>();
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
            System.out.println("Failed to get cities by district");
            return null;
        }
    } // METHOD getCitiesByDistrict()

    /**
     * Gets the city from the world MySQL database which has a given name.
     * @param name the name of the city we want to get.
     * @return The City.
     */
    @RequestMapping("get_city_by_name")
    public City getCityByName(@RequestParam(value = "name") String name) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM city "
                            + "WHERE Name = '" + name + "'";

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
            System.out.println("Failed to get city by name");
            return null;
        }
    }   // METHOD getCityByName()

    /**
     * Gets the city from the world MySQL database which has a given id code.
     * @param id the code of the city we want to get.
     * @return The City.
     */
    @RequestMapping("get_city_by_id")
    public City getCityByID(@RequestParam(value = "id") String id) {
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
     * Gets the top N populated cities from the world MySQL database.
     * @param n Number of cities to get.
     * @return A list of the top N populated cities in database, or null if there is an error.
     */
    @RequestMapping("get_top_n_cities")
    public List<City> getTopNCities(@RequestParam(value = "n") String n) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM city "
                            + "ORDER BY Population DESC "
                            + "LIMIT " + n;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities
            List<City> cities = new ArrayList<>();
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
            System.out.println("Failed to get top N cities");
            return null;
        }
    } // METHOD getTopNCities()

    /**
     * Gets the top N populated cities in a given continent.
     * @param n Number of cities to get.
     * @param continent A string which contains the name of the continent.
     * @return A list of the top N populated cities in a given continent, or null if there is an error.
     */
    @RequestMapping("get_top_n_cities_by_continent")
    public List<City> getTopNCitiesByContinent(@RequestParam(value = "n") String n,
                                               @RequestParam(value = "continent") String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.*, country.Continent "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode = country.Code "
                            +"WHERE country.Continent = '" + continent + "' "
                            +"ORDER BY city.Population DESC "
                            +"LIMIT "+ n;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities
            List<City> cities = new ArrayList<>();
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
            System.out.println("Failed to get top N cites by continent");
            return null;
        }
    } // METHOD getTopNCitiesByContinent()

    /**
     * Gets the top N populated cities in a given region.
     * @param n Number of cities to get.
     * @param region A string which contains the name of the region.
     * @return A list of the top N populated cities in a given region, or null if there is an error.
     */
    @RequestMapping("get_top_n_cities_by_region")
    public List<City> getTopNCitiesByRegion(@RequestParam(value = "n") String n,
                                            @RequestParam(value = "region") String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.*, country.Continent "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode = country.Code "
                            +"WHERE country.Region = '" + region + "' "
                            +"ORDER BY city.Population DESC "
                            +"LIMIT "+ n;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities
            List<City> cities = new ArrayList<>();
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
            System.out.println("Failed to get top N cites by region");
            return null;
        }
    } // METHOD getTopNCitiesByRegion()

    /**
     * Gets the top N populated cities in a given country.
     * @param n Number of cities to get.
     * @param country A string which contains the name of the country.
     * @return A list of the top N populated cities in a given country, or null if there is an error.
     */
    @RequestMapping("get_top_n_cities_by_country")
    public List<City> getTopNCitiesByCountry(@RequestParam(value = "n") String n,
                                             @RequestParam(value = "country") String country) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.*, country.Continent "
                            +"FROM city "
                            +"JOIN country ON "
                            +"city.CountryCode = country.Code "
                            +"WHERE country.Name = '" + country + "' "
                            +"ORDER BY city.Population DESC "
                            +"LIMIT "+ n;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities
            List<City> cities = new ArrayList<>();
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
            System.out.println("Failed to get top N cites by country");
            return null;
        }
    } // METHOD getTopNCitiesByCountry()

    /**
     * Gets the top N populated cities in a given district.
     * @param n Number of cities to get.
     * @param district A string which contains the name of the district.
     * @return A list of the top N populated cities in a given district, or null if there is an error.
     */
    @RequestMapping("get_top_n_cities_by_district")
    public List<City> getTopNCitiesByDistrict(@RequestParam(value = "n") String n,
                                              @RequestParam(value = "district") String district) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            +"FROM city "
                            +"WHERE District = '" + district + "' "
                            +"ORDER BY city.Population DESC "
                            +"LIMIT "+ n;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Create a List for the cities
            List<City> cities = new ArrayList<>();
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
            System.out.println("Failed to get top N cites by district");
            return null;
        }
    } // METHOD getTopNCitiesByDistrict()

    /**
     * Prints a report of cities.
     * @param cities The list of cities to print.
     */
    public void printCitiesReport(List<City> cities, String reportTitle) {
        if(cities == null || cities.isEmpty()) {
            System.out.println("Failed to print " + reportTitle +" report: cities list is empty.");
        } else {
            // Print report header
            System.out.println("REPORT ON " + reportTitle);
            System.out.printf("%-5s %-35s %-52s %-20s %-11s\n",
                    "No", "Name", "Country", "District", "Population");
            System.out.println(
                    "----------------------------------------------------------------------------------------------------------------------------"
            );
            int i = 1;
            // Print each city in the list
            for (City c : cities) {
                if(c == null) {
                    continue;
                } else {
                    System.out.printf("%-5s %-35s %-52s %-20s %-11d\n",
                            i + ".", c.name, getCountryByCode(c.countryCode).name, c.district, c.population
                    );
                    i++;
                }
            }
        }
    }

    /**
     * Generates a list which is a report of cities.
     * @param cities The list of countries to print.
     */
    @RequestMapping("cities_report")
    public List<CitiesReportItem> generateCitiesReport(@RequestParam(value = "list") List<City> cities) {
        // Check list not empty nor null
        if(cities == null || cities.isEmpty()) {
            System.out.println("Failed to generate report.");
            return null;
        } else {
            // Create a list where store report items
            List<CitiesReportItem> report = new ArrayList<>();
            // Generate a report item for each country and add to report
            for(City c : cities) {
                if(c == null) {
                    continue;
                } else {
                    CitiesReportItem item = new CitiesReportItem(
                            c.name,
                            getCountryByCode(c.countryCode).name,
                            c.district,
                            c.population
                    );
                    report.add(item);
                }
            }
            return report;
        }
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> CAPITAL CITIES METHODS

    /**
     * Gets all the capital cities from the world MySQL database.
     * @return A list of all capital cities in database, or null if there is an error.
     */
    @RequestMapping("get_all_capital_cities")
    public List<City> getAllCapitalCities() {
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
            List<City> capitalCities = new ArrayList<>();
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
    @RequestMapping("get_capital_cities_by_continent")
    public List<City> getCapitalCitiesByContinent(@RequestParam(value = "continent") String continent) {
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
            List<City> capitalCities = new ArrayList<>();
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
    @RequestMapping("get_capital_cities_by_region")
    public List<City> getCapitalCitiesByRegion(@RequestParam(value = "region") String region) {
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
            List<City> capitalCities = new ArrayList<>();
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
    @RequestMapping("get_top_n_capital_cities")
    public List<City> getTopNCapitalCities(@RequestParam(value = "n") String n) {
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
            List<City> capitalCities = new ArrayList<>();
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
    @RequestMapping("get_top_n_capital_cities_by_continent")
    public List<City> getTopNCapitalCitiesByContinent(@RequestParam(value = "n") String n,
                                                      @RequestParam(value = "continent") String continent) {
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
            List<City> capitalCities = new ArrayList<>();
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
    @RequestMapping("get_top_n_capital_cities_by_region")
    public List<City> getTopNCapitalCitiesByRegion(@RequestParam(value = "n") String n,
                                                   @RequestParam(value = "region") String region) {
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
            List<City> capitalCities = new ArrayList<>();
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

    /**
     * Prints a report of capital cities.
     * @param capitalCities The list of capital cities to print.
     */
    public void printCapitalCitiesReport(List<City> capitalCities, String reportTitle) {
        if(capitalCities == null || capitalCities.isEmpty()) {
            System.out.println("Failed to print " + reportTitle +" report: capital cities list is empty.");
        } else {
            // Print report header
            System.out.println("REPORT ON " + reportTitle);
            System.out.printf("%-5s %-35s %-52s %-11s\n",
                    "No", "Name", "Country", "Population");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------"
            );
            int i = 1;
            // Print each city in the list
            for (City c : capitalCities) {
                if(c == null) {
                    continue;
                } else {
                    System.out.printf("%-5s %-35s %-52s %-11d\n",
                            i + ".", c.name, getCountryByCode(c.countryCode).name, c.population
                    );
                    i++;
                }
            }
        }
    }

    /**
     * Generates a list which is a report of capital cities.
     * @param capitals The list of countries to print.
     */
    @RequestMapping("capital_cities_report")
    public List<CapitalCitiesReportItem> generateCapitalCitiesReport(@RequestParam(value = "list") List<City> capitals) {
        // Check list not empty nor null
        if(capitals == null || capitals.isEmpty()) {
            System.out.println("Failed to generate report.");
            return null;
        } else {
            // Create a list where store report items
            List<CapitalCitiesReportItem> report = new ArrayList<>();
            // Generate a report item for each country and add to report
            for(City c : capitals) {
                if(c == null) {
                    continue;
                } else {
                    CapitalCitiesReportItem item = new CapitalCitiesReportItem(
                            c.name,
                            getCountryByCode(c.countryCode).name,
                            c.population
                    );
                    report.add(item);
                }
            }
            return report;
        }
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> POPULATION METHODS

    /**
     * Gets the population of the world.
     * @return An int value for the population, or -1 if there is an error.
     */
    @RequestMapping("get_world_population")
    public long getWorldPopulation() {
        try {
            //Create the result variable to return
            long population = 0;

            //Create a list with all the countries in the world
            List<Country> countries = getAllCountries();
            //Add the population of all countries to the result
            for(Country c : countries) {
                population += c.population;
            }
            return population;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get world population");
            return -1;
        }
    }

    /**
     * Gets the population of a given continent.
     * @param continent A string that contains the name of the continent.
     * @return An int value for the population, or -1 if there is an error.
     */
    @RequestMapping("get_population_by_continent")
    public long getPopulationByContinent(@RequestParam(value = "continent") String continent) {
        try {
            //Create the result variable to return
            long population = 0;

            //Create a list with all the countries in the continent
            List<Country> countries = getCountriesByContinent(continent);
            //Add the population of all countries to the result
            for(Country c : countries) {
                population += c.population;
            }
            return population;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get continent population");
            return -1;
        }
    }

    /**
     * Gets the population of a given region.
     * @param region A string that contains the name of the region.
     * @return An int value for the population, or -1 if there is an error.
     */
    @RequestMapping("get_population_by_region")
    public int getPopulationByRegion(@RequestParam(value = "region") String region) {
        try {
            //Create the result variable to return
            int population = 0;

            //Create a list with all the countries in the continent
            List<Country> countries = getCountriesByRegion(region);
            //Add the population of all countries to the result
            for(Country c : countries) {
                population += c.population;
            }
            return population;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get region population");
            return -1;
        }
    }

    /**
     * Gets the population of a given country.
     * @param country A string that contains the name of the country.
     * @return An int value for the population, or -1 if there is an error.
     */
    @RequestMapping("get_population_by_country")
    public int getPopulationByCountry(@RequestParam(value = "country") String country) {
        try {
            return getCountryByName(country).population;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country population");
            return -1;
        }
    }

    /**
     * Gets the population of a given district.
     * @param district A string that contains the name of the district.
     * @return An int value for the population, or -1 if there is an error.
     */
    @RequestMapping("get_population_by_district")
    public int getPopulationByDistrict(@RequestParam(value = "district") String district) {
        try {
            //Create the result variable to return
            int population = 0;

            //Create a list with all the cities in the district
            List<City> cities = getCitiesByDistrict(district);
            //Add the population of all countries to the result
            for(City c : cities) {
                population += c.population;
            }
            return population;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get district population");
            return -1;
        }
    }

    /**
     * Gets the population of a given city.
     * @param city A string that contains the name of the city.
     * @return An int value for the population, or -1 if there is an error.
     */
    @RequestMapping("get_population_by_city")
    public int getPopulationByCity(@RequestParam(value = "city") String city) {
        try {
            return getCityByName(city).population;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country population");
            return -1;
        }
    }

    /**
     * Gets the population living in cities of a given continent.
     * @param continent A string that contains the name of the continent.
     * @return An int value for the population, or -1 if there is an error.
     */
    @RequestMapping("get_population_in_cities_by_continent")
    public long getPopulationInCitiesByContinent(@RequestParam(value = "continent") String continent) {
        try {
            //Create the result variable to return
            long population = 0;

            //Create a list with all the countries in the continent
            List<City> cities = getCitiesByContinent(continent);
            //Add the population of all countries to the result
            for(City c : cities) {
                population += c.population;
            }
            return population;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population living in cities by continent");
            return -1;
        }
    }

    /**
     * Gets the population living in cities of a given region.
     * @param region A string that contains the name of the region.
     * @return An int value for the population, or -1 if there is an error.
     */
    @RequestMapping("get_population_in_cities_by_region")
    public int getPopulationInCitiesByRegion(@RequestParam(value = "region") String region) {
        try {
            //Create the result variable to return
            int population = 0;

            //Create a list with all the countries in the continent
            List<City> cities = getCitiesByRegion(region);
            //Add the population of all countries to the result
            for(City c : cities) {
                population += c.population;
            }
            return population;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population living in cities by region");
            return -1;
        }
    }

    /**
     * Gets the population living in cities of a given country.
     * @param country A string that contains the name of the country.
     * @return An int value for the population, or -1 if there is an error.
     */
    @RequestMapping("get_population_in_cities_by_country")
    public int getPopulationInCitiesByCountry(@RequestParam(value = "country") String country) {
        try {
            //Create the result variable to return
            int population = 0;

            //Create a list with all the countries in the continent
            List<City> cities = getCitiesByCountry(country);
            //Add the population of all countries to the result
            for(City c : cities) {
                population += c.population;
            }
            return population;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population living in cities by country");
            return -1;
        }
    }

    /**
     * Prints a report of population living in each continent.
     * @param reportTitle String that contains the name of the report.
     */
    public void printPopulationInContinentsReport(String reportTitle) {
        ArrayList<Continent> continents = getAllContinents();
        if(continents == null || continents.isEmpty()) {
            System.out.println("Failed to print " + reportTitle + " report: continents list is empty.");
        } else {
            // Print report header
            System.out.println("REPORT ON " + reportTitle);
            System.out.printf("%-35s %-20s %-25s %-25s \n",
                    "Continent", "Population", "In cities", "Not in cities");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------"
            );
            for(Continent c : continents) {
                if(c == null) {
                    continue;
                } else {
                    long population = getPopulationByContinent(c.getName());
                    if(population == -1) {
                        System.out.println("Unable to get population by continent\n");
                        break;
                    } else {
                        int inCities;
                        String inCitiesPct;
                        String nonCitiesPct;
                        if(population == 0) {
                            inCities = 0;
                            inCitiesPct = String.format("%.2f", (double) 0);
                            nonCitiesPct = String.format("%.2f", (double) 0);
                        } else {
                            inCities = (int) (long) getPopulationInCitiesByContinent(c.getName());
                            inCitiesPct = String.format("%.2f", (double) inCities / population * 100);
                            nonCitiesPct = String.format("%.2f", (double) (population - inCities) / population * 100);
                        }

                        System.out.printf("%-35s %-20s %-25s %-25s \n",
                                c.getName(), population,
                                inCities + " (" + inCitiesPct + "%)",
                                population - inCities + " (" + nonCitiesPct + "%)"
                        );
                    }
                }
            }
        }
    }

    /**
     * Generates a list which is a report of population in continents.
     */
    @RequestMapping("population_in_continents_report")
    public List<PopulationReportItem> generatePopulationInContinentsReport() {
        ArrayList<Continent> continents = getAllContinents();
        // Check list not empty nor null
        if(continents == null || continents.isEmpty()) {
            System.out.println("Failed to generate report.");
            return null;
        } else {
            // Create a list where store report items
            List<PopulationReportItem> report = new ArrayList<>();
            // Generate a report item for each continent and add to report
            for(Continent c : continents) {
                if(c == null) {
                    continue;
                } else {
                    PopulationReportItem item = new PopulationReportItem(
                            c.getName(),
                            getPopulationByContinent(c.getName()),
                            getPopulationInCitiesByContinent(c.getName())
                    );
                    report.add(item);
                }
            }
            return report;
        }
    }

    /**
     * Prints a report of population living in each region.
     * @param reportTitle String that contains the name of the report.
     */
    public void printPopulationInRegionsReport(String reportTitle) {
        ArrayList<String> regions = getAllRegions();
        if(regions == null || regions.isEmpty()) {
            System.out.println("Failed to print " + reportTitle + " report: regions list is empty.");
        } else {
            // Print report header
            System.out.println("REPORT ON " + reportTitle);
            System.out.printf("%-35s %-20s %-25s %-25s \n",
                    "Region", "Population", "In cities", "Not in cities");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------"
            );
            for(String s : regions) {
                if(s == null) {
                    continue;
                } else {
                    long population = getPopulationByRegion(s);
                    if(population == -1) {
                        System.out.println("Unable to get population by region\n");
                        break;
                    } else {
                        int inCities;
                        String inCitiesPct;
                        String nonCitiesPct;
                        if(population == 0) {
                            inCities = 0;
                            inCitiesPct = String.format("%.2f", (double) 0);
                            nonCitiesPct = String.format("%.2f", (double) 0);
                        } else {
                            inCities = getPopulationInCitiesByRegion(s);
                            inCitiesPct = String.format("%.2f", (double) inCities / population * 100);
                            nonCitiesPct = String.format("%.2f", (double) (population - inCities) / population * 100);
                        }

                        System.out.printf("%-35s %-20s %-25s %-25s \n",
                                s, population,
                                inCities + " (" + inCitiesPct + "%)",
                                population - inCities + " (" + nonCitiesPct + "%)"
                        );
                    }
                }
            }
        }
    }

    /**
     * Generates a list which is a report of population in regions.
     */
    @RequestMapping("population_in_regions_report")
    public List<PopulationReportItem> generatePopulationInRegionsReport() {
        ArrayList<String> regions = getAllRegions();
        // Check list not empty nor null
        if(regions == null || regions.isEmpty()) {
            System.out.println("Failed to generate report.");
            return null;
        } else {
            // Create a list where store report items
            List<PopulationReportItem> report = new ArrayList<>();
            // Generate a report item for each region and add to report
            for(String s : regions) {
                if(s == null) {
                    continue;
                } else {
                    PopulationReportItem item = new PopulationReportItem(
                            s,
                            getPopulationByRegion(s),
                            getPopulationInCitiesByRegion(s)
                    );
                    report.add(item);
                }
            }
            return report;
        }
    }

    /**
     * Prints a report of population living in each country.
     * @param reportTitle String that contains the name of the report.
     */
    public void printPopulationInCountriesReport(String reportTitle) {
        ArrayList<Country> countries = getAllCountries();
        if(countries == null || countries.isEmpty()) {
            System.out.println("Failed to print " + reportTitle + " report: countries list is empty.");
        } else {
            // Print report header
            System.out.println("REPORT ON " + reportTitle);
            System.out.printf("%-35s %-20s %-25s %-25s \n",
                    "Country", "Population", "In cities", "Not in cities");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------"
            );
            for(Country c : countries) {
                if(c == null) {
                    continue;
                } else {
                    long population = c.population;
                    if(population == -1) {
                        System.out.println("Unable to get population by country\n");
                        break;
                    } else {
                        int inCities;
                        String inCitiesPct;
                        String nonCitiesPct;
                        if(population == 0) {
                            inCities = 0;
                            inCitiesPct = String.format("%.2f", (double) 0);
                            nonCitiesPct = String.format("%.2f", (double) 0);
                        } else {
                            inCities = getPopulationInCitiesByCountry(c.name);
                            inCitiesPct = String.format("%.2f", (double) inCities / population * 100);
                            nonCitiesPct = String.format("%.2f", (double) (population - inCities) / population * 100);
                        }

                        System.out.printf("%-35s %-20s %-25s %-25s \n",
                                c.name, population,
                                inCities + " (" + inCitiesPct + "%)",
                                population - inCities + " (" + nonCitiesPct + "%)"
                        );
                    }
                }
            }
        }
    }

    /**
     * Generates a list which is a report of population in regions.
     */
    @RequestMapping("population_in_countries_report")
    public List<PopulationReportItem> generatePopulationInCountriesReport() {
        ArrayList<Country> countries = getAllCountries();
        // Check list not empty nor null
        if(countries == null || countries.isEmpty()) {
            System.out.println("Failed to generate report.");
            return null;
        } else {
            // Create a list where store report items
            List<PopulationReportItem> report = new ArrayList<>();
            // Generate a report item for each country and add to report
            for(Country c : countries) {
                if(c == null) {
                    continue;
                } else {
                    PopulationReportItem item = new PopulationReportItem(
                            c.name,
                            getPopulationByCountry(c.name),
                            getPopulationInCitiesByCountry(c.name)
                    );
                    report.add(item);
                }
            }
            return report;
        }
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> LANGUAGE METHODS

    /**
     * Gets the population speaking a given language.
     * @param language A string that contains the name of the language.
     * @return An int value for the population, or -1 if there is an error.
     */
    @RequestMapping("get_population_by_language")
    public int getPopulationByLanguage(@RequestParam(value = "language") String language) {
        try {
            //Create the result variable to return
            int population = 0;

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT C.*, L.Percentage "
                            +"FROM country C "
                            +"JOIN countrylanguage L ON "
                            +"C.Code = L.CountryCode "
                            +"WHERE C.Code = L.CountryCode "
                            +"AND L.Language = '" + language + "' ";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            //Add the population of all countries to the result
//            int i = 1;
            while(rset.next()) {
//                System.out.printf("%-5s %-5s %-26s %-11s %-6s %-11s\n",
//                        i + ".",
//                        rset.getString("Code"),
//                        rset.getString("Name"),
//                        rset.getInt("Population"),
//                        rset.getDouble("Percentage"),
//                        (int) (rset.getInt("Population") * rset.getDouble("Percentage") / 100)
//                );
                population += (int) (rset.getInt("Population") * rset.getDouble("Percentage") / 100);
//                i++;
            }
            return population;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population by language");
            return -1;
        }
    }

    /**
     * Makes a list of languages sorted by number of speakers from greatest to smallest from a given array of languages
     * @param languages String array that contains the names of the languages to sort
     */
    @RequestMapping("sort_languages_by_speakers")
    public List<String> sortLanguagesBySpeakers(@RequestParam(value = "languages") String[] languages) {
        try {
            //Create all aux structures and variables
            List<String> sortedLanguages = new ArrayList<>();
            PriorityQueue<Integer> speakers = new PriorityQueue<>();
            Map<Integer, String> aux = new HashMap<>();
            int key;

            //Fill aux map with {number of speakers, language}
            //and priority queue with number of speakers so they get sorted
            for(String s : languages) {
                key = getPopulationByLanguage(s);
                speakers.add(key);
                aux.put(key, s);
            }
            while(!speakers.isEmpty()) {
                sortedLanguages.add(aux.get(speakers.remove()));
            }
            return sortedLanguages;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get sort languages");
            return null;
        }
    }

    /**
     * Prints a report of population that speaks the following languages, form greatest number to smallest:
     *      - Chinese, English, Hindi, Spanish and Arabic
     */
    @RequestMapping("population_speaking_languages_report")
    public void printPopulationSpeakingLanguagesReport() {
        try {
            //Get the world population for later operations
            long worldPopulation = getWorldPopulation();
            //Create an array with the names of the languages
            String[] languages = {"Chinese", "English", "Hindi", "Spanish", "Arabic"};

            //Sort languages with an aux method
            List<String> sortedLanguages = sortLanguagesBySpeakers(languages);

            //Print header
            System.out.println("REPORT ON NUMBER OF SPEAKERS FOR ARABIC, CHINESE, ENGLISH, HINDI AND SPANISH");
            System.out.printf("%-5s %-20s %-20s %-6s \n", "No", "Language", "Speakers", "World Pct.");
            System.out.println("----------------------------------------------------------");
            //Get the population speaking each of them and print
            int i = 1;
            while(!sortedLanguages.isEmpty()) {
                String language = sortedLanguages.remove(sortedLanguages.size()-1);
                int population = getPopulationByLanguage(language);
                String worldPct = String.format("%.2f", (double) population / worldPopulation * 100);
                System.out.printf("%-5s %-20s %-20d %-6s \n",
                        i + ".",
                        language,
                        population,
                        "(" + worldPct + "%)"
                );
                i++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to print population speaking languages report");
        }
    }

    /**
     * Generates a list which is a report of population in regions.
     */
    @RequestMapping("population_speaking_report")
    public List<LanguageReportItem> generatePopulationSpeakingLanguagesReport() {
        //Get the world population for later operations
        long worldPopulation = getWorldPopulation();
        //Create an array with the names of the languages
        String[] languages = {"Chinese", "English", "Hindi", "Spanish", "Arabic"};

        //Sort languages with an aux method
        List<String> sortedLanguages = sortLanguagesBySpeakers(languages);
        if(sortedLanguages == null || sortedLanguages.isEmpty()) {
            System.out.println("Failed to generate report.");
            return null;
        } else {
            // Create a list where store report items
            List<LanguageReportItem> report = new ArrayList<>();
            // Generate a report item for each country and add to report
            while(!sortedLanguages.isEmpty()) {
                String s = sortedLanguages.remove(sortedLanguages.size()-1);
                if("".equals(s)) {
                    continue;
                } else {
                    LanguageReportItem item = new LanguageReportItem(
                            toTitleCase(s),
                            getPopulationByLanguage(s),
                            worldPopulation
                    );
                    report.add(item);
                }
            }
            return report;
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

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Web report triggers

    /**
     * Generate report one for web view.
     */
    @RequestMapping("report_one")
    public List<CountriesReportItem> report1() {
        List<Country> list = getAllCountries();
        return generateCountriesReport(list);
    }


    /**
     * Generate report two for web view.
     */
    @RequestMapping("report_two")
    public List<CountriesReportItem> report2(@RequestParam(value = "continent") String continent) {
        List<Country> list = getCountriesByContinent(continent);
        return generateCountriesReport(list);
    }

    /**
     * Generate report three for web view.
     */
    @RequestMapping("report_three")
    public List<CountriesReportItem> report3(@RequestParam(value = "region") String region) {
        List<Country> list = getCountriesByRegion(region);
        return generateCountriesReport(list);
    }

    /**
     * Generate report four for web view.
     */
    @RequestMapping("report_four")
    public List<CountriesReportItem> report4(@RequestParam(value = "number") String n) {
        List<Country> list = getTopNCountries(n);
        return generateCountriesReport(list);
    }

    /**
     * Generate report five for web view.
     */
    @RequestMapping("report_five")
    public List<CountriesReportItem> report5(@RequestParam(value = "number") String n,
                                             @RequestParam(value = "continent") String continent) {
        List<Country> list = getTopNCountriesByContinent(n, continent);
        return generateCountriesReport(list);
    }

    /**
     * Generate report six for web view.
     */
    @RequestMapping("report_six")
    public List<CountriesReportItem> report6(@RequestParam(value = "number") String n,
                                             @RequestParam(value = "region") String region) {
        List<Country> list = getTopNCountriesByRegion(n, region);
        return generateCountriesReport(list);
    }

    /**
     * Generate report seven for web view.
     */
    @RequestMapping("report_seven")
    public List<CitiesReportItem> report7() {
        List<City> list = getAllCities();
        return generateCitiesReport(list);
    }

    /**
     * Generate report eight for web view.
     */
    @RequestMapping("report_eight")
    public List<CitiesReportItem> report8(@RequestParam(value = "continent") String continent) {
        List<City> list = getCitiesByContinent(continent);
        return generateCitiesReport(list);
    }

    /**
     * Generate report nine for web view.
     */
    @RequestMapping("report_nine")
    public List<CitiesReportItem> report9(@RequestParam(value = "region") String region) {
        List<City> list = getCitiesByRegion(region);
        return generateCitiesReport(list);
    }

    /**
     * Generate report ten for web view.
     */
    @RequestMapping("report_ten")
    public List<CitiesReportItem> report10(@RequestParam(value = "country") String country) {
        List<City> list = getCitiesByCountry(country);
        return generateCitiesReport(list);
    }

    /**
     * Generate report eleven for web view.
     */
    @RequestMapping("report_eleven")
    public List<CitiesReportItem> report11(@RequestParam(value = "district") String district) {
        List<City> list = getCitiesByDistrict(district);
        return generateCitiesReport(list);
    }

    /**
     * Generate report twelve for web view.
     */
    @RequestMapping("report_twelve")
    public List<CitiesReportItem> report12(@RequestParam(value = "number") String n) {
        List<City> list = getTopNCities(n);
        return generateCitiesReport(list);
    }

    /**
     * Generate report thirteen for web view.
     */
    @RequestMapping("report_thirteen")
    public List<CitiesReportItem> report13(@RequestParam(value = "number") String n,
                                           @RequestParam(value = "continent") String continent) {
        List<City> list = getTopNCitiesByContinent(n, continent);
        return generateCitiesReport(list);
    }

    /**
     * Generate report fourteen for web view.
     */
    @RequestMapping("report_fourteen")
    public List<CitiesReportItem> report14(@RequestParam(value = "number") String n,
                                           @RequestParam(value = "region") String region) {
        List<City> list = getTopNCitiesByRegion(n, region);
        return generateCitiesReport(list);
    }

    /**
     * Generate report fifteen for web view.
     */
    @RequestMapping("report_fifteen")
    public List<CitiesReportItem> report15(@RequestParam(value = "number") String n,
                                           @RequestParam(value = "country") String country) {
        List<City> list = getTopNCitiesByCountry(n, country);
        return generateCitiesReport(list);
    }

    /**
     * Generate report sixteen for web view.
     */
    @RequestMapping("report_sixteen")
    public List<CitiesReportItem> report16(@RequestParam(value = "number") String n,
                                           @RequestParam(value = "district") String district) {
        List<City> list = getTopNCitiesByDistrict(n, district);
        return generateCitiesReport(list);
    }

    /**
     * Generate report seventeen for web view.
     */
    @RequestMapping("report_seventeen")
    public List<CapitalCitiesReportItem> report17() {
        List<City> list = getAllCapitalCities();
        return generateCapitalCitiesReport(list);
    }


    /**
     * Generate report eighteen for web view.
     */
    @RequestMapping("report_eighteen")
    public List<CapitalCitiesReportItem> report18(@RequestParam(value = "continent") String continent) {
        List<City> list = getCapitalCitiesByContinent(continent);
        return generateCapitalCitiesReport(list);
    }

    /**
     * Generate report nineteen for web view.
     */
    @RequestMapping("report_nineteen")
    public List<CapitalCitiesReportItem> report19(@RequestParam(value = "region") String region) {
        List<City> list = getCapitalCitiesByRegion(region);
        return generateCapitalCitiesReport(list);
    }

    /**
     * Generate report twenty for web view.
     */
    @RequestMapping("report_twenty")
    public List<CapitalCitiesReportItem> report20(@RequestParam(value = "number") String n) {
        List<City> list = getTopNCapitalCities(n);
        return generateCapitalCitiesReport(list);
    }

    /**
     * Generate report twenty one for web view.
     */
    @RequestMapping("report_twenty_one")
    public List<CapitalCitiesReportItem> report21(@RequestParam(value = "number") String n,
                                                  @RequestParam(value = "continent") String continent) {
        List<City> list = getTopNCapitalCitiesByContinent(n, continent);
        return generateCapitalCitiesReport(list);
    }

    /**
     * Generate report twenty two for web view.
     */
    @RequestMapping("report_twenty_two")
    public List<CapitalCitiesReportItem> report22(@RequestParam(value = "number") String n,
                                                  @RequestParam(value = "region") String region) {
        List<City> list = getTopNCapitalCitiesByRegion(n, region);
        return generateCapitalCitiesReport(list);
    }

    /**
     * Generate report twenty three for web view.
     */
    @RequestMapping("report_twenty_three")
    public List<PopulationReportItem> report23() {
        return generatePopulationInContinentsReport();
    }

    /**
     * Generate report twenty four for web view.
     */
    @RequestMapping("report_twenty_four")
    public List<PopulationReportItem> report24() {
        return generatePopulationInRegionsReport();
    }

    /**
     * Generate report twenty five for web view.
     */
    @RequestMapping("report_twenty_five")
    public List<PopulationReportItem> report25() {
        return generatePopulationInCountriesReport();
    }

    /**
     * Generate report twenty six for web view.
     */
    @RequestMapping("report_twenty_six")
    public long report26() {
        return getWorldPopulation();
    }

    /**
     * Generate report twenty seven for web view.
     */
    @RequestMapping("report_twenty_seven")
    public long report27(@RequestParam(value = "continent") String continent) {
        return getPopulationByContinent(continent);
    }

    /**
     * Generate report twenty eight for web view.
     */
    @RequestMapping("report_twenty_eight")
    public int report28(@RequestParam(value = "region") String region) {
        return getPopulationByRegion(region);
    }

    /**
     * Generate report twenty nine for web view.
     */
    @RequestMapping("report_twenty_nine")
    public int report29(@RequestParam(value = "country") String country) {
        return getPopulationByCountry(country);
    }

    /**
     * Generate report thirty for web view.
     */
    @RequestMapping("report_thirty")
    public int report30(@RequestParam(value = "district") String district) {
        return getPopulationByDistrict(district);
    }

    /**
     * Generate report thirty one for web view.
     */
    @RequestMapping("report_thirty_one")
    public int report31(@RequestParam(value = "city") String city) {
        return getPopulationByCity(city);
    }

    /**
     * Generate report thirty two for web view.
     */
    @RequestMapping("report_thirty_two")
    public List<LanguageReportItem> report32() {
        return generatePopulationSpeakingLanguagesReport();
    }

} // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CLASS App