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
package com.wildbeeslabs.sensiblemetrics.diffy.converter.utils;

import com.google.common.collect.ImmutableMap;
import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidFormatException;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.beanutils.ConversionException;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Converter utilities implementation
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class ConverterUtils {

    /**
     * Default converter mappings
     */
    private static final Map<Class<? extends Object>, Converter<?, ? extends Object>> typeTransformers = Collections.unmodifiableMap(createTypeTransformers());
    /**
     * An empty array.  Used to invoke accessors via reflection.
     */
    public static final Object[] NULL_ARGUMENTS = {};

    /**
     * Returns {@link Supplier} by input parameters
     *
     * @param <T>       type of value to convert from
     * @param <R>       type of value to convert to
     * @param supplier  - initial input {@link Supplier}
     * @param converter - initial input {@link Converter}
     * @return {@link Supplier}
     */
    @NonNull
    public static <T, R> Supplier<R> ifSupplierNotNullDo(final Supplier<T> supplier, final Converter<T, R> converter) {
        return () -> {
            final T val = supplier.get();
            if (Objects.isNull(val)) {
                return null;
            }
            return converter.convert(val);
        };
    }

    /**
     * Returns converted value by converter instance {@link Converter}
     *
     * @param <T>       type of input element to be converted from by operation
     * @param <R>       type of input element to be converted to by operation
     * @param value     - initial argument value to be converted
     * @param converter - initial converter to process on {@link Converter}
     * @return converted value
     * @throws NullPointerException if converter is {@code null}
     */
    @Nullable
    public static <T, R> R convert(final T value, final Converter<T, R> converter) {
        ValidationUtils.notNull(converter, "Converter should not be null");
        return converter.convert(value);
    }

    public static <T> T convertTo(final T obj, final Class<T> dest) {
        if (Objects.isNull(obj)) {
            return null;
        }
        if (dest.isAssignableFrom(obj.getClass())) {
            return obj;
        }
        if (dest.isPrimitive()) {
            if (obj instanceof Number) {
                return obj;
            }
            if (dest == int.class) {
                return (T) Integer.valueOf(obj.toString());
            } else if (dest == short.class) {
                return (T) Short.valueOf(obj.toString());
            } else if (dest == long.class) {
                return (T) Long.valueOf(obj.toString());
            } else if (dest == byte.class) {
                return (T) Byte.valueOf(obj.toString());
            } else if (dest == float.class) {
                return (T) Float.valueOf(obj.toString());
            } else if (dest == double.class) {
                return (T) Double.valueOf(obj.toString());
            } else if (dest == char.class) {
                final String asString = dest.toString();
                if (asString.length() > 0) {
                    return (T) Character.valueOf(asString.charAt(0));
                }
            } else if (dest == boolean.class) {
                return obj;
            }
            throw new RuntimeException(String.format("ERROR: cannot convert {%s} to {%s}", obj.getClass().getName(), dest.getName()));
        } else {
            if (dest.isEnum()) {
                return (T) Enum.valueOf((Class<Enum>) dest, obj.toString());
            }
            if (dest == Integer.class) {
                if (obj instanceof Number) {
                    return (T) Integer.valueOf(((Number) obj).intValue());
                }
                return (T) Integer.valueOf(obj.toString());
            }
            if (dest == Long.class) {
                if (obj instanceof Number) {
                    return (T) Long.valueOf(((Number) obj).longValue());
                }
                return (T) Long.valueOf(obj.toString());
            }
            if (dest == Short.class) {
                if (obj instanceof Number) {
                    return (T) Short.valueOf(((Number) obj).shortValue());
                }
                return (T) Short.valueOf(obj.toString());
            }
            if (dest == Byte.class) {
                if (obj instanceof Number) {
                    return (T) Byte.valueOf(((Number) obj).byteValue());
                }
                return (T) Byte.valueOf(obj.toString());
            }
            if (dest == Float.class) {
                if (obj instanceof Number) {
                    return (T) Float.valueOf(((Number) obj).floatValue());
                }
                return (T) Float.valueOf(obj.toString());
            }
            if (dest == Double.class) {
                if (obj instanceof Number) {
                    return (T) Double.valueOf(((Number) obj).doubleValue());
                }
                return (T) Double.valueOf(obj.toString());
            }
            if (dest == Character.class) {
                final String asString = dest.toString();
                if (asString.length() > 0) {
                    return (T) Character.valueOf(asString.charAt(0));
                }
            }
            throw new RuntimeException(String.format("ERROR: cannot convert {%s} to {%s}", obj.getClass().getName(), dest.getName()));
        }
    }

    public static <T> T convertToStrict(final T obj, final Class<T> dest) {
        if (Objects.isNull(obj)) {
            return null;
        }
        if (dest.isAssignableFrom(obj.getClass())) {
            return obj;
        }
        if (dest.isPrimitive()) {
            if (dest == int.class) {
                if (obj instanceof Number) {
                    return obj;
                }
                return (T) Integer.valueOf(obj.toString());
            } else if (dest == short.class) {
                if (obj instanceof Number) {
                    return obj;
                }
                return (T) Short.valueOf(obj.toString());
            } else if (dest == long.class) {
                if (obj instanceof Number) {
                    return obj;
                }
                return (T) Long.valueOf(obj.toString());
            } else if (dest == byte.class) {
                if (obj instanceof Number) {
                    return obj;
                }
                return (T) Byte.valueOf(obj.toString());
            } else if (dest == float.class) {
                if (obj instanceof Number) {
                    return obj;
                }
                return (T) Float.valueOf(obj.toString());
            } else if (dest == double.class) {
                if (obj instanceof Number) {
                    return obj;
                }
                return (T) Double.valueOf(obj.toString());
            } else if (dest == char.class) {
                final String asString = dest.toString();
                if (asString.length() > 0) {
                    return (T) Character.valueOf(asString.charAt(0));
                }
            } else if (dest == boolean.class) {
                return obj;
            }
            throw new RuntimeException(String.format("ERROR: cannot convert {%s} to {%s}", obj.getClass().getName(), dest.getName()));
        } else {
            if (dest.isEnum()) {
                return (T) Enum.valueOf((Class<Enum>) dest, obj.toString());
            }
            if (dest == Integer.class) {
                if (obj instanceof Number) {
                    return (T) Integer.valueOf(((Number) obj).intValue());
                }
                return (T) Integer.valueOf(obj.toString());
            }
            if (dest == Long.class) {
                if (obj instanceof Number) {
                    return (T) Long.valueOf(((Number) obj).longValue());
                }
                return (T) Long.valueOf(obj.toString());
            }
            if (dest == Short.class) {
                if (obj instanceof Number) {
                    return (T) Short.valueOf(((Number) obj).shortValue());
                }
                return (T) Short.valueOf(obj.toString());
            }
            if (dest == Byte.class) {
                if (obj instanceof Number) {
                    return (T) Byte.valueOf(((Number) obj).byteValue());
                }
                return (T) Byte.valueOf(obj.toString());
            }
            if (dest == Float.class) {
                if (obj instanceof Number) {
                    return (T) Float.valueOf(((Number) obj).floatValue());
                }
                return (T) Float.valueOf(obj.toString());
            }
            if (dest == Double.class) {
                if (obj instanceof Number) {
                    return (T) Double.valueOf(((Number) obj).doubleValue());
                }
                return (T) Double.valueOf(obj.toString());
            }
            if (dest == Character.class) {
                final String asString = dest.toString();
                if (asString.length() > 0) {
                    return (T) Character.valueOf(asString.charAt(0));
                }
            }
            throw new RuntimeException(String.format("ERROR: cannot convert {%s} to {%s}", obj.getClass().getName(), dest.getName()));
        }
    }

    private static <T> Map<Class<? extends Object>, Converter<T, ? extends Object>> createTypeTransformers() {
        return ImmutableMap.<Class<? extends Object>, Converter<T, ? extends Object>>builder()
            .put(Boolean.TYPE, (Converter<T, Boolean>) input -> Boolean.valueOf(input.toString()))
            .put(Character.TYPE, (Converter<T, Character>) input -> Character.valueOf(input.toString().charAt(0)))
            .put(Byte.TYPE, (Converter<T, Byte>) input -> Byte.valueOf(input.toString()))
            .put(Short.TYPE, (Converter<T, Short>) input -> Short.valueOf(input.toString()))
            .put(Integer.TYPE, (Converter<T, Integer>) input -> Integer.valueOf(input.toString()))
            .put(Long.TYPE, (Converter<T, Long>) input -> Long.valueOf(input.toString()))
            .put(Float.TYPE, (Converter<T, Float>) input -> Float.valueOf(input.toString()))
            .put(Double.TYPE, (Converter<T, Double>) input -> Double.valueOf(input.toString()))
            .build();
    }

    public static int convertToInteger(final Object obj) {
        if (Objects.isNull(obj)) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        if (obj instanceof String) {
            return Integer.parseInt((String) obj);
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to int");
    }

    public static Integer convertToIntegerWrapper(final Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        final Class<?> c = obj.getClass();
        if (c == Integer.class) {
            return (Integer) obj;
        }
        if (obj instanceof Number) {
            return Integer.valueOf(((Number) obj).intValue());
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Integer");
    }

    public static short convertToShort(final Object obj) {
        if (Objects.isNull(obj)) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).shortValue();
        }
        if (obj instanceof String) {
            return Short.parseShort((String) obj);
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to short");
    }

    public static Short convertToShortWrapper(final Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        final Class<?> c = obj.getClass();
        if (c == Short.class) {
            return (Short) obj;
        }
        if (obj instanceof Number) {
            return Short.valueOf(((Number) obj).shortValue());
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Short");
    }

    public static long convertToLong(final Object obj) {
        if (Objects.isNull(obj)) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        if (obj instanceof String) {
            return Long.parseLong((String) obj);
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to long");
    }

    public static Long convertToLongWrapper(final Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        final Class<?> c = obj.getClass();
        if (c == Long.class) {
            return (Long) obj;
        }
        if (obj instanceof Number) {
            return Long.valueOf(((Number) obj).longValue());
        }
        throw new ConversionException("Primitive: Can not convert value '" + obj + "' As " + obj.getClass().getName() + " to Long");
    }

    public static byte convertToByte(final Object obj) {
        if (Objects.isNull(obj)) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).byteValue();
        }
        if (obj instanceof String) {
            return Byte.parseByte((String) obj);
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to byte");
    }

    public static Byte convertToByteWrapper(final Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        final Class<?> c = obj.getClass();
        if (c == Byte.class) {
            return (Byte) obj;
        }
        if (obj instanceof Number) {
            return Byte.valueOf(((Number) obj).byteValue());
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Byte");
    }

    public static float convertToFloat(final Object obj) {
        if (Objects.isNull(obj)) {
            return 0f;
        }
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        }
        if (obj instanceof String) {
            return Float.parseFloat((String) obj);
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to float");
    }

    public static Float convertToFloatWrapper(final Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        final Class<?> c = obj.getClass();
        if (c == Float.class) {
            return (Float) obj;
        }
        if (obj instanceof Number) {
            return Float.valueOf(((Number) obj).floatValue());
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Float");
    }

    public static double convertToDouble(final Object obj) {
        if (Objects.isNull(obj)) {
            return 0.0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        if (obj instanceof String) {
            return Double.parseDouble((String) obj);
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to float");
    }

    public static Double convertToDoubleWrapper(final Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        final Class<?> c = obj.getClass();
        if (c == Double.class) {
            return (Double) obj;
        }
        if (obj instanceof Number) {
            return Double.valueOf(((Number) obj).doubleValue());
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Float");
    }

    public static char convertToChararacter(final Object obj) {
        if (Objects.isNull(obj)) {
            return ' ';
        }
        if (obj instanceof String) {
            if (((String) obj).length() > 0) {
                return ((String) obj).charAt(0);
            }
            return ' ';
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to char");
    }

    public static Character convertToCharacterWrapper(final Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        final Class<?> c = obj.getClass();
        if (c == Character.class) {
            return (Character) obj;
        }
        if (obj instanceof String) {
            if (((String) obj).length() > 0) {
                return ((String) obj).charAt(0);
            }
            return ' ';
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Character");
    }

    public static boolean convertToBoolean(final Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        }
        if (obj.getClass() == Boolean.class) {
            return ((Boolean) obj).booleanValue();
        }
        if (obj instanceof String) {
            return Boolean.parseBoolean((String) obj);
        }
        if (obj instanceof Number) {
            if (obj.toString().equals("0")) {
                return false;
            }
            return true;
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to boolean");
    }

    public static Boolean convertToBooleanWrapper(final Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        }
        final Class<?> c = obj.getClass();
        if (c == Boolean.class) {
            return (Boolean) obj;
        }
        if (obj instanceof String) {
            return Boolean.parseBoolean((String) obj);
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Boolean");
    }

    public static BigDecimal safeBigDecimal(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static BigInteger safeBigInteger(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            return new BigInteger(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static int safeIntValue(final String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            try {
                return (int) Long.parseLong(value);
            } catch (NumberFormatException nfe) {
                return new BigDecimal(value).intValue();
            }
        }
    }

    public static long safeLongValue(final String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return new BigDecimal(value).longValue();
        }
    }

    public static <T extends Number> Function<? super String, Optional<T>> converterOfType(final Class<T> toType) {
        return (Function<String, Optional<T>>) input -> {
            try {
                if (Integer.class.equals(toType)) {
                    return (Optional<T>) Optional.of(Integer.valueOf(input));
                } else if (Long.class.equals(toType)) {
                    return (Optional<T>) Optional.of(Long.valueOf(input));
                } else if (Double.class.equals(toType)) {
                    return (Optional<T>) Optional.of(Double.valueOf(input));
                } else if (Float.class.equals(toType)) {
                    return (Optional<T>) Optional.of(Float.valueOf(input));
                } else if (Byte.class.equals(toType)) {
                    return (Optional<T>) Optional.of(Byte.valueOf(input));
                } else if (Short.class.equals(toType)) {
                    return (Optional<T>) Optional.of(Short.valueOf(input));
                } else if (BigDecimal.class.equals(toType)) {
                    return (Optional<T>) Optional.of(safeBigDecimal(input));
                } else if (BigInteger.class.equals(toType)) {
                    return (Optional<T>) Optional.of(safeBigInteger(input));
                }
            } catch (NumberFormatException ignored) {
            }
            return Optional.empty();
        };
    }

    /**
     * Returns a transformer for the given primitive type.
     *
     * @param aType the primitive type whose transformer to return
     * @return a transformer that will convert strings into that type,
     * or null if the given type is not a primitive type
     */
    public static <T> Converter<T, ? extends Object> getTypeTransformer(final Class<? extends Object> aType) {
        return (Converter<T, ? extends Object>) typeTransformers.get(aType);
    }

    public static <T> Object convertType(final Class<?> newType, final T value) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ValidationUtils.notNull(newType, "Type should not be null");
        final Class<?>[] types = {value.getClass()};
        try {
            final Constructor<?> constructor = newType.getConstructor(types);
            final Object[] arguments = {value};
            return constructor.newInstance(arguments);
        } catch (NoSuchMethodException e) {
            return Optional.ofNullable(getTypeTransformer(newType)).map(c -> c.convert(value)).orElse(null);
        }
    }

    public static <T> Double toDouble(final T value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (Double.class.isAssignableFrom(value.getClass())) {
            return (Double) value;
        } else if (Integer.class.isAssignableFrom(value.getClass())) {
            return ((Integer) value).doubleValue();
        } else if (Long.class.isAssignableFrom(value.getClass())) {
            return ((Long) value).doubleValue();
        } else if (BigDecimal.class.isAssignableFrom(value.getClass())) {
            return ((BigDecimal) value).doubleValue();
        } else if (Float.class.isAssignableFrom(value.getClass())) {
            return ((Float) value).doubleValue();
        } else if (String.class.isAssignableFrom(value.getClass())) {
            return Double.valueOf(value.toString());
        }
        throw new InvalidFormatException(String.format("ERROR: cannot parse value = {%s} to {%s}", value, Double.class.getName()));
    }

    public static <T> Long toLong(final T value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (Long.class.isAssignableFrom(value.getClass())) {
            return (Long) value;
        } else if (Integer.class.isAssignableFrom(value.getClass())) {
            return ((Integer) value).longValue();
        } else if (Double.class.isAssignableFrom(value.getClass())) {
            return ((Double) value).longValue();
        } else if (BigDecimal.class.isAssignableFrom(value.getClass())) {
            return ((BigDecimal) value).longValue();
        } else if (Float.class.isAssignableFrom(value.getClass())) {
            return ((Float) value).longValue();
        } else if (String.class.isAssignableFrom(value.getClass())) {
            return Long.valueOf(value.toString());
        }
        throw new InvalidFormatException(String.format("ERROR: cannot parse value = {%s} to {%s}", value, Long.class.getName()));
    }

    public static <T> Integer toInteger(final T value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (Integer.class.isAssignableFrom(value.getClass())) {
            return (Integer) value;
        } else if (Long.class.isAssignableFrom(value.getClass())) {
            return ((Long) value).intValue();
        } else if (Double.class.isAssignableFrom(value.getClass())) {
            return ((Double) value).intValue();
        } else if (BigDecimal.class.isAssignableFrom(value.getClass())) {
            return ((BigDecimal) value).intValue();
        } else if (Float.class.isAssignableFrom(value.getClass())) {
            return ((Float) value).intValue();
        } else if (String.class.isAssignableFrom(value.getClass())) {
            return Integer.valueOf(value.toString());
        }
        throw new InvalidFormatException(String.format("ERROR: cannot parse value = {%s} to {%s}", value, Integer.class.getName()));
    }

    public static <T> Float toFloat(final T value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (Float.class.isAssignableFrom(value.getClass())) {
            return (Float) value;
        } else if (Integer.class.isAssignableFrom(value.getClass())) {
            return ((Integer) value).floatValue();
        } else if (Long.class.isAssignableFrom(value.getClass())) {
            return ((Long) value).floatValue();
        } else if (BigDecimal.class.isAssignableFrom(value.getClass())) {
            return ((BigDecimal) value).floatValue();
        } else if (Double.class.isAssignableFrom(value.getClass())) {
            return ((Double) value).floatValue();
        } else if (String.class.isAssignableFrom(value.getClass())) {
            return Float.valueOf(value.toString());
        }
        throw new InvalidFormatException(String.format("ERROR: cannot parse value = {%s} to {%s}", value, Float.class.getName()));
    }

    public static <T> BigDecimal toBigDecimal(final T value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return new BigDecimal(value.toString());
    }

    public static <T> Boolean toBoolean(final T value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (Boolean.class.isAssignableFrom(value.getClass())) {
            return (Boolean) value;
        }
        throw new InvalidFormatException(String.format("ERROR: cannot parse value = {%s} to {%s}", value, Boolean.class.getName()));
    }

    public static <T> Date toDate(final T value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (Date.class.isAssignableFrom(value.getClass())) {
            return (Date) value;
        } else if (Long.class.isAssignableFrom(value.getClass())) {
            return new Date((Long) value);
        } else if (String.class.isAssignableFrom(value.getClass())) {
            try {
                return DateFormat.getInstance().parse(value.toString());
            } catch (ParseException e) {
                throw new InvalidFormatException(e);
            }
        }
        throw new InvalidFormatException(String.format("ERROR: cannot parse value = {%s} to {%s}", value, Date.class.getName()));
    }

    public static <T> BigInteger toBigInteger(final T value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return new BigInteger(value.toString());
    }

    public static boolean convertToBoolean(String prop, Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }
        if (value instanceof String) {
            String str = (String) value;
            if (str.equalsIgnoreCase("false")) {
                return false;
            }
            if (str.equalsIgnoreCase("true")) {
                return true;
            }
            throw new IllegalArgumentException("Invalid String value for property '" + prop + "': expected Boolean value.");
        }
        throw new IllegalArgumentException("Invalid value type (" + value.getClass() + ") for property '" + prop + "': expected Boolean value.");
    }

    public static int convertToInt(final String prop, final Object value, int minValue) {
        int i;
        if (Objects.isNull(value)) {
            i = 0;
        } else if (value instanceof Number) {
            i = ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                i = Integer.parseInt((String) value);
            } catch (NumberFormatException nex) {
                throw new IllegalArgumentException("Invalid String value for property '" + prop + "': expected a number (Integer).");
            }
        } else {
            throw new IllegalArgumentException("Invalid value type (" + value.getClass() + ") for property '" + prop + "': expected Integer value.");
        }

        if (i < minValue) {
            throw new IllegalArgumentException("Invalid numeric value (" + i
                + ") for property '" + prop
                + "': minimum is " + minValue + ".");
        }
        return i;
    }

    public static long convertToLong(final String prop, final Object value, long minValue) {
        long i;
        if (Objects.isNull(value)) {
            i = 0;
        } else if (value instanceof Number) {
            i = ((Number) value).longValue();
        } else if (value instanceof String) {
            try {
                i = Long.parseLong((String) value);
            } catch (NumberFormatException nex) {
                throw new IllegalArgumentException("Invalid String value for property '" + prop + "': expected a number (Long).");
            }
        } else {
            throw new IllegalArgumentException("Invalid value type (" + value.getClass() + ") for property '" + prop + "': expected Long value.");
        }

        if (i < minValue) {
            throw new IllegalArgumentException("Invalid numeric value (" + i
                + ") for property '" + prop
                + "': minimum is " + minValue + ".");
        }
        return i;
    }

    public static Class<?> getEnumType(final Class<?> targetType) {
        Class<?> enumType = targetType;
        while (Objects.nonNull(enumType) && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        ValidationUtils.notNull(enumType, "The target type " + targetType.getName() + " does not refer to an enum");
        return enumType;
    }
}
