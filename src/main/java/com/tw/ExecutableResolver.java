package com.tw;

import javax.inject.Inject;
import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ExecutableResolver implements Resolver {
    public List<Object> fillParameters(Container container, Class<?> klass, Executable executable) throws NoSuchFieldException, IllegalAccessException, InstantiationException, InvocationTargetException {
        List<Object> parameters = new ArrayList<>();
        if (executable.getAnnotation(Inject.class) != null) {
            executable.setAccessible(true);
            Class<?>[] types = executable.getParameterTypes();
            if (types == null) return null;
            for (Parameter parameter : executable.getParameters()) {
                Class<?> type = parameter.getType();
                Annotation qualifier = Arrays.stream(parameter.getAnnotations())
                        .filter(anno -> anno.annotationType().isAnnotationPresent(Qualifier.class))
                        .findFirst().orElseGet(() -> null);
                if (qualifier != null) {
                    Object entity = container.resolveByQualifier(new QualifierMeta(qualifier.annotationType()));
                    if (entity instanceof Class) {
                        container.checkCircularDependencies(type, klass);
                        container.dependencyMap.put(type, klass);
                        parameters.add(container.getResource((Class<?>) entity));
                    } else {
                        parameters.add(entity);
                    }
                } else {
                    container.checkCircularDependencies(type, klass);
                    container.dependencyMap.put(type, klass);
                    parameters.add(container.getResource(type));
                }
            }
        }
        return parameters;
    }

    abstract public Object resolveObject(Container container, Class<?> klass, Object object) throws NoSuchFieldException, IllegalAccessException, InstantiationException, InvocationTargetException;
}
