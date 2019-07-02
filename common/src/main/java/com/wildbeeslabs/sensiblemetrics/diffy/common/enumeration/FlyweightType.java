/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration;

import lombok.NonNull;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.namespace.QName;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.net.Proxy;
import java.nio.charset.CodingErrorAction;
import java.util.*;
import java.util.logging.Logger;

/**
 * Default flyweight type {@link Enum}
 */
public enum FlyweightType {
    /**
     * java.lang.Enum
     */
    ENUM(Enum.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return true;
        }
    },
    /**
     * java.lang.Class
     */
    CLASS(Class.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return true;
        }
    },
    // XXX There is no nullipotent way of determining the interned status of a string
    // There are numerous String constants within the JDK (see list at http://docs.oracle.com/javase/7/docs/api/constant-values.html),
    // but enumerating all of them would lead to lots of == tests.
    //STRING(String.class) {
    //    @Override
    //    boolean isShared(final Object obj) { return obj == ((String)obj).intern(); }
    //},
    /**
     * java.lang.Boolean
     */
    BOOLEAN(Boolean.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return obj == Boolean.TRUE || obj == Boolean.FALSE;
        }
    },
    /**
     * java.lang.Integer
     */
    INTEGER(Integer.class) {
        @Override
        protected boolean isShared(final Object obj) {
            int value = (Integer) obj;
            return value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE && obj == Integer.valueOf(value);
        }
    },
    /**
     * java.lang.Short
     */
    SHORT(Short.class) {
        @Override
        protected boolean isShared(final Object obj) {
            short value = (Short) obj;
            return value >= Short.MIN_VALUE && value <= Short.MAX_VALUE && obj == Short.valueOf(value);
        }
    },
    /**
     * java.lang.Byte
     */
    BYTE(Byte.class) {
        @Override
        protected boolean isShared(final Object obj) {
            byte value = (Byte) obj;
            return value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE && obj == Byte.valueOf((Byte) obj);
        }
    },
    /**
     * java.lang.Long
     */
    LONG(Long.class) {
        @Override
        protected boolean isShared(final Object obj) {
            long value = (Long) obj;
            return value >= Long.MIN_VALUE && value <= Long.MAX_VALUE && obj == Long.valueOf(value);
        }
    },
    /**
     * java.math.BigInteger
     */
    BIGINTEGER(BigInteger.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return obj == BigInteger.ZERO || obj == BigInteger.ONE || obj == BigInteger.TEN;
        }
    },
    /**
     * java.math.BigDecimal
     */
    BIGDECIMAL(BigDecimal.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return obj == BigDecimal.ZERO || obj == BigDecimal.ONE || obj == BigDecimal.TEN;
        }
    },
    /**
     * java.math.MathContext
     */
    MATHCONTEXT(MathContext.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return obj == MathContext.UNLIMITED || obj == MathContext.DECIMAL32 || obj == MathContext.DECIMAL64 || obj == MathContext.DECIMAL128;
        }
    },
    /**
     * java.lang.Character
     */
    CHARACTER(Character.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return (Character) obj <= Byte.MAX_VALUE && obj == Character.valueOf((Character) obj);
        }
    },
    /**
     * java.lang.Locale
     */
    LOCALE(Locale.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return obj instanceof Locale && GLOBAL_LOCALES.contains(obj);
        }
    },
    /**
     * java.util.Logger
     */
    LOGGER(Logger.class) {
        @Override
        @SuppressWarnings("deprecation")
        protected boolean isShared(final Object obj) {
            return obj == Logger.global;
        }
    },
    /**
     * java.net.Proxy
     */
    PROXY(Proxy.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return obj == Proxy.NO_PROXY;
        }
    },
    /**
     * java.nio.charset.CodingErrorAction
     */
    CODINGERRORACTION(CodingErrorAction.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return true;
        }
    },
    /**
     * javax.xml.datatype.DatatypeConstants.Field
     */
    DATATYPECONSTANTS_FIELD(DatatypeConstants.Field.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return true;
        }
    },
    /**
     * javax.xml.namespace.QName
     */
    QNAME(QName.class) {
        @Override
        protected boolean isShared(final Object obj) {
            return obj == DatatypeConstants.DATETIME
                || obj == DatatypeConstants.TIME
                || obj == DatatypeConstants.DATE
                || obj == DatatypeConstants.GYEARMONTH
                || obj == DatatypeConstants.GMONTHDAY
                || obj == DatatypeConstants.GYEAR
                || obj == DatatypeConstants.GMONTH
                || obj == DatatypeConstants.GDAY
                || obj == DatatypeConstants.DURATION
                || obj == DatatypeConstants.DURATION_DAYTIME
                || obj == DatatypeConstants.DURATION_YEARMONTH;
        }
    },
    /**
     * misc comparisons that can not rely on the object's class.
     */
    MISC(Void.class) {
        @Override
        protected boolean isShared(final Object obj) {
            boolean emptyCollection = obj == Collections.EMPTY_SET || obj == Collections.EMPTY_LIST || obj == Collections.EMPTY_MAP;
            boolean systemStream = obj == System.in || obj == System.out || obj == System.err;
            return emptyCollection || systemStream || obj == String.CASE_INSENSITIVE_ORDER;
        }
    };

    private static final Map<Class<?>, FlyweightType> TYPE_MAPPINGS = new HashMap<>();

    static {
        for (final FlyweightType type : FlyweightType.values()) {
            TYPE_MAPPINGS.put(type.clazz, type);
        }
    }

    private static final Set<Locale> GLOBAL_LOCALES;

    static {
        final Map<Locale, Void> locales = new IdentityHashMap<>();
        for (final Field f : Locale.class.getFields()) {
            final int modifiers = f.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Locale.class.equals(f.getType())) {
                try {
                    locales.put((Locale) f.get(null), null);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
            }
        }
        GLOBAL_LOCALES = locales.keySet();
    }

    private final Class<?> clazz;

    FlyweightType(final Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * Whether this is a shared object
     *
     * @param obj the object to check for
     * @return true, if shared
     */
    protected abstract boolean isShared(final Object obj);

    /**
     * Will return the Flyweight enum instance for the flyweight Class, or null if type isn't flyweight
     *
     * @param aClazz the class we need the FlyweightType instance for
     * @return the FlyweightType, or null
     */
    @NonNull
    public static FlyweightType getFlyweightType(final Class<?> aClazz) {
        if (aClazz.isEnum() || (Objects.nonNull(aClazz.getSuperclass()) && aClazz.getSuperclass().isEnum())) {
            return ENUM;
        }
        return Optional.ofNullable(TYPE_MAPPINGS.get(aClazz)).orElse(MISC);
    }
}
