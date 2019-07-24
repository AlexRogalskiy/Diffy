package com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces;

/**
 * Provides the ability to ensure characters are part of a permitted set.
 */
public interface CharacterValidator {
    /**
     * Validates the supplied character.
     *
     * @param chr The character to validate.
     * @return <code>true</code> if chr is valid, <code>false</code> if not.
     */
    boolean isValid(char chr);
}
