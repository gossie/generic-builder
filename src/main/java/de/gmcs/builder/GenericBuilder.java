package de.gmcs.builder;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The {@link GenericBuilder} is capable of creating and building instances.
 *
 * @param <T>
 *            The type of the class to be built.
 */
public class GenericBuilder<T> {

    private T instance;
    private Class<T> clazz;

    private GenericBuilder(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        this.instance = clazz.newInstance();
        this.clazz = clazz;
    }

    /**
     * The method creates a new instance of the {@link GenericBuilder}.
     * 
     * @param clazz
     *            The parameter determines the type of the instance to be built.
     * @return An instance of the {@link GenericBuilder} is returned.
     * @throws GenericBuilderException
     *             The exception is thrown if an error occurs while creating an
     *             instance of the class to be built.
     */
    public static <T> GenericBuilder<T> getInstance(Class<T> clazz) throws GenericBuilderException {
        try {
            return new GenericBuilder<>(clazz);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new GenericBuilderException(e);
        }
    }

    /**
     * The method invokes the method with the passed name and passes the passed
     * values.
     * 
     * @param methodName
     *            The name of the method to be invoked.
     * @param propertyValues
     *            The parameters to be passed to the invoked method.
     * @return The {@link GenericBuilder} is returned.
     * @throws GenericBuilderException
     *             The exception is thrown if an error occurs while invoking the
     *             method.
     */
    public GenericBuilder<T> with(String methodName, Object... propertyValues) throws GenericBuilderException {
        try {
            Class<T>[] parameterTypes = getParameterTypes(propertyValues);

            Method method = clazz.getMethod(methodName, parameterTypes);
            method.invoke(instance, propertyValues);
            return this;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new GenericBuilderException(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected Class<T>[] getParameterTypes(Object... propertyValues) {
        Class<T>[] parameterTypes = (Class<T>[]) Array.newInstance(Class.class, propertyValues.length);
        for (int i = 0; i < propertyValues.length; i++) {
            parameterTypes[i] = (Class<T>) propertyValues[i].getClass();
        }
        return parameterTypes;
    }

    /**
     * The method returns the instance that was built.
     * 
     * @return The instance taht was built.
     */
    public T build() {
        return instance;
    }
}
