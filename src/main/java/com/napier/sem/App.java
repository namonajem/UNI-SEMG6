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
            connect("localhost:3306");
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
     * @param countries The list of cities to print.
     */
    @RequestMapping("countries_report")
    public void printCountriesReport(@RequestParam(value = "list") List<Country> countries,
                                     @RequestParam(value = "title") String reportTitle) {
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
    @RequestMapping("cities_report")
    public void printCitiesReport(@RequestParam(value = "list") List<City> cities,
                                  @RequestParam(value = "title") String reportTitle) {
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
    @RequestMapping("capital_cities_report")
    public void printCapitalCitiesReport(@RequestParam(value = "list") List<City> capitalCities,
                                         @RequestParam(value = "title") String reportTitle) {
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

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> POPULATION METHODS

    /**
     * Gets the population of the world.
     * @return An int value for the population, or -1 if there is an error.
     */
    @RequestMapping("get_world_population")
    public int getWorldPopulation() {
        try {
            //Create the result variable to return
            int population = 0;

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
    public int getPopulationByContinent(@RequestParam(value = "continent") String continent) {
        try {
            //Create the result variable to return
            int population = 0;

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
    public int getPopulationInCitiesByContinent(@RequestParam(value = "continent") String continent) {
        try {
            //Create the result variable to return
            int population = 0;

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
     * Prints a report of population.
     * @param typeOfTerritory String that contains a type of territory between continent/region/country.
     * @param territory String that contains the name of the territory.
     * @param reportTitle String that contains the name of the report.
     */
    @RequestMapping("population_report")
    public void printPopulationReport(@RequestParam(value = "type_of_territory") String typeOfTerritory,
                                      @RequestParam(value = "territory") String territory,
                                      @RequestParam(value = "title") String reportTitle) {
        //Get territory data
        if("".equals(typeOfTerritory) || "".equals(territory)) {
            System.out.println("Failed to get territory. Don't enter empty parameters.");
        } else {
            int population;
            int inCities;
            switch (typeOfTerritory.toUpperCase()) {
                case "CONTINENT":
                    population = getPopulationByContinent(territory);
                    inCities = getPopulationInCitiesByContinent(territory);
                    break;
                case "REGION":
                    population = getPopulationByRegion(territory);
                    inCities = getPopulationInCitiesByRegion(territory);
                    break;
                case "COUNTRY":
                    population = getPopulationByCountry(territory);
                    inCities = getPopulationInCitiesByCountry(territory);
                    break;
                default:
                    System.out.println("Enter a valid type of territory: continent/region/country.");
                    return;
            }

            if (population <= 0) {
                System.out.println("Failed to print " + reportTitle + " report.\n" +
                        "Unable to get territory population.\n" +
                        "Please, make sure you enter an existent territory.");
            } else {
                //Calculate percentage values
                String inCitiesPct = String.format("%.2f", (double) inCities / population * 100);
                String nonCitiesPct = String.format("%.2f", (double) (population - inCities) / population * 100);

                // Print report header
                System.out.println("REPORT ON " + reportTitle);
                System.out.printf("%-35s %-20s %-25s %-25s \n",
                        "Territory name", "Population", "In cities", "Not in cities");
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------"
                );
                System.out.printf("%-35s %-20s %-25s %-25s \n",
                        territory, population,
                        inCities + " (" + inCitiesPct + "%)",
                        population - inCities + " (" + nonCitiesPct + "%)"
                );
            }
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
            int worldPopulation = getWorldPopulation();
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

//    /**
//     * Prints a report of population that speaks a list of languages.
//     * @param args String that contains the name of the territory.
//     */
//    public void printPopulationSpeakingLanguagesReport(String... args) {
//        try {
//            int worldPopulation = getWorldPopulation();
//            String languages = "";
//            //Sort languages with an aux method
//            List<String> sortedLanguages = sortLanguagesBySpeakers(args);
//
//            //Print header
//            for(String s : args) {
//                languages += " " + s.toUpperCase() + ",";
//            }
//            System.out.println("REPORT ON NUMBER OF SPEAKERS FOR" + languages);
//            System.out.printf("%-5s %-20s %-20s %-6s \n", "No", "Language", "Speakers", "World Pct.");
//            System.out.println("----------------------------------------------------------");
//
//            //Get the population speaking each of them and print
//            int i = 1;
//            while(!sortedLanguages.isEmpty()) {
//                String language = sortedLanguages.remove(sortedLanguages.size()-1);
//                int population = getPopulationByLanguage(language);
//                String worldPct = String.format("%.2f", (double) population / worldPopulation * 100);
//                System.out.printf("%-5s %-20s %-20d %-6s \n",
//                        i + ".",
//                        language,
//                        population,
//                        "(" + worldPct + "%)"
//                );
//                i++;
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println("Failed to print population speaking languages report");
//        }
//    }

} // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CLASS App