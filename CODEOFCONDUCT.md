# Code of Conduct

> A code of conduct is a set of rules outlining the social norms and rules and responsibilities of, or proper practices for, an individual, party or organization.

## Our compromise

All members of Group 6, for the simple reason of belonging, we commit ourselves to behave according to the following codes of conduct:

- **Weekly meeting** - Every Tuesday at 1pm @ JKCC.
- Personal work compromise, measured in...?
- Non-compliance consequences.
- Git branches workflow.
- Zube kanban boards workflow.

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