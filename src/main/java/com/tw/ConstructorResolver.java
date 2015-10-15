package com.tw;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ConstructorResolver extends ExecutableResolver {
    @Override
    public Object resolveObject(Container container, Class<?> klass, Object object) throws NoSuchFieldException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (klass == null) {
            return object;
        }
        for (Constructor constructor : klass.getConstructors()) {
            List<Object> parameters =  fillParameters(container, klass, constructor);
            if (parameters == null) continue;
            object = constructor.newInstance(parameters.toArray(new Object[constructor.getParameterCount()]));
        }
        if (object == null) object = klass.newInstance();
        return object;
    }
}
