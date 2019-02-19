# USE CASE 9: Produce a Report on the People Speaking a Language from greatest number to smallest.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Data Analyst* I want *to know the number of people who speak a given list of languages and the percentage of the world population* so that *I can make a report*.

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the languages list. Database contains current population speaking languages data.

### Success End Condition

A report is available for Sociology.

### Failed End Condition

No report is produced.

### Primary Actor

Data Analyst.

### Trigger

A request for people speaking a given list of languages information is sent to Data Analysis.

## MAIN SUCCESS SCENARIO

1. Sociology request population speaking a given list of languages information.
2. Data Analyst captures names of the languages to get population speaking them information.
3. Data Analyst extracts current population speaking the given list of languages.
4. Data Analyst orders the languages by greatest number of speakers to smallest.
5. Data Analyst provides report to Sociology.

## EXTENSIONS

3. **Language does not exist**:
    1. Data Analyst informs Sociology no language exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0