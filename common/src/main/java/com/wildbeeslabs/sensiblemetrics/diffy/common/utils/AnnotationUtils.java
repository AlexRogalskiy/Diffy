package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@UtilityClass
public class AnnotationUtils {

    /**
     * Indicates whether an annotation of given {@code annotationType} is present on the given {@code element},
     * considering that the given {@code annotationType} may be present as a meta annotation on any other annotation
     * on that element.
     *
     * @param element        The element to inspect
     * @param annotationType The type of annotation to find
     * @return {@code true} if such annotation is present.
     */
    @SuppressWarnings("unchecked")
    public static boolean isAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> annotationType) {
        return isAnnotationPresent(element, annotationType.getName());
    }

    /**
     * Indicates whether an annotation with given {@code annotationType} is present on the given {@code element},
     * considering that the given {@code annotationType} may be present as a meta annotation on any other annotation
     * on that element.
     *
     * @param element        The element to inspect
     * @param annotationType The name of the annotation to find
     * @return {@code true} if such annotation is present.
     */
    public static boolean isAnnotationPresent(AnnotatedElement element, String annotationType) {
        return findAnnotationAttributes(element, annotationType).isPresent();
    }

    /**
     * Find the attributes of an annotation with given {@code annotationName} on the given {@code element}. The returned
     * optional has a value present if the annotation has been found, either directly on the {@code element}, or as a
     * meta-annotation.
     * <p>
     * The map of attributes contains all the attributes found on the annotation, as well as attributes of any
     * annotations on which the targeted annotation was placed (directly, or indirectly).
     *
     * @param element        The element for find the annotation on
     * @param annotationName The name of the annotation to find
     * @return an optional that resolved to a map with attribute names and value, if the annotation is found
     */
    public static Optional<Map<String, Object>> findAnnotationAttributes(AnnotatedElement element, String annotationName) {
        Map<String, Object> attributes = new HashMap<>();
        Annotation ann = getAnnotation(element, annotationName);
        boolean found = false;
        if (ann != null) {
            collectAttributes(ann, attributes);
            found = true;
        } else {
            HashSet<String> visited = new HashSet<>();
            for (Annotation metaAnn : element.getAnnotations()) {
                if (collectAnnotationAttributes(metaAnn.annotationType(), annotationName, visited, attributes)) {
                    found = true;
                    collectAttributes(metaAnn, attributes);
                }
            }
        }
        return found ? Optional.of(attributes) : Optional.empty();
    }

    /**
     * Find the attributes of an annotation of type {@code annotationType} on the given {@code element}. The returned
     * optional has a value present if the annotation has been found, either directly on the {@code element}, or as a
     * meta-annotation.
     * <p>
     * The map of attributes contains all the attributes found on the annotation, as well as attributes of any
     * annotations on which the targeted annotation was placed (directly, or indirectly). Note that the {@code value}
     * property of annotations is reported as the simple class name (lowercase first character) of the annotation. This
     * allows specific attribute overrides for annotations that have multiple meta-annotation with the {@code value}
     * property.
     *
     * @param element        The element for find the annotation on
     * @param annotationType The type of the annotation to find
     * @return an optional that resolved to a map with attribute names and value, if the annotation is found
     */
    public static Optional<Map<String, Object>> findAnnotationAttributes(AnnotatedElement element,
                                                                         Class<? extends Annotation> annotationType) {
        return findAnnotationAttributes(element, annotationType.getName());
    }

    private static boolean collectAnnotationAttributes(Class<? extends Annotation> target, String annotationType, HashSet<String> visited, Map<String, Object> attributes) {
        Annotation ann = getAnnotation(target, annotationType);
        if (ann == null && visited.add(target.getName())) {
            for (Annotation metaAnn : target.getAnnotations()) {
                if (collectAnnotationAttributes(metaAnn.annotationType(), annotationType, visited, attributes)) {
                    collectAttributes(metaAnn, attributes);
                    return true;
                }
            }
        } else if (ann != null) {
            collectAttributes(ann, attributes);
            return true;
        }
        return false;
    }

    private static Annotation getAnnotation(AnnotatedElement target, String annotationType) {
        for (Annotation annotation : target.getAnnotations()) {
            if (annotationType.equals(annotation.annotationType().getName())) {
                return annotation;
            }
        }
        return null;
    }

    private static <T extends Annotation> void collectAttributes(T ann, Map<String, Object> attributes) {
        Method[] methods = ann.annotationType().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getParameterTypes().length == 0 && method.getReturnType() != void.class) {
                try {
                    Object value = method.invoke(ann);
                    attributes.put(resolveName(method), value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalArgumentException("Error while inspecting annotation values", e);
                }
            }
        }
    }

    private static String resolveName(Method method) {
        if ("value".equals(method.getName())) {
            String simpleName = method.getDeclaringClass().getSimpleName();
            return simpleName.substring(0, 1).toLowerCase(Locale.ENGLISH).concat(simpleName.substring(1));
        }
        return method.getName();
    }

    private static Annotation getAnnotation(Class<? extends Annotation> target, String annotationType,
                                            Set<String> visited) {
        Annotation ann = getAnnotation(target, annotationType);
        if (ann == null && visited.add(target.getName())) {
            for (Annotation metaAnn : target.getAnnotations()) {
                ann = getAnnotation(metaAnn.annotationType(), annotationType, visited);
                if (ann != null) {
                    break;
                }
            }
        }
        return ann;
    }
}
