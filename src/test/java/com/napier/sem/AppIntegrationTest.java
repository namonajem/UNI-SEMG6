package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("192.168.99.100:33060");
    }

    @Test
    void testGetCountryByCode()
    {
        Country cntry = app.getCountryByCode("ESP");
        assertEquals(cntry.name, "Spain", "Name of the Country");
        assertEquals(cntry.code, "ESP", "Code of the Country");
    }
}