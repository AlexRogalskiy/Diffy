package com.wildbeeslabs.sensiblemetrics.diffy.core.property.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CamelCaseUtil {
    private static final char SEPARATOR = '_';

    public static String toUnderline(final String s) {
        if (s == null) return null;
        char c;
        boolean upperCase = false;
        boolean nextUpperCase = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            nextUpperCase = true;

            if (i < s.length() - 1) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if (i >= 0 && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) sb.append(SEPARATOR);
                }

                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    public static String toCamelCase(final String s) {
        return toCamelCase(s, false);
    }

    public static String toCamelCase(final String value, final boolean space) {
        if (value == null) return null;
        String s = value.toLowerCase();

        char c;
        boolean upperCase = false;
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
                if (space) sb.append(' ');
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String toCapitalizedCamelCase(final String s) {
        return toCapitalizedCamelCase(s, false);
    }

    public static String toCapitalizedCamelCase(final String value, final boolean space) {
        if (value == null) return null;
        String s = toCamelCase(value, space);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
