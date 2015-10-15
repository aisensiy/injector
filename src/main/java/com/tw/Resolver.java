package com.tw;

import java.lang.reflect.InvocationTargetException;

public interface Resolver {
    Object resolveObject(Container container, Class<?> klass, Object object) throws NoSuchFieldException, IllegalAccessException, InstantiationException, InvocationTargetException;
}
