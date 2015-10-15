package com.tw;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class QualifierMeta {

    private Class<?> qualifierClass;
    private Map<String, Object> metaMapper = new HashMap<>();

    public QualifierMeta(Class<?> qualifierClass) {
        this.qualifierClass = qualifierClass;
    }

    public QualifierMeta meta(String name, Object value) {
        metaMapper.put(name, value);
        return this;
    }

    public static QualifierMeta buildQualifierMeta(Annotation qualifier) throws InvocationTargetException, IllegalAccessException {
        QualifierMeta qualifierMeta = new QualifierMeta(qualifier.annotationType());
        for(Method method : qualifier.annotationType().getDeclaredMethods())
            qualifierMeta.meta(method.getName(), method.invoke(qualifier));
        return qualifierMeta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QualifierMeta)) return false;

        QualifierMeta that = (QualifierMeta) o;

        if (qualifierClass != null ? !qualifierClass.equals(that.qualifierClass) : that.qualifierClass != null)
            return false;
        return !(metaMapper != null ? !metaMapper.equals(that.metaMapper) : that.metaMapper != null);

    }

    @Override
    public int hashCode() {
        int result = qualifierClass != null ? qualifierClass.hashCode() : 0;
        result = 31 * result + (metaMapper != null ? metaMapper.hashCode() : 0);
        return result;
    }
}
