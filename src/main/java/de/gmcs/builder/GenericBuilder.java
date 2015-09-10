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

    private GenericBuilder(Class<T> clazz, T instance) throws InstantiationException, IllegalAccessException {
        this.instance = instance;
        this.clazz = clazz;
    }

    /**
     * The method created a new instance of the {@link GenericBuilder}.
     * 
     * @param clazz
     *            The parameter determines the type of the instance to be built.
     * @param args
     *            The arguments that are passed to the constructor that creates
     *            the instance to be build.
     * @return An instance of the {@link GenericBuilder} is returned.
     * @throws GenericBuilderException
     *             The exception is thrown if an error occurs while creating an
     *             instance of the class to be built.
     */
    public static <T> GenericBuilder<T> getInstance(Class<T> clazz, Object... args) throws GenericBuilderException {
        try {
            return new GenericBuilder<>(clazz, clazz.getConstructor(getParameterTypes(args)).newInstance(args));
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new GenericBuilderException(e);
        }
    }

    /**
     * The method created a new instance of the {@link GenericBuilder}.
     * 
     * @param clazz
     *            The parameter determines the type of the instance to be built.
     * @param factoryMethodName
     *            The name of the factory method that creates the instance to be
     *            built.
     * @param args
     *            The arguments that are passed to the factory method that
     *            creates the instance to be build.
     * @return An instance of the {@link GenericBuilder} is returned.
     * @throws GenericBuilderException
     *             The exception is thrown if an error occurs while creating an
     *             instance of the class to be built.
     */
    @SuppressWarnings("unchecked")
    public static <T> GenericBuilder<T> getInstanceFromFactoryMethod(Class<T> clazz, String factoryMethodName, Object... args) throws GenericBuilderException {
        try {
            return new GenericBuilder<T>(clazz, (T) clazz.getMethod(factoryMethodName, getParameterTypes(args)).invoke(null, args));
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            throw new GenericBuilderException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T>[] getParameterTypes(Object... propertyValues) {
        Class<T>[] parameterTypes = (Class<T>[]) Array.newInstance(Class.class, propertyValues.length);
        for (int i = 0; i < propertyValues.length; i++) {
            parameterTypes[i] = (Class<T>) propertyValues[i].getClass();
        }
        return parameterTypes;
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
    public GenericBuilder<T> invoke(String methodName, Object... propertyValues) throws GenericBuilderException {
        try {
            Class<T>[] parameterTypes = getParameterTypes(propertyValues);

            Method method = clazz.getMethod(methodName, parameterTypes);
            method.invoke(instance, propertyValues);
            return this;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new GenericBuilderException(e);
        }
    }

    /**
     * The method assumes that a setter method for the passed property exists
     * and calls it with the passed value.
     * 
     * @param property
     *            The property to be set.
     * @param value
     *            The value to set.
     * @return The {@link GenericBuilder} is returned.
     * @throws GenericBuilderException
     *             The exception is thrown if an error occurs while invoking the
     *             method.
     */
    public GenericBuilder<T> set(String property, Object value) throws GenericBuilderException {
        String methodName = "set" + String.valueOf(property.charAt(0)).toUpperCase() + property.substring(1);
        return invoke(methodName, value);
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
