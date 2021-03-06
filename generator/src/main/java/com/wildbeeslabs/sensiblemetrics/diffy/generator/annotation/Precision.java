package com.wildbeeslabs.sensiblemetrics.diffy.generator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * <p>Mark a parameter of a {@link com.pholser.junit.quickcheck.Property}
 * method with this annotation to constrain the values generated for the
 * parameter to a given precision.</p>
 */
@Target({ PARAMETER, FIELD, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@GeneratorConfiguration
public @interface Precision {
    /**
     * @return desired {@linkplain java.math.BigDecimal#scale() scale} of the
     * generated values
     */
    int scale();
}
