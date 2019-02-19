# USE CASE 1: Produce a Report on the Population of the Countries of a Given Territory

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Data Analyst* I want *to get a list of the countries in a given territory ordered by population* so that *I can make a report*.

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

A request for countries in a territory population information is sent to Data Analysis.

## MAIN SUCCESS SCENARIO

1. Sociology request population information for countries in a given territory.
2. Data Analyst captures name of the territory to get countries's within population information.
3. Data Analyst extracts current population information of all countries of the given territory.
4. Data Analyst orders the countries in result by population.
5. Data Analyst provides report to Sociology.

## EXTENSIONS

3. **Territory does not exist**:
    1. Data Analyst informs Sociology no territory exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0