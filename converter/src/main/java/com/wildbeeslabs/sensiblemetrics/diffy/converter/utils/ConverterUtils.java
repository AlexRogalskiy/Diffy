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

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.beanutils.ConversionException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Converter utilities implementation
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class ConverterUtils {

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

    public static int convertToint(final Object obj) {
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

    public static Integer convertToInt(final Object obj) {
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

    public static short convertToshort(final Object obj) {
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

    public static Short convertToShort(final Object obj) {
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

    public static long convertTolong(final Object obj) {
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

    public static Long convertToLong(final Object obj) {
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

    public static byte convertTobyte(final Object obj) {
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

    public static Byte convertToByte(final Object obj) {
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

    public static float convertTofloat(final Object obj) {
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

    public static Float convertToFloat(final Object obj) {
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

    public static double convertTodouble(final Object obj) {
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

    public static Double convertToDouble(final Object obj) {
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

    public static char convertTochar(final Object obj) {
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

    public static Character convertToChar(final Object obj) {
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

    public static boolean convertTobool(final Object obj) {
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

    public static Boolean convertToBool(final Object obj) {
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
            return Optional.absent();
        };
    }
}
