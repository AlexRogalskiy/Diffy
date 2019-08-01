package com.wildbeeslabs.sensiblemetrics.diffy.validator.utils;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class ExpressionUtils {

    public static boolean isBlank(final Object o) {
        return o == null || (o instanceof String && ((String) o).length() == 0);
    }

    public static boolean isNonBlankData(final Object o) {
        return
            o != null && (!(o instanceof String) || ((String) o).length() > 0);
    }

    public static boolean isTrue(final Object o) {
        return o != null &&
            (o instanceof Boolean ?
                ((Boolean) o).booleanValue() :
                Boolean.parseBoolean(o.toString()));
    }

    public static boolean sameValue(final Object v1, final Object v2) {
        if (v1 == null) {
            return (v2 == null)
                || (v2 instanceof String && ((String) v2).length() == 0);
        } else if (v2 == null) {
            return (v1 == null)
                || (v1 instanceof String && ((String) v1).length() == 0);
        } else {
            return v1.equals(v2);
        }
    }

    public static boolean isArray(final Object v) {
        return v != null && v.getClass().isArray();
    }

    public static boolean isArrayOrCollection(final Object v) {
        return v != null && (v.getClass().isArray() || v instanceof Collection<?>);
    }

    public static boolean isArrayOrList(final Object v) {
        return v != null && (v.getClass().isArray() || v instanceof List<?>);
    }

    @SuppressWarnings("unchecked")
    public static List<Object> toObjectList(final Object v) {
        return (List<Object>) v;
    }

    @SuppressWarnings("unchecked")
    public static Collection<Object> toObjectCollection(final Object v) {
        return (Collection<Object>) v;
    }
}
