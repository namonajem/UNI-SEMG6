# Code of Conduct

> A code of conduct is a set of rules outlining the social norms and rules and responsibilities of, or proper practices for, an individual, party or organization.

## Our compromise

All members of Group 6, for the simple reason of belonging, we commit ourselves to behave according to the following codes of conduct:

- **Weekly meeting** - Every Tuesday at 1pm @ JKCC.
- **Personal work commitment** - 10 Zube pts. 

## Non-compliance consequences

1. **Minor non-compliance consequences** - The member should bring cookies for the rest of the team and look while the other eat them up. 
2. **Major non-compliance consequences** - The member will be reported to the module leader and possible ejected from the team.

## Git branches workflow.

1. Pull up to date develop branch.
2. Each team member works on their own individual feature branch for their assigned feature.
3. Once feature is complete, the up-to-date develop branch is merged with the new feature.
4. Once all features for next release are completed and merged with develop check develop still works.
5. Create new release, merge develop into master then master into develop.
6. Start back form 1 for a new feature.


## Zube kanban boards workflow.

1. The Product Owner will prepare a sprint per week. Sprints start on Tuesday meeting and are a week long.
2. Each sprint will consist of at least one user story. 
3. User stories will be broken down into tasks and assigned by the Product Owner - each member up to 10 pts.
4. Each member of the team should look at the Zube Sprint Board at least once per week and start working on the assigned tasks.
5. Tasks are completed when the cards have moved through all requisite fields up to "In Review" and approved by other team members.
6. Once the sprint has ended, any uncompleted tasks should be transferred to the next sprint board, review why it did not get finished and then possibly reassign the task.
7. Start back from 1 for a new sprint.


## Coding Style Guide

The intention of this guide is to provide a set of conventions that encourage good and accordant code among all group members.

#### Braces

Braces follow the KR style ("Egyptian brackets"). This means line break should be after the opening brace, before the closing brace and after the closing brace only when not in a multi-block statement (`if`/`else` or `try`/`catch`/`finally`).

```java
// Like this
void myMethod() {
	try {
    	doSomething();
    } catch (error) {
    	printError();
    }
}
```
#### Block Indentation

Each time a new block or block-like construct is opened, the indent increases by **one tab**.

#### Line Continuation

```java
// This
public City(
        int id,
        String name,
        String countryCode,
        String district,
        int population) {
    
    this.id = id;
    ...
}

// Better than this
public City(int id, String name, String countryCode, String district, int population) {
    this.id = id;
    ...
}
```

#### Case Styles

- **Class** names are written in `UpperCamelCase`.
- **Method** names and **variable** names are written in `lowerCamelCase`.
- **Constant** names use `CONSTANT_CASE`.

#### Variable naming

- A variable name should describe the variable's purpose.
- Adding extra information like scope and type is generally a sign of a bad variable name.
- Extremely short variable names should be reserved for instances like loop indices. 

#### Spacing Operators

```java
// Do this
return (8 + 12) - value;

// Not this
return (8+12)-value;
```

#### Documentation

- **Documenting a class:** A thorough class doc usually has a one sentence summary and, if necessary, a more detailed explanation.

- **Documenting a method:** A method doc should tell what the method does. Depending on the argument types, it may also be important to document input format.

- **Commenting a line:** A difficult to understand line should be explained with a comment.

```java
/**
 * Represents a City
 */
public class City {
    // City ID number
    int id;
    
    /**
     * ID constructor
     * 
     * @param id the id of the City
     */
    public City(int id) {
        this.id = id;
    }
}
```