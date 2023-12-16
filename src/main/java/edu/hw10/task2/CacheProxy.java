package edu.hw10.task2;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CacheProxy implements InvocationHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Path STORAGE =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "edu", "persistence");

    private final Object targetObject;
    private final Map<String, Object> inMemoryCache = new HashMap<>();
    private final Path storageFilepath;

    public static <T> T create(T object, Class<?> objectClass) {
        return (T) Proxy.newProxyInstance(
            objectClass.getClassLoader(),
            objectClass.getInterfaces(),
            new CacheProxy(object)
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
                if (cacheAnnotation.persist()) {
                    Files.write(storageFilepath, (key + " : " + result + "\n").getBytes(), StandardOpenOption.APPEND);
                }
                return result;
            }
        }

        return method.invoke(targetObject, args);
    }

    @SneakyThrows
    private CacheProxy(Object object) {
        this.targetObject = object;
        storageFilepath = Paths.get(STORAGE.toString(), UUID.randomUUID() + ".cache");
        Files.createFile(storageFilepath);
    }
}
