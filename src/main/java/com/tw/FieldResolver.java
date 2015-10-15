package com.tw;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static com.tw.QualifierMeta.buildQualifierMeta;

public class FieldResolver implements Resolver {
    @Override
    public Object resolveObject(Container container, Class<?> klass, Object object) throws IllegalAccessException, NoSuchFieldException, InstantiationException, InvocationTargetException {
        if (klass == null) {
            return object;
        }
        Class<?> superClass = klass.getSuperclass();
        if (superClass != null)
            resolveObject(container, superClass, object);
        for (Field field : klass.getDeclaredFields()) {
            if (field.getAnnotation(Inject.class) != null) {
                container.checkInjectFinalField(field);
                field.setAccessible(true);
                container.checkCircularDependencies(field.getType(), klass);
                container.dependencyMap.put(field.getType(), klass);
                if (Modifier.isStatic(field.getModifiers()) && field.get(object) != null) {
                    continue;
                }

                Named annotation = field.getAnnotation(Named.class);
                if (annotation != null) {
                    resolveNamed(container, object, field, container.resolveByName(annotation.value()));
                    return object;
                }

                Annotation qualifier = Arrays
                        .stream(field.getAnnotations())
                        .filter(qualifierAnnotation -> qualifierAnnotation.annotationType().isAnnotationPresent(Qualifier.class))
                        .findFirst().orElseGet(() -> null);
                if (qualifier != null) {
                    resolveQualifier(container, object, field, qualifier);
                    return object;
                }

                field.set(object, container.getResource(field.getType()));
                return object;
            }
        }
        return object;
    }

    private void resolveQualifier(Container container, Object object, Field field, Annotation qualifier) throws IllegalAccessException, NoSuchFieldException, InstantiationException, InvocationTargetException {
        Object entity = container.resolveByQualifier(buildQualifierMeta(qualifier));
        if (entity instanceof Class) {
            container.checkCircularDependencies(field.getType(), (Class<?>) entity);
            container.dependencyMap.put(field.getType(), (Class<?>) entity);
            field.set(object, container.getResource((Class<?>) entity));
        } else {
            field.set(object, entity);
        }
    }

    public void resolveNamed(Container container, Object object, Field field, Object entity) throws IllegalAccessException, NoSuchFieldException, InstantiationException, InvocationTargetException {
        if (entity instanceof Class) {
            field.set(object, container.getResource((Class<?>) entity));
        } else {
            field.set(object, entity);
        }
    }
}
