# USE CASE 4: Produce a List of the Top N Populated Cities of a Given Territory

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Data Analyst* I want *to get a list of a given number of cities that are the most populated in a given territory* so that *I can make a report*.

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the territory. Database contains current cities population data.

### Success End Condition

A report is available for Sociology.

### Failed End Condition

No report is produced.

### Primary Actor

Data Analyst.

### Trigger

A request for top N populated cities in a territory is sent to Data Analysis.

## MAIN SUCCESS SCENARIO

1. Sociology request top N populated cities in a territory.
2. Data Analyst captures name of the territory to get cities's within population information.
3. Data Analyst extracts current population information of all cities of the given territory.
4. Data Analyst orders the cities in result by population.
5. Data Analyst removes unrequested items of the list.
6. Data Analyst provides report to Sociology.

## EXTENSIONS

3. **Territory does not exist**:
    1. Data Analyst informs Sociology no territory exists.
5. **Result contains less items than requested**:
    1. Data Analyst informs Sociology less cities than requested.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0