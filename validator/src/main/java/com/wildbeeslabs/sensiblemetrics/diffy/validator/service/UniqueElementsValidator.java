package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Validates that the provided collection only contains unique elements, i.e. that we can't find 2 equal elements in the
 * collection.
 * <p>
 * Uniqueness is defined by the {@code equals()} method of the objects being compared.
 */
public class UniqueElementsValidator implements Validator<Collection> {

    /**
     * @param collection                 the collection to validate
     * @param constraintValidatorContext context in which the constraint is evaluated
     * @return true if the input collection is null or does not contain duplicate elements
     */
    @Override
    public boolean validate(final Collection collection) {
        if (collection == null || collection.size() < 2) {
            return true;
        }
        final List<Object> duplicates = this.findDuplicates(collection);
        if (duplicates.isEmpty()) {
            return true;
        }
        return false;
    }

    private List<Object> findDuplicates(final Collection<?> collection) {
        final Set<Object> uniqueElements = new HashSet<>(collection.size());
        return collection.stream().filter(o -> !uniqueElements.add(o)).collect(toList());
    }
}
