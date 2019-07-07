package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.cache.interfaces.WeakHashClock;
import com.wildbeeslabs.sensiblemetrics.diffy.common.cache.interfaces.WeakHashLRU;

import java.lang.reflect.Array;

/**
 * This class contains utilities useful for any code
 *
 * @author jwells
 */
public class GeneralUtilities {
    /**
     * Returns true if a is equals to b, or both
     * and and b are null.  Is safe even if
     * a or b is null.  If a or b is null but
     * the other is not null, this returns false
     *
     * @param a A possibly null object to compare
     * @param b A possibly null object to compare
     * @return true if equal, false if not
     */
    public static boolean safeEquals(Object a, Object b) {
        if (a == b) return true;
        if (a == null) return false;
        if (b == null) return false;

        return a.equals(b);
    }

    private static Class<?> loadArrayClass(ClassLoader cl, String aName) {
        Class<?> componentType = null;
        int[] dimensions = null;

        int dot = 0;
        while (componentType == null) {
            char dotChar = aName.charAt(dot);
            if (dotChar == '[') {
                dot++;
                continue;
            }

            dimensions = new int[dot];
            for (int lcv = 0; lcv < dot; lcv++) {
                dimensions[lcv] = 0;
            }

            if (dotChar == 'B') {
                componentType = byte.class;
            } else if (dotChar == 'I') {
                componentType = int.class;
            } else if (dotChar == 'J') {
                componentType = long.class;
            } else if (dotChar == 'Z') {
                componentType = boolean.class;
            } else if (dotChar == 'S') {
                componentType = short.class;
            } else if (dotChar == 'C') {
                componentType = char.class;
            } else if (dotChar == 'D') {
                componentType = double.class;
            } else if (dotChar == 'F') {
                componentType = float.class;
            } else {
                if (dotChar != 'L') {
                    throw new IllegalArgumentException("Unknown array type " + aName);
                }

                if (aName.charAt(aName.length() - 1) != ';') {
                    throw new IllegalArgumentException("Badly formed L array expresion: " + aName);
                }

                String cName = aName.substring(dot + 1, aName.length() - 1);

                componentType = loadClass(cl, cName);
                if (componentType == null) return null;
            }
        }

        Object retArray = Array.newInstance(componentType, dimensions);
        return retArray.getClass();
    }

    /**
     * Loads the class from the given classloader or returns null (does not throw).
     * Property handles array classes as well
     *
     * @param cl    The non-null classloader to load the class from
     * @param cName The fully qualified non-null name of the class to load
     * @return The class if it could be loaded from the classloader, or
     * null if it could not be found for any reason
     */
    public static Class<?> loadClass(ClassLoader cl, String cName) {
        if (cName.startsWith("[")) {
            return loadArrayClass(cl, cName);
        }

        try {
            return cl.loadClass(cName);
        } catch (Throwable th) {
            return null;
        }
    }

    /**
     * Creates a weak hash clock
     *
     * @param isWeak if true this will keep weak keyes, if false the keys will
     *               be hard and will not go away even if they do not exist anywhere else
     *               but this cache
     * @return A weak hash clock implementation
     */
    public static <K, V> WeakHashClock<K, V> getWeakHashClock(boolean isWeak) {
        return new WeakHashClockImpl<K, V>(isWeak);
    }

    /**
     * Creates a weak hash clock
     *
     * @param isWeak if true this will keep weak keyes, if false the keys will
     *               be hard and will not go away even if they do not exist anywhere else
     *               but this cache
     * @return A weak hash clock implementation
     */
    public static <K> WeakHashLRU<K> getWeakHashLRU(boolean isWeak) {
        return new WeakHashLRUImpl<K>(isWeak);
    }

}
