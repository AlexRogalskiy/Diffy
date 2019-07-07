package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import java.lang.reflect.*;

/**
 * This class contains various utilities for ensuring
 * java type safety
 *
 * @author jwells
 */
public class TypeChecker {

    public static WildcardType getWildcard(Type type) {
        if (type == null) return null;
        if (type instanceof WildcardType) {
            return (WildcardType) type;
        }
        return null;
    }

    public static TypeVariable<?> getTypeVariable(Type type) {
        if (type == null) return null;
        if (type instanceof TypeVariable) {
            return (TypeVariable<?>) type;
        }
        return null;
    }

    public static boolean isWildcard(Type type) {
        if (type == null) return false;
        return (type instanceof WildcardType);
    }

    private static boolean isTypeVariable(Type type) {
        if (type == null) return false;
        return (type instanceof TypeVariable);
    }

    /**
     * An actual type is either a Class or a ParameterizedType
     *
     * @param type The type to test
     * @return true if this is an actual type
     */
    public static boolean isActualType(Type type) {
        if (type == null) return false;
        return ((type instanceof Class) || (type instanceof ParameterizedType));

    }

    /**
     * An array type can be a class that is an array
     * or a GenericArrayType
     *
     * @param type The type to test
     * @return true if this is an actual type
     */
    public static boolean isArrayType(Type type) {
        if (type == null) return false;
        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            return clazz.isArray();
        }
        return (type instanceof GenericArrayType);
    }

    /**
     * An array type can be a class that is an array
     * or a GenericArrayType
     *
     * @param type The type to test
     * @return true if this is an actual type
     */
    public static Type getArrayType(Type type) {
        if (type == null) return null;
        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            return clazz.getComponentType();
        }
        if (type instanceof GenericArrayType) {
            GenericArrayType gat = (GenericArrayType) type;
            return gat.getGenericComponentType();
        }
        return null;
    }
}
