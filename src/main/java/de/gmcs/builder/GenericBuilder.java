package de.gmcs.builder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GenericBuilder<T> {

    private T instance;
    private Class<T> clazz;

    private GenericBuilder(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        this.instance = clazz.newInstance();
        this.clazz = clazz;
    }

    public static <T> GenericBuilder<T> getInstance(Class<T> clazz) throws GenericBuilderException {
        try {
            return new GenericBuilder<>(clazz);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new GenericBuilderException(e);
        }
    }

    public GenericBuilder<T> with(String methodName, Object... propertyValues) throws GenericBuilderException {
        try {
            Class<Object>[] parameterTypes = new Class[propertyValues.length];
            for (int i = 0; i < propertyValues.length; i++) {
                parameterTypes[i] = (Class<Object>) propertyValues[i].getClass();
            }

            Method method = clazz.getMethod(methodName, parameterTypes);
            method.invoke(instance, propertyValues);
            return this;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new GenericBuilderException(e);
        }
    }

    public T build() {
        return instance;
    }
}
