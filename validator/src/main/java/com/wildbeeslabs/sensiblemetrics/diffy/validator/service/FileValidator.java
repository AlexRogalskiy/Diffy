package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.exception.ValidationException;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class FileValidator implements Validator<String> {

    @Override
    public boolean validate(final String value) throws ValidationException {
        final Path path = Paths.get(value);
        if (!Files.exists(path)) {
            return false;
        }
        if (!Files.isRegularFile(path)) {
            throw new ValidationException(String.format(Locale.ROOT, "%s is not a file: %s", path, value));
        }
        if (!Files.isReadable(path)) {
            throw new ValidationException(String.format(Locale.ROOT, "%s is not readable: %s", path, value));
        }
        return true;
    }
}
