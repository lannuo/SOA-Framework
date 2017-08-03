package com.jun.soa.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public abstract class SpringDataAnnotationUtils {
    private SpringDataAnnotationUtils() {
    }

    public static void assertPageableUniqueness(MethodParameter parameter) {
        Method method = parameter.getMethod();
        if (containsMoreThanOnePageableParameter(method)) {
            Annotation[][] annotations = method.getParameterAnnotations();
            assertQualifiersFor(method.getParameterTypes(), annotations);
        }

    }

    private static boolean containsMoreThanOnePageableParameter(Method method) {
        boolean pageableFound = false;
        Class[] var2 = method.getParameterTypes();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Class<?> type = var2[var4];
            if (pageableFound && type.equals(Pageable.class)) {
                return true;
            }

            if (type.equals(Pageable.class)) {
                pageableFound = true;
            }
        }

        return false;
    }

    public static Object getSpecificPropertyOrDefaultFromValue(Annotation annotation, String property) {
        Object propertyDefaultValue = AnnotationUtils.getDefaultValue(annotation, property);
        Object propertyValue = AnnotationUtils.getValue(annotation, property);
        return ObjectUtils.nullSafeEquals(propertyDefaultValue, propertyValue) ? AnnotationUtils.getValue(annotation) : propertyValue;
    }

    public static void assertQualifiersFor(Class<?>[] parameterTypes, Annotation[][] annotations) {
        Set<String> values = new HashSet();

        for(int i = 0; i < annotations.length; ++i) {
            if (Pageable.class.equals(parameterTypes[i])) {
                Qualifier qualifier = findAnnotation(annotations[i]);
                if (null == qualifier) {
                    throw new IllegalStateException("Ambiguous Pageable arguments in handler method. If you use multiple parameters of type Pageable you need to qualify them with @Qualifier");
                }

                if (values.contains(qualifier.value())) {
                    throw new IllegalStateException("Values of the user Qualifiers must be unique!");
                }

                values.add(qualifier.value());
            }
        }

    }

    public static Qualifier findAnnotation(Annotation[] annotations) {
        Annotation[] var1 = annotations;
        int var2 = annotations.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Annotation annotation = var1[var3];
            if (annotation instanceof Qualifier) {
                return (Qualifier)annotation;
            }
        }

        return null;
    }
}
