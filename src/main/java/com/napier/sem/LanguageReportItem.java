package com.napier.sem;

public class LanguageReportItem {
    public String name;
    public int speakers;
    public String worldPct;

    public LanguageReportItem() {
    }

    public  LanguageReportItem(
            String name,
            int speakers,
            long worldPopulation) {

        this.name = name;
        this.speakers = speakers;
        this.worldPct = String.format("%.2f", (double) speakers / worldPopulation * 100);
    }
}
