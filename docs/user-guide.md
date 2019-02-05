# User Guide

This guide will provide you with a brief overview over the most essential parts of the Comparalyzer API.

## Comparator

The `Comparator` package is the central part of the library that contains the entry point to the difference compare operation.
It acts as a factory to get hold of an actual `DefaultDiffComparator` instance with the help of which the comparison mechanism can be instantiated.

## Converter

The `Converter` package contains the converters instances that can be applied during the comparing procedure.

## Entry

The `Entry` package contains the difference entry objects that wraps the objects values on a per-property basis.

## Factory

The `Factory` package contains the basic classes that return difference comparator instances by input object type.

## Matcher

The `Matcher` package contains the matchers classes that work as predicates to filter out the objects by custom property values.

## Utils

The `Utils` package contains helper service classes to support the object comparison by applying particular comparator / converter instances.

## Configuration API

The following sections describes the different parts of the configuration API:

#### AbstractDiffComparator

Allows to register custom comparing instance and apply custom criteria that are only known after the diff for the affected references and its sub-references has been determined.

#### AbstractConverter

Allows to register custom converter instance and apply custom converters to objects comparison.

#### DefaultDiffEntry

Allows to register custom difference entry instance as a result of objects comparison.

#### DefaultDiffComparatorFactory

Allows to instantiate custom comparators instances by calling one of its numerous static builder methods based on object input type.

#### AbstractMatcher

Allows to register custom matcher instance to filter input objects by user-defined property-based rules.

#### AbstractTypeSafeMatcher

Allows to register custom type safe matcher instance to filter input objects by particular type and user-defined property-based rules.

#### AbstractFieldMatcher

Allows to register custom field matcher instance to filter input objects by property values and user-defined rules.
