package com.tw;

import com.tw.util.Pair;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MethodResolver extends ExecutableResolver {
    private Set<String> invokedMethods = new HashSet<>();

    @Override
    public Object resolveObject(Container container, Class<?> klass, Object object) throws NoSuchFieldException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (klass == null) {
            return object;
        }
        Class<?> supperClass = klass.getSuperclass();
        resolveObject(container, supperClass, object);
        for (Method method : klass.getDeclaredMethods()) {
            Pair<Class<?>, String> pair = Pair.createPair(method.getDeclaringClass(), method.getName());
            if (method.getAnnotation(Inject.class) != null &&
                    !invokedMethods.contains(method.getName()) &&
                    !container.invokedStaticMethodSet.contains(pair)) {
                method.setAccessible(true);
                List<Object> parameters = fillParameters(container, klass, method);
                method.invoke(object, parameters.toArray(new Object[method.getParameterCount()]));
                invokedMethods.add(method.getName());
                container.invokedStaticMethodSet.add(pair);
                method.setAccessible(false);
            }
        }
        return object;
    }
}
