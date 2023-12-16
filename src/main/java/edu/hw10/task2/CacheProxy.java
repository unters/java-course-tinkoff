package edu.hw10.task2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CacheProxy implements InvocationHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Object targetObject;
    private final Map<String, Object> inMemoryCache = new HashMap<>();

    public CacheProxy(Object object) {
        this.targetObject = object;
    }

    public static <T> T create(T obj, Class<?> objectClass) {
        return (T) Proxy.newProxyInstance(
            objectClass.getClassLoader(),
            objectClass.getInterfaces(),
            new CacheProxy(obj)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Cache cacheAnnotation = method.getAnnotation(Cache.class);
        String key = method.getName() + Arrays.toString(args);
        if (cacheAnnotation != null) {
            if (inMemoryCache.containsKey(key)) {
                LOGGER.debug("Using cached value [%s] for key [%s]".formatted(inMemoryCache.get(key), key));
                return inMemoryCache.get(key);
            } else {
                Object result = method.invoke(targetObject, args);
                inMemoryCache.put(key, result);
                return result;
            }
        }

        return method.invoke(targetObject, args);
    }
}
