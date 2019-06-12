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

import lombok.experimental.UtilityClass;
import org.apache.commons.beanutils.ConversionException;

import java.util.Objects;

/**
 * Converter utilities implementation
 */
@UtilityClass
public class ConverterUtils {

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
        if (obj == null)
            return 0;
        if (obj instanceof Number)
            return ((Number) obj).intValue();
        if (obj instanceof String)
            return Integer.parseInt((String) obj);
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to int");
    }

    public static Integer convertToInt(final Object obj) {
        if (obj == null)
            return null;
        Class<?> c = obj.getClass();
        if (c == Integer.class)
            return (Integer) obj;
        if (obj instanceof Number)
            return Integer.valueOf(((Number) obj).intValue());
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Integer");
    }

    public static short convertToshort(final Object obj) {
        if (obj == null)
            return 0;
        if (obj instanceof Number)
            return ((Number) obj).shortValue();
        if (obj instanceof String)
            return Short.parseShort((String) obj);
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to short");
    }

    public static Short convertToShort(final Object obj) {
        if (obj == null)
            return null;
        Class<?> c = obj.getClass();
        if (c == Short.class)
            return (Short) obj;
        if (obj instanceof Number)
            return Short.valueOf(((Number) obj).shortValue());
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Short");
    }

    public static long convertTolong(final Object obj) {
        if (obj == null)
            return 0;
        if (obj instanceof Number)
            return ((Number) obj).longValue();
        if (obj instanceof String)
            return Long.parseLong((String) obj);
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to long");
    }

    public static Long convertToLong(final Object obj) {
        if (obj == null)
            return null;
        Class<?> c = obj.getClass();
        if (c == Long.class)
            return (Long) obj;
        if (obj instanceof Number)
            return Long.valueOf(((Number) obj).longValue());
        throw new ConversionException("Primitive: Can not convert value '" + obj + "' As " + obj.getClass().getName() + " to Long");
    }

    public static byte convertTobyte(final Object obj) {
        if (obj == null)
            return 0;
        if (obj instanceof Number)
            return ((Number) obj).byteValue();
        if (obj instanceof String)
            return Byte.parseByte((String) obj);
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to byte");
    }

    public static Byte convertToByte(final Object obj) {
        if (obj == null)
            return null;
        Class<?> c = obj.getClass();
        if (c == Byte.class)
            return (Byte) obj;
        if (obj instanceof Number)
            return Byte.valueOf(((Number) obj).byteValue());
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Byte");
    }

    public static float convertTofloat(final Object obj) {
        if (obj == null)
            return 0f;
        if (obj instanceof Number)
            return ((Number) obj).floatValue();
        if (obj instanceof String)
            return Float.parseFloat((String) obj);
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to float");
    }

    public static Float convertToFloat(final Object obj) {
        if (obj == null)
            return null;
        Class<?> c = obj.getClass();
        if (c == Float.class)
            return (Float) obj;
        if (obj instanceof Number)
            return Float.valueOf(((Number) obj).floatValue());
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Float");
    }

    public static double convertTodouble(final Object obj) {
        if (obj == null)
            return 0.0;
        if (obj instanceof Number)
            return ((Number) obj).doubleValue();
        if (obj instanceof String)
            return Double.parseDouble((String) obj);
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to float");
    }

    public static Double convertToDouble(final Object obj) {
        if (obj == null)
            return null;
        Class<?> c = obj.getClass();
        if (c == Double.class)
            return (Double) obj;
        if (obj instanceof Number)
            return Double.valueOf(((Number) obj).doubleValue());
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Float");
    }

    public static char convertTochar(final Object obj) {
        if (obj == null)
            return ' ';
        if (obj instanceof String)
            if (((String) obj).length() > 0)
                return ((String) obj).charAt(0);
            else
                return ' ';
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to char");
    }

    public static Character convertToChar(final Object obj) {
        if (obj == null)
            return null;
        Class<?> c = obj.getClass();
        if (c == Character.class)
            return (Character) obj;
        if (obj instanceof String)
            if (((String) obj).length() > 0)
                return ((String) obj).charAt(0);
            else
                return ' ';
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Character");
    }

    public static boolean convertTobool(final Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() == Boolean.class)
            return ((Boolean) obj).booleanValue();
        if (obj instanceof String)
            return Boolean.parseBoolean((String) obj);
        if (obj instanceof Number) {
            if (obj.toString().equals("0"))
                return false;
            else
                return true;
        }
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to boolean");
    }

    public static Boolean convertToBool(final Object obj) {
        if (obj == null)
            return null;
        Class<?> c = obj.getClass();
        if (c == Boolean.class)
            return (Boolean) obj;
        if (obj instanceof String)
            return Boolean.parseBoolean((String) obj);
        throw new ConversionException("Primitive: Can not convert " + obj.getClass().getName() + " to Boolean");
    }
}
