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
package com.wildbeeslabs.sensiblemetrics.diffy.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.BadOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.IllegalAccessException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.MethodInvocationException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.PropertyAccessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import javax.annotation.Nullable;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collections;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.TypeUtils.*;

/**
 * Reflection utilities implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class ReflectionUtils {

    /**
     * Returns matchable class instance {@link Class} by input class type {@link Class}
     *
     * @param expectedClass - initial argument class instance {@link Class}
     * @return matchable class instance {@link Class}
     */
    public static Class<?> getMatchableClass(final Class<?> expectedClass) {
        final Optional<? extends Class<?>> optionalClass = DEFAULT_PRIMITIVE_TYPES
            .keySet()
            .stream()
            .filter(type -> type.equals(expectedClass))
            .map(DEFAULT_PRIMITIVE_TYPES::get)
            .findFirst();
        return (optionalClass.isPresent()) ? optionalClass.get() : expectedClass;
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
     * Returns binary flag based on input comparable class instance {@link Class}
     *
     * @param clazz - initial comparable class instance {@link Class}
     * @return true - if input class is of comparable type, false - otherwise
     */
    public static boolean isComparableType(final Class<?> clazz) {
        return Comparable.class.isAssignableFrom(clazz);
    }

    /**
     * Returns binary flag base on input simple class instance {@link Class}
     *
     * @param clazz - initial simple class instance {@link Class}
     * @return true - if input class is of simple type, false - otherwise
     */
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
     * @param <T>   type of input class instance
     * @param clazz - input argument class instance {@link Class}
     * @return initialized instance by class {@link Class}
     */
    @Nullable
    public static <T> T instanceOf(final Class<T> clazz) {
        if (Objects.isNull(clazz)) {
            return null;
        }
        final Constructor<T> constructor;
        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (final NoSuchMethodException e) {
            BadOperationException.throwBadOperation(String.format("ERROR: missing default constructor for class: {%s}", clazz.getName()), e);
            return null;
        }
        try {
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            BadOperationException.throwBadOperation(String.format("ERROR: cannot create instance by class: {%s}", clazz), e);
            return null;
        }
    }

    /**
     * Returns traversable collection of types {@link Set} by input collection of object values
     *
     * @param values - initial input collection of object values
     * @return traversable collection of types {@link Set}
     */
    public static Set<Class<?>> typesOf(final Object... values) {
        final Object[] traversableType = Optional.ofNullable(values).orElseGet(() -> new Object[]{});
        return Stream.of(traversableType).filter(Objects::nonNull).map(Object::getClass).collect(Collectors.toSet());
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
        return !StreamSupport.stream(traversableType.spliterator(), false).allMatch(sharedType::isAssignableFrom);
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
     * Returns property value of an object {@code T} by name {@link String}
     *
     * @param <T>          type of input element to get property value from
     * @param value        - initial argument to get property value from
     * @param propertyName - initial property name {@link String}
     * @param clazz        - initial class to be casted to {@link Class}
     * @return property value
     */
    public static <T> T getProperty(final T value, final String propertyName, final Class<? extends T> clazz) {
        try {
            return castSafe(BeanUtils.getProperty(value, propertyName), clazz);
        } catch (java.lang.IllegalAccessException e) {
            PropertyAccessException.throwIllegalAccess(propertyName, value, e);
        } catch (InvocationTargetException | NoSuchMethodException e) {
            PropertyAccessException.throwIllegalAccess(propertyName, value, e);
        }
        return null;
    }

    /**
     * Returns null-safe argument cast {@link Class}
     *
     * @param <T>
     * @param value - initial argument to be casted {@link Object}
     * @param clazz - initial class to be casted to {@link Class}
     * @return casted object
     */
    public static <T> T castSafe(final Object value, @NonNull final Class<? extends T> clazz) {
        return clazz.isInstance(value) ? clazz.cast(value) : null;
    }

    /**
     * Returns type of the supplied argument {@link Member}
     * or {@link Method}
     *
     * @param member - initial argument to reflect on {@link Member}
     * @return type of the supplied argument {@link Member}
     */
    public static Type typeOf(@NonNull final Member member) {
        if (member instanceof Field) {
            return ((Field) member).getGenericType();
        }
        if (member instanceof Method) {
            return ((Method) member).getGenericReturnType();
        }
        IllegalAccessException.throwInvalidAccess(String.format("ERROR: no such class member = {%s}, neither a field nor a method", member));
        return null;
    }

    /**
     * Returns list of super-classes {@link List} of the supplied class {@link Class}
     *
     * @param clazz - class to reflect on {@link Class}
     * @return list of super-classes of the supplied class {@link Class}
     */
    public static List<Class<?>> getAllSuperclasses(@NonNull final Class<?> clazz) {
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
    public static Field[] getAllFields(final List<Class<?>> classes) {
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
        return isNotStaticOrFinal(field, includeFinalFields) && (!includeAccessibleFields || field.trySetAccessible());
    }

    /**
     * Returns method type instance {@link ReflectionMethodType}
     *
     * @param methodName         - initial method name {@link String}
     * @param numberOfParameters - initial number of method parameters
     * @param typedParameter     - initial index of type parameter position
     * @return reflection method type instance
     */
    public static ReflectionMethodType getMethodType(final String methodName, int numberOfParameters, int typedParameter) {
        return new ReflectionMethodType(methodName, numberOfParameters, typedParameter);
    }

    /**
     * Returns binary flag based on getter method characteristics (prefix/abstract/static/native/return type)
     *
     * @param rawMethod - initial method type {@link Method}
     * @return true - if method is a getter, false - otherwise
     */
    public static boolean isGetter(final Method rawMethod) {
        return hasGetOrIsPrefix(rawMethod) &&
            hasNoParameters(rawMethod) &&
            returnsSomething(rawMethod) &&
            isNotStatic(rawMethod) &&
            //isNotAbstract(rawMethod) &&
            isNotNative(rawMethod);
    }

    /**
     * Return binary flag whether method has subclass implementation
     *
     * @param parent  - initial parent method type {@link Method}
     * @param toCheck - initial method type to be checked {@link Method}
     * @return true - if method has subclass implementation, false - otherwise
     */
    public static boolean isSubClass(final Method parent, final Method toCheck) {
        return parent.getDeclaringClass().isAssignableFrom(toCheck.getDeclaringClass());
    }

    /**
     * Return binary flag whether method has same name in subclass
     *
     * @param parent  - initial parent method type {@link Method}
     * @param toCheck - initial method type to be checked {@link Method}
     * @return true - if method has same name, false - otherwise
     */
    public static boolean sameMethodName(final Method parent, final Method toCheck) {
        return parent.getName().equals(toCheck.getName());
    }

    /**
     * Return binary flag whether method has covariant return type
     *
     * @param parent  - initial parent method type {@link Method}
     * @param toCheck - initial method type to be checked {@link Method}
     * @return true - if method has covariant return type, false - otherwise
     */
    public static boolean returnTypeCovariant(final Method parent, final Method toCheck) {
        return parent.getReturnType().isAssignableFrom(toCheck.getReturnType());
    }

    /**
     * Return binary flag whether method has same arguments
     *
     * @param parent  - initial parent method type {@link Method}
     * @param toCheck - initial method type to be checked {@link Method}
     * @return true - if method has same arguments, false - otherwise
     */
    public static boolean sameArguments(final Method parent, final Method toCheck) {
        return Arrays.equals(parent.getParameterTypes(), toCheck.getParameterTypes());
    }

    /**
     * Changes accessibility type of input member instance {@link Member}
     *
     * @param rawMember - initial member instance {@link Member}
     */
    public static void setAccessible(final Member rawMember) {
        if (!isPublic(rawMember)) {
            ((AccessibleObject) rawMember).setAccessible(true);
        }
    }

    /**
     * Return binary flag whether member has "public" modifier
     *
     * @param member - initial member instance {@link Member}
     * @return true - if method has "public" modifier, false - otherwise
     */
    public static boolean isPublic(@NonNull final Member member) {
        return Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers());
    }

    /**
     * Returns value by method invocation on a target instance {@link Object}
     *
     * @param target     - initial target the method to be invoked on {@link Object}
     * @param getterName - initial getter name to be invoked {@link String}
     * @return method value by return type {@link Object}
     * @throws RuntimeException
     */
    public static Object invokeGetter(@NonNull final Object target, @NonNull final String getterName) {
        try {
            final Method m = target.getClass().getMethod(getterName);
            return m.invoke(target);
        } catch (Exception e) {
            MethodInvocationException.throwMethodInvocation(getterName, target, e);
        }
        return null;
    }

    /**
     * Returns iterable collection of all persistent fields {@link List} by input class instance {@link Class}
     *
     * @param clazz - input class instance {@link Class}
     * @return collection of persistent fields {@link List}
     */
    public static List<Field> getAllPersistentFields(final Class clazz) {
        return Arrays.stream(Optional.ofNullable(getAllFields(clazz)).orElseGet(() -> new Field[]{})).filter(ReflectionUtils::isPersistentField).collect(Collectors.toList());
    }

    /**
     * Returns iterable collection of type arguments {@link List} by input type instance {@link Type}
     *
     * @param type - input type instance {@link Type}
     * @return collection of type arguments {@link List}
     */
    public static List<Type[]> getTypeArguments(final Type type) {
        if (!(type instanceof ParameterizedType)) {
            return Collections.emptyList();
        }
        return Lists.<Type[]>newArrayList(((ParameterizedType) type).getActualTypeArguments());
    }

    /**
     * Return optional type {@link Type} by input java type {@link Type}
     *
     * @param javaType - initial java type instance {@link Type}
     * @return optinal type {@link Type}
     */
    public static Optional<Type> isConcreteType(final Type javaType) {
        if (javaType instanceof Class || javaType instanceof ParameterizedType) {
            return Optional.of(javaType);
        }
        if (javaType instanceof WildcardType) {
            final WildcardType wildcardType = (WildcardType) javaType;
            if (wildcardType.getLowerBounds().length == 0) {
                for (final Type type : wildcardType.getUpperBounds()) {
                    if (type instanceof Class && type.equals(Object.class)) {
                        continue;
                    }
                    return Optional.of(type);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Returns class instance {@link Class} by input java type {@link Type}
     *
     * @param javaType - initial java type instance {@link Type}
     * @return class instance {@link Class}
     */
    public static Class extractClass(final Type javaType) {
        if (javaType instanceof ParameterizedType && ((ParameterizedType) javaType).getRawType() instanceof Class) {
            return (Class) ((ParameterizedType) javaType).getRawType();
        } else if (javaType instanceof GenericArrayType) {
            return Object[].class;
        } else if (javaType instanceof Class) {
            return (Class) javaType;
        }
        IllegalAccessException.throwInvalidAccess(String.format("ERROR: cannot get class by type: {%s}", javaType));
        return null;
    }

    /**
     * Returns binary flag based on annotation type by input class instance {@link Class}
     *
     * @param clazz           - initial input class instance {@link Class}
     * @param annotationClazz - initial input annotation class instance {@link Class}
     * @return true - if annotation is present, false - otherwise
     */
    public static boolean isAnnotationPresentInHierarchy(final Class<?> clazz, final Class<? extends Annotation> annotationClazz) {
        Class<?> current = clazz;
        while (Objects.nonNull(current) && current != Object.class) {
            if (current.isAnnotationPresent(annotationClazz)) {
                return true;
            }
            current = current.getSuperclass();
        }
        return false;
    }

    /**
     * Returns collection of types {@link List} in class hierarchy by input class instance {@link Class}
     *
     * @param clazz - initial input class instance {@link Class}
     * @return collection of types {@link List} in class hierarchy
     */
    public static List<Type> calculateHierarchyDistance(final Class<?> clazz) {
        final List<Type> interfaces = new ArrayList<>();
        final List<Type> parents = new ArrayList<>();
        Class<?> current = clazz;
        while (Objects.nonNull(current) && current != Object.class) {
            if (clazz != current) {
                parents.add(current);
            }
            for (final Class i : current.getInterfaces()) {
                if (!interfaces.contains(i)) {
                    interfaces.add(i);
                }
            }
            current = current.getSuperclass();
        }
        parents.addAll(interfaces);
        return parents;
    }

    /**
     * Returns annotation value by annotation instance {@link Annotation} and property name {@link String}
     *
     * @param annotation   - initial input annotation instance {@link Annotation}
     * @param propertyName - initial input property name {@link String}
     * @param <T>
     * @return annotation value
     */
    public static <T> T getAnnotationValue(final Annotation annotation, final String propertyName) {
        return (T) invokeGetter(annotation, propertyName);
    }

    /**
     * Returns collection of annotations {@link Set} by input member instance {@link Member}
     *
     * @param member - initial input member instance {@link Member}
     * @return collection of annotations {@link Set}
     */
    public static Set<Annotation> getAnnotations(final Member member) {
        return Collections.unmodifiableSet(Sets.newHashSet(((AccessibleObject) member).getAnnotations()));
    }

    /**
     * Return binary flag whether method has prefix "get/is"
     *
     * @param rawMethod - initial method type {@link Method}
     * @return true - if method has a prefix, false - otherwise
     */
    private static boolean hasGetOrIsPrefix(final Method rawMethod) {
        return rawMethod.getName().startsWith("get") || rawMethod.getName().startsWith("is");
    }

    /**
     * Return binary flag whether method has input parameters
     *
     * @param rawMethod - initial method type {@link Method}
     * @return true - if method has input parameters, false - otherwise
     */
    private static boolean hasNoParameters(final Method rawMethod) {
        return rawMethod.getParameterTypes().length == 0;
    }

    /**
     * Return binary flag whether method returns value
     *
     * @param rawMethod - initial method type {@link Method}
     * @return true - if method returns value, false - otherwise
     */
    private static boolean returnsSomething(final Method rawMethod) {
        return rawMethod.getGenericReturnType() != void.class;
    }

    /**
     * Return binary flag whether method is not abstract
     *
     * @param rawMethod - initial method type {@link Method}
     * @return true - if method is not abstract, false - otherwise
     */
    private static boolean isNotAbstract(final Method rawMethod) {
        return !Modifier.isAbstract(rawMethod.getModifiers());
    }

    /**
     * Return binary flag whether method is not static
     *
     * @param rawMethod - initial method type {@link Method}
     * @return true - if method is not static, false - otherwise
     */
    private static boolean isNotStatic(final Method rawMethod) {
        return !Modifier.isStatic(rawMethod.getModifiers());
    }

    /**
     * Return binary flag whether method is not native
     *
     * @param rawMethod - initial method type {@link Method}
     * @return true - if method is not native, false - otherwise
     */
    private static boolean isNotNative(final Method rawMethod) {
        return !Modifier.isNative(rawMethod.getModifiers());
    }

    /**
     * Return binary flag whether method has is been overridden
     *
     * @param parent  - initial parent method type {@link Method}
     * @param toCheck - initial method type to be checked {@link Method}
     * @return true - if method is been overridden, false - otherwise
     */
    private static boolean isOverridden(final Method parent, final Method toCheck) {
        return isSubClass(parent, toCheck) &&
            sameMethodName(parent, toCheck) &&
            returnTypeCovariant(parent, toCheck) &&
            sameArguments(parent, toCheck);
    }

    /**
     * Return binary flag whether field is persistent
     *
     * @param field - initial field instance {@link Field}
     * @return true - if method is persistent, false - otherwise
     */
    private static boolean isPersistentField(final Field field) {
        return !Modifier.isTransient(field.getModifiers()) &&
            !Modifier.isStatic(field.getModifiers()) &&
            !field.getName().equals("this$0");
    }

    /**
     * Return binary flag whether member is private
     *
     * @param member - initial member instance {@link Member}
     * @return true - if method is private, false - otherwise
     */
    private static boolean isPrivate(final Member member) {
        return Modifier.isPrivate(member.getModifiers());
    }

    /**
     * Return binary flag whether member is protected
     *
     * @param member - initial member instance {@link Member}
     * @return true - if method is protected, false - otherwise
     */
    private static boolean isProtected(final Member member) {
        return Modifier.isProtected(member.getModifiers());
    }

    /**
     * Returns sorted iterable collection {@link List} by input collection {@link List}, object field name {@link String} and class instance {@link Class}
     *
     * @param list  - initial input collection to be sorted {@link List}
     * @param field - initial input object field name {@link String}
     * @param clazz - initial input class instance {@link Class}
     * @param <T>
     * @return sorted collection {@link List}
     * @throws ReflectiveOperationException
     * @throws IntrospectionException
     */
    public static <T> List<T> sortList(final List<T> list, final String field, @NonNull final Class<? extends T> clazz) throws ReflectiveOperationException, IntrospectionException {
        final Field f = clazz.getDeclaredField(field);
        final PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), clazz);
        final Method getter = propertyDescriptor.getReadMethod();
        final Class<?> returnType = getter.getReturnType();

        if (Comparable.class.isAssignableFrom(returnType) || returnType.isPrimitive()) {
            Collections.sort(Optional.ofNullable(list).orElseGet(Collections::emptyList),
                (e1, e2) -> {
                    try {
                        final Comparable<Object> val1 = (Comparable<Object>) getter.invoke(e1);
                        final Comparable<Object> val2 = (Comparable<Object>) getter.invoke(e2);
                        return val1.compareTo(val2);
                    } catch (Exception e) {
                        BadOperationException.throwBadOperation(String.format("ERROR: cannot invoke getter method = {%s} on field = {%s}", getter.getName(), field), e);
                    }
                    return 0;
                });
        } else {
            BadOperationException.throwBadOperation(String.format("ERROR: cannot compare list: {%s} by field: {%s} of type: {%s}", org.apache.commons.lang3.StringUtils.join(list, "|"), field, returnType.getName()));
        }
        return list;
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

        /**
         * Default reflection method type constructor with initial method name {@link String}, number of input parameters and their types
         *
         * @param methodName         - initial input method name {@link String}
         * @param numberOfParameters - initial input number of parameters
         * @param typedParameter     - initial input index of positional parameter
         */
        public ReflectionMethodType(final String methodName, int numberOfParameters, int typedParameter) {
            this.methodName = methodName;
            this.numberOfParameters = numberOfParameters;
            this.typeParameter = typedParameter;
        }

        /**
         * Returns method parameter type {@link Class} by initial class instance {@link Class}
         *
         * @param clazz - initial class instance {@link Class}
         * @return method parameter type {@link Class}
         */
        public Class<?> getType(final Class<?> clazz) {
            for (Class c = clazz; c != Object.class; c = c.getSuperclass()) {
                final Optional<Method> methodOptional = Stream.of(c.getDeclaredMethods()).filter(this::hasSignature).findFirst();
                if (methodOptional.isPresent()) {
                    return getParameterType(methodOptional.get());
                }
            }
            BadOperationException.throwBadOperation(String.format("ERROR: cannot determine correct type for method={%s}", getMethodName()));
            return null;
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
