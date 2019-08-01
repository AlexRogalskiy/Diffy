package com.wildbeeslabs.sensiblemetrics.diffy.common.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface JsonElement {

    String key() default "";
}
