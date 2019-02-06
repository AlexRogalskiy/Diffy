/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.comparalyzer.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Custom reflection utilities implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class ReflectionUtils {

    /**
     * Default primitive wrapper types {@link Set}
     */
    private static final Set<Class<?>> DEFAULT_PRIMITIVE_WRAPPER_TYPES = getDefaultPrimitiveWrapperTypes();
    /**
     * Default primitive numeric types {@link Collection}
     */
    private static final Collection<Class<?>> DEFAULT_PRIMITIVE_NUMERIC_TYPES = getDefaultPrimitiveNumericTypes();

    /**
     * Returns collection of primitive wrapper types {@link Set}
     *
     * @return collection of primitive wrapper types {@link Set}
     */
    private static Set<Class<?>> getDefaultPrimitiveWrapperTypes() {
        final Set<Class<?>> wrapperTypes = new HashSet<>();
        wrapperTypes.add(Boolean.class);
        wrapperTypes.add(Character.class);
        wrapperTypes.add(Byte.class);
        wrapperTypes.add(Short.class);
        wrapperTypes.add(Integer.class);
        wrapperTypes.add(Long.class);
        wrapperTypes.add(Float.class);
        wrapperTypes.add(Double.class);
        wrapperTypes.add(Void.class);
        return wrapperTypes;
    }

    /**
     * Returns collection of primitive numeric types {@link Collection}
     *
     * @return collection of primitive numeric types {@link Collection}
     */
    private static Collection<Class<?>> getDefaultPrimitiveNumericTypes() {
        final Collection<Class<?>> numericTypes = new HashSet<>();
        numericTypes.add(char.class);
        numericTypes.add(byte.class);
        numericTypes.add(short.class);
        numericTypes.add(int.class);
        numericTypes.add(long.class);
        numericTypes.add(float.class);
        numericTypes.add(double.class);
        numericTypes.add(boolean.class);
        numericTypes.add(void.class);
        return numericTypes;
    }

    /**
     * Default collection of extendable simple types {@link Collection}
     */
    @SuppressWarnings("unchecked")
    private static final Collection<Class<?>> EXTENDABLE_SIMPLE_TYPES = Arrays.asList(
            BigDecimal.class,
            BigInteger.class,
            CharSequence.class,
            Calendar.class,
            Date.class,
            Enum.class

    );
    /**
     * Default collection of final simple types {@link List}
     */
    @SuppressWarnings("unchecked")
    private static final Collection<Class<? extends Serializable>> FINAL_SIMPLE_TYPES = Arrays.asList(
            Class.class,
            URI.class,
            URL.class,
            Locale.class,
            UUID.class
    );

    /**
     * Returns matchable class instance {@link Class} by input argument class type
     *
     * @param expectedClass - initial argument class instance {@link Class}
     * @return matchable class instance {@link Class}
     */
    public static Class<?> getMatchableClass(final Class<?> expectedClass) {
        if (boolean.class.equals(expectedClass)) return Boolean.class;
        if (byte.class.equals(expectedClass)) return Byte.class;
        if (char.class.equals(expectedClass)) return Character.class;
        if (double.class.equals(expectedClass)) return Double.class;
        if (float.class.equals(expectedClass)) return Float.class;
        if (int.class.equals(expectedClass)) return Integer.class;
        if (long.class.equals(expectedClass)) return Long.class;
        if (short.class.equals(expectedClass)) return Short.class;
        return expectedClass;
    }

    /**
     * Returns binary flag based on input class instance {@link Class}
     *
     * @param clazz - initial class instance {@link Class}
     * @return true - if input class is of primitive numeric type, false - otherwise
     */
    public static boolean isPrimitiveNumericType(final Class<?> clazz) {
        return DEFAULT_PRIMITIVE_NUMERIC_TYPES.contains(clazz);
    }

    /**
     * Returns binary flag based on input class instance {@link Class}
     *
     * @param clazz - initial class instance {@link Class}
     * @return true - if input class is of comparable type, false - otherwise
     */
    public static boolean isComparableType(final Class<?> clazz) {
        return Comparable.class.isAssignableFrom(clazz);
    }

    public static boolean isSimpleType(final Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            return false;
        } else if (isPrimitiveType(clazz) || isPrimitiveWrapperType(clazz)) {
            return true;
        }
        return FINAL_SIMPLE_TYPES.stream().anyMatch(type -> type.equals(clazz))
                || EXTENDABLE_SIMPLE_TYPES.stream().anyMatch(type -> type.isAssignableFrom(clazz));
    }

    /**
     * Returns binary flag based on input argument class instance {@link Class}
     *
     * @param clazz - input argument class instance {@link Class}
     * @return true - if input class is of primitive type, false - otherwise
     */
    public static boolean isPrimitiveType(final Class<?> clazz) {
        return Objects.nonNull(clazz) && clazz.isPrimitive();
    }

    /**
     * Returns binary flag based on input argument class instance {@link Class}
     *
     * @param clazz - input argument class instance {@link Class}
     * @return true - if input class is of primitive wrapper type, false - otherwise
     */
    public static boolean isPrimitiveWrapperType(final Class<?> clazz) {
        return Objects.nonNull(clazz) && DEFAULT_PRIMITIVE_WRAPPER_TYPES.contains(clazz);
    }

    /**
     * Returns initialized instance by input argument class instance {@link Class}
     *
     * @param clazz - input argument class instance {@link Class}
     * @param <T>
     * @return initialized instance by class {@link Class}
     */
    public static <T> T instanceOf(final Class<T> clazz) {
        if (Objects.isNull(clazz)) {
            return null;
        }
        final Constructor<T> constructor;
        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (final NoSuchMethodException e) {
            log.error("ERROR: missing default constructor for type {}. Assuming standard default values for primitive properties.", clazz.getName());
            return null;
        }
        final boolean accessibility = constructor.isAccessible();
        try {
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            constructor.setAccessible(accessibility);
        }
    }

    public static Set<Class<?>> typesOf(final Object... values) {
        final Object[] traversableType = Optional.ofNullable(values).orElseGet(() -> new Object[]{});
        final Set<Class<?>> types = new HashSet<>(traversableType.length);
        Stream.of(traversableType).filter(Objects::nonNull).forEach(type -> types.add(type.getClass()));
        return types;
    }

    /**
     * Returns binary flag based on input iterable collection of argument types {@link Iterable} and shared type instance {@link Class}
     *
     * @param sharedType - input shared type instance {@link Class}
     * @param types      - input iterable collection of input argument types {@link Iterable}
     * @return true - if shared type is assigned by input collection of argument types, fales - otherwise
     */
    public static boolean allAssignableFrom(final Class<?> sharedType, final Iterable<? extends Class<?>> types) {
        final Iterable<? extends Class<?>> traversableType = Optional.ofNullable(types).orElseGet(Collections::emptyList);
        return !StreamSupport.stream(traversableType.spliterator(), false).anyMatch(type -> !sharedType.isAssignableFrom(type));
    }

    /**
     * Returns property value of an object
     *
     * @param value        - initial argument {@link Object} to get property value from
     * @param propertyName - initial property name {@link String}
     * @return property value of input object {@link Object}
     */
    public static Object getProperty(final Object value, final String propertyName) {
        return getProperty(value, StringUtils.sanitize(propertyName), Object.class);
    }

    /**
     * Returns property value of an object argument by name {@link String}
     *
     * @param <T>
     * @param value        - initial argument to get property value from
     * @param propertyName - initial property name {@link String}
     * @param clazz        - initial class to be casted to {@link Class}
     * @return property value
     */
    public static <T> T getProperty(final T value, final String propertyName, final Class<? extends T> clazz) {
        try {
            return castSafe(BeanUtils.getProperty(value, propertyName), clazz);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(String.format("ERROR: cannot access property = {%s}, {%s}, argument: {%s}, message = ${%s}", propertyName, value, ex.getMessage()));
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(String.format("ERROR: cannot get value of property = {%s}, argument: {%s}, message = ${%s}", propertyName, value, ex.getMessage()));
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(String.format("ERROR: cannot get value of property = {%s}, argument: {%s}, message = ${%s}", propertyName, value, ex.getMessage()));
        }
    }

    /**
     * Returns null-safe argument cast {@link Class}
     *
     * @param <T>
     * @param value - initial argument to be casted {@link Object}
     * @param clazz - initial class to be casted to {@link Class}
     * @return casted object
     */
    public static <T> T castSafe(final Object value, final Class<? extends T> clazz) {
        Objects.requireNonNull(clazz);
        return clazz.isInstance(value) ? clazz.cast(value) : null;
    }

    /**
     * Returns type of the supplied argument {@link Member}
     * or {@link Method}
     *
     * @param member - initial argument to reflect on {@link Member}
     * @return type of the supplied argument {@link Member}
     */
    public static Type typeOf(final Member member) {
        Objects.requireNonNull(member);
        if (member instanceof Field) {
            return ((Field) member).getGenericType();
        }
        if (member instanceof Method) {
            return ((Method) member).getGenericReturnType();
        }
        throw new IllegalArgumentException(String.format("ERROR: no such class member = {%s}, neither a field nor a method", member));
    }

    /**
     * Returns list of super-classes {@link List} of the supplied class {@link Class}
     *
     * @param clazz - class to reflect on {@link Class}
     * @return list of super-classes of the supplied class {@link Class}
     */
    public static List<Class<?>> getAllSuperclasses(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        final List<Class<?>> classes = new ArrayList<>();
        Class<?> superclass = clazz.getSuperclass();
        while (Objects.nonNull(superclass)) {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return classes;
    }

    /**
     * Returns an array of all fields {@link Field} of the supplied class. Union of
     * {@link Class#getDeclaredFields()} which ignores and super-classes, and
     * {@link Class#getFields()} which ignored non-public fields
     *
     * @param clazz - initial class to reflect on {@link Class}
     * @return array of fields {@link Field} of the supplied class
     */
    public static Field[] getAllFields(final Class<?> clazz) {
        final List<Class<?>> classes = getAllSuperclasses(clazz);
        classes.add(clazz);
        return getAllFields(classes);
    }

    /**
     * Returns array of fields {@link #getAllFields(Class)} but acts on a
     * list of {@link Class}s and uses only {@link Class#getDeclaredFields()}.
     *
     * @param classes - collection of classes to reflect on {@link List}
     * @return array of fields {@link Field} of the supplied list of classes
     */
    private static Field[] getAllFields(final List<Class<?>> classes) {
        final Set<Field> fields = new HashSet<>();
        Optional.of(classes).orElseGet(Collections::emptyList).forEach(clazz -> fields.addAll(Arrays.asList(clazz.getDeclaredFields())));
        return fields.toArray(new Field[fields.size()]);
    }

    /**
     * Returns collection of fields {@link Field} filtered by static/final/accessible field modifiers
     *
     * @param fields                  - initial array of fields {@link Field} to be filtered on
     * @param includeFinalFields      - initial flag to include/exclude final fields (true - to include final fields, false - otherwise)
     * @param includeAccessibleFields - initial flag to include/exclude accessible fields (true - to include accessible fields, false - otherwise)
     * @return collection of fields {@link Field}
     */
    public static List<Field> getValidFields(final Field[] fields, boolean includeFinalFields, boolean includeAccessibleFields) {
        return Arrays.stream(fields)
                .filter(field -> isNotStaticOrFinalOrAccessible(field, includeFinalFields, includeAccessibleFields))
                .collect(Collectors.toList());
    }

    /**
     * Returns binary flag based on static/final field {@link Field} modifier
     *
     * @param field              - initial field {@link Field} to be checked
     * @param includeFinalFields - initial flag to include/exclude final fields (true - to include final fields, false - otherwise)
     * @return true - if field is non-static/final, false - otherwise
     */
    public static boolean isNotStaticOrFinal(final Field field, boolean includeFinalFields) {
        return !Modifier.isStatic(field.getModifiers()) && (includeFinalFields || !Modifier.isFinal(field.getModifiers()));
    }

    /**
     * Returns binary flag based on static/final field {@link Field} modifier
     *
     * @param field                   - initial field {@link Field} to be checked
     * @param includeFinalFields      - initial flag to include/exclude final fields (true - to include final fields, false - otherwise)
     * @param includeAccessibleFields - initial flag to include/exclude accessible fields (true - to include accessible fields, false - otherwise)
     * @return true - if field is non-static/final/accessible, false - otherwise
     */
    public static boolean isNotStaticOrFinalOrAccessible(final Field field, boolean includeFinalFields, boolean includeAccessibleFields) {
        return isNotStaticOrFinal(field, includeFinalFields) && (!includeAccessibleFields || field.isAccessible());
    }

    /**
     * Returns method type instance {@link ReflectionMethodType}
     *
     * @return class instance by input type
     */
    public static ReflectionMethodType getMethodType(final String methodName, int numberOfParameters, int typedParameter) {
        return new ReflectionMethodType(methodName, numberOfParameters, typedParameter);
    }

    /**
     * Custom reflection method type implementation
     */
    @Data
    @EqualsAndHashCode
    @ToString
    public static class ReflectionMethodType {

        private final String methodName;
        private final int numberOfParameters;
        private final int typeParameter;

        public ReflectionMethodType(final String methodName, int numberOfParameters, int typedParameter) {
            this.methodName = methodName;
            this.numberOfParameters = numberOfParameters;
            this.typeParameter = typedParameter;
        }

        public Class<?> getType(final Class<?> clazz) {
            for (Class c = clazz; c != Object.class; c = c.getSuperclass()) {
                final Optional<Method> methodOptional = Stream.of(c.getDeclaredMethods()).filter(method -> hasSignature(method)).findFirst();
                if (methodOptional.isPresent()) {
                    return getParameterType(methodOptional.get());
                }
            }
            throw new RuntimeException(String.format("ERROR: cannot determine correct type for method={%s}", getMethodName()));
        }

        /**
         * Return binary flag depending on initial method signature {@link Method}
         *
         * @param method - initial method instance {@link Method}
         * @return true - if method signature matches, false - otherwise
         */
        private boolean hasSignature(final Method method) {
            return method.getName().equals(getMethodName())
                    && (method.getParameterTypes().length == getNumberOfParameters())
                    && !method.isSynthetic();
        }

        /**
         * Returns method input parameter type {@link Class}
         *
         * @param method - initial method instance {@link Method}
         * @return parameter type {@link Class}
         */
        private Class<?> getParameterType(final Method method) {
            return method.getParameterTypes()[getTypeParameter()];
        }
    }
}
