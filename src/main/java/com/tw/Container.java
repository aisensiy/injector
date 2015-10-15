package com.tw;

import com.tw.exception.CicularDependenciesException;
import com.tw.exception.ClassNotFoundInContainerException;
import com.tw.exception.InjectFinalFieldException;
import com.tw.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Container {

    private Set<Class<?>> registedClass = new HashSet<>();
    public Map<Class<?>, Class<?>> dependencyMap = new HashMap<>();
    public Set<Pair<Class<?>, String>> invokedStaticMethodSet = new HashSet<>();
    public Map<Object, Object> namedMapper = new HashMap<>();
    private Map<Object, Object> qualifierMapper = new HashMap<>();

    public void register(Class<?> klass) {
        registedClass.add(klass);
    }

    public Object getResource(Class<?> klass) throws NoSuchFieldException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (!registedClass.contains(klass)) {
            throw new ClassNotFoundInContainerException(klass.getName());
        }

        Object object;

        object = new ConstructorResolver().resolveObject(this, klass, null);
        object = new FieldResolver().resolveObject(this, klass, object);
        object = new MethodResolver().resolveObject(this, klass, object);

        return object;
    }

    public void checkInjectFinalField(Field field) {
        if (Modifier.isFinal(field.getModifiers())) {
            throw new InjectFinalFieldException();
        }
    }

    public void checkCircularDependencies(Class<?> child, Class<?> parent) {
        if (dependencyMap.getOrDefault(parent, null) == null) {
            return;
        } else if (child == parent) {
            throw new CicularDependenciesException();
        } else {
            checkCircularDependencies(child, dependencyMap.getOrDefault(parent, null));
        }
    }

    public void registerByName(String name, Object object) {
        namedMapper.put(name, object);
        if (object instanceof Class) {
            register((Class<?>) object);
        }
    }

    public Object resolveByName(String value) {
        return namedMapper.getOrDefault(value, null);
    }

    public void registerByQualifier(QualifierMeta qualifierMeta, Object object) {
        qualifierMapper.put(qualifierMeta, object);
        if (object instanceof Class) {
            register((Class<?>) object);
        }
    }

    public Object resolveByQualifier(QualifierMeta qualifierMeta) {
        return qualifierMapper.getOrDefault(qualifierMeta, null);
    }
}