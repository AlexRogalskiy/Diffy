package com.wildbeeslabs.sensiblemetrics.diffy.annotation;

import lombok.NonNull;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@NonNull
public @interface Factory {
}
