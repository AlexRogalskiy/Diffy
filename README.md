# Comparalyzer

The Comparalyzer project

## Features

* Any object can be used despite the references dependency graph
* Finds the differences between two objects
* Returns the object's difference in an easy iterable structure
* Requires no changes to your existing class hierarchy
* Provides a simple configurable API
* No extra runtime dependencies

## Installation

***Using with Maven:***

```xml
<dependency>
    <groupId>com.wildbeeslabs.sensiblemetrics.Comparalyzer</groupId>
    <artifactId>comparalyzer-core</artifactId>
    <version>1.0.0-RELEASE</version>
</dependency>
```

***Using with Gradle:***

```groovy
compile 'com.wildbeeslabs.sensiblemetrics.Comparalyzer:1.0.0-RELEASE'
```

***Packaging:***

Package the application with all the dependencies:
```java
mvn clean compile assembly:single
```

## Technical description

As a result represents an iterable structure of fields difference entries by any given object (custom object /fields comparators can be used if provided any).

```java
// First object to be compared by
Object object1 = new Object();
// Last object to be compared with
Object object2 = new Object();
...
// Difference comparator instance
DiffComparator<Object> diffComparator = DefaultDiffComparatorFactory.create(Object.class);
// ResultSet of objects comparison
Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(object1, object2);
```

## Contribution

If you discovered a bug or have just an idea for a new feature development, please don't hesitate to contact our team at
[Pull Request](https://help.github.com/articles/using-pull-requests)

More information regarding a contribution involvement process can be found at:
[here](https://github.com/SQiShER/java-object-diff/blob/master/CONTRIBUTING.md)

---
Powered by *IntelliJ IDEA* IDE
[![IntelliJ IDEA](https://www.jetbrains.com/idea/docs/logo_intellij_idea.png)](https://www.jetbrains.com/idea/)
