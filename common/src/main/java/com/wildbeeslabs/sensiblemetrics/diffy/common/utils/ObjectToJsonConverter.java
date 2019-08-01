package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.Init;
import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.JsonElement;
import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.JsonSerializable;
import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.JsonSerializationException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectToJsonConverter {
    public String convertToJson(final Object object) throws JsonSerializationException {
        try {
            this.checkIfSerializable(object);
            this.initializeObject(object);
            return getJsonString(object);
        } catch (Exception e) {
            throw new JsonSerializationException(e.getMessage());
        }
    }

    private void checkIfSerializable(final Object object) {
        if (Objects.isNull(object)) {
            throw new JsonSerializationException("Can't serialize a null object");
        }
        final Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
            throw new JsonSerializationException("The class " + clazz.getSimpleName() + " is not annotated with JsonSerializable");
        }
    }

    private void initializeObject(final Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Class<?> clazz = object.getClass();
        for (final Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Init.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    private String getJsonString(final Object object) throws IllegalArgumentException, IllegalAccessException {
        final Class<?> clazz = object.getClass();
        final Map<String, String> jsonElementsMap = new HashMap<>();
        for (final Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonElement.class)) {
                jsonElementsMap.put(getKey(field), (String) field.get(object));
            }
        }

        String jsonString = jsonElementsMap.entrySet()
            .stream()
            .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
            .collect(Collectors.joining(","));
        return "{" + jsonString + "}";
    }

    private String getKey(final Field field) {
        final String value = field.getAnnotation(JsonElement.class).key();
        return value.isEmpty() ? field.getName() : value;
    }
}

/*
@JsonSerializable
public class Person {
    @JsonElement
    private String firstName;
    @JsonElement
    private String lastName;
    @JsonElement(key = "personAge")
    private String age;

    private String address;

    public Person(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName, String age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Init
    private void initNames() {
        this.firstName = this.firstName.substring(0, 1)
            .toUpperCase() + this.firstName.substring(1);
        this.lastName = this.lastName.substring(0, 1)
            .toUpperCase() + this.lastName.substring(1);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
 */
