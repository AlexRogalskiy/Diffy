## General Description

`Diffy` is a simple-easy library to provide the differences between set of Java objects.

## Introduction

With the help of this library you can figure out the differences between the versions of an object or just simply compare the existing ones.

Based on a reflection mechanism to scan the object for fields and using the getters methods one can easily differentiate the values of the objects and get a collection of difference entries by a per-property slice.

By hiding the complexities of nested objects comparison this tool provides a simple API that can be used without any preconditions:

```java
Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(object1, object2);
```

The difference entry result-set can be traversed in multiple ways by using iterators, visitors etc.

Each entry `DefaultDiffEntry` represents a property name with underlying objects values (how the values differ from each other) and can be extended depending on custom business needs.
