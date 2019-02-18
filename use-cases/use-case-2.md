# USE CASE 2: Produce a List of the Top N Populated Countries of a Given Territory

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Data Analyst* I want *to get a list of a given number of countries that are the most populated in a given territory* so that *I can make a report*.

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the territory. Database contains current countries population data.

### Success End Condition

A report is available for Sociology.

### Failed End Condition

No report is produced.

### Primary Actor

Data Analyst.

### Trigger

A request for top N populated countries in a territory is sent to Data Analysis.

## MAIN SUCCESS SCENARIO

1. Sociology request top N populated countries in a territory.
2. Data Analyst captures name of the territory to get countries's within population information.
3. Data Analyst extracts current population information of all countries of the given territory.
4. Data Analyst manages to order the countries in result by population.
5. Data Analyst removes unrequested items of the list.
6. Data Analyst provides report to Sociology.

## EXTENSIONS

3. **Territory does not exist**:
    1. Data Analyst informs Sociology no territory exists.
5. **Result contains less items than requested**:
    1. Data Analyst informs Sociology less countries than requested.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0