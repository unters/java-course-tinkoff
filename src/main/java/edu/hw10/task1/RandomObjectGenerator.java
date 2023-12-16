package edu.hw10.task1;

import edu.hw10.task1.generators.BooleanGenerator;
import edu.hw10.task1.generators.Generator;
import edu.hw10.task1.generators.IntGenerator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import edu.hw10.task1.generators.StringGenerator;
import lombok.NonNull;
import lombok.SneakyThrows;

public class RandomObjectGenerator {
    private static Map<Class, Generator> GENERATORS = new HashMap<>();

    static {
        GENERATORS.put(int.class, new IntGenerator());
        GENERATORS.put(boolean.class, new BooleanGenerator());
        GENERATORS.put(String.class, new StringGenerator());
    }

    @SneakyThrows
    public static <T> T nextObject(@NonNull Class<T> clazz) {
        Constructor<T>[] constructors = (Constructor<T>[]) clazz.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new IllegalArgumentException("given class doesn't have public constructors");
        }

        Constructor<T> constructor = constructors[ThreadLocalRandom.current().nextInt(constructors.length)];
        Object[] arguments = initializeArguments(constructor.getParameters());
        return constructor.newInstance(arguments);
    }

    @SneakyThrows({IllegalAccessException.class, InvocationTargetException.class})
    public static <T> T nextObject(@NonNull Class<T> clazz, @NonNull String fabricMethod)
        throws NoSuchMethodException {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(fabricMethod)) {
                /* Here must be checked that method returns object of class T, but I failed to figure out how to
                 * perform it.  */
                Object[] arguments = initializeArguments(method.getParameters());
                return (T) method.invoke(null, arguments);
            }
        }

        throw new NoSuchMethodException("Class " + clazz.getName() + " doesn't have method with name" + fabricMethod);
    }

    private static Object[] initializeArguments(Parameter[] parameters) {
        Object[] arguments = new Object[parameters.length];
        for (int i = 0; i < parameters.length; ++i) {
            Generator generator = GENERATORS.get(parameters[i].getType());
            arguments[i] = (generator == null) ? null : generator.generate(parameters[i].getAnnotations());
        }
        return arguments;
    }
}
