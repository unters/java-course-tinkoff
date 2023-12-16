package edu.hw10.task1.generators;

import edu.hw10.task1.annotations.NotNull;
import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class StringGenerator implements Generator {
    private static final String[] DICTIONARY = new String[] {
        "alpha",
        "beta",
        "gamma",
        "Hello World!",
        "Java",
        "Rectangle"
    };

    @Override
    public Object generate(Annotation[] annotations) {
        boolean permitNull = true;
        for (var annotation : annotations) {
            if (annotation instanceof NotNull) {
                permitNull = false;
            }
        }

        if (permitNull && ThreadLocalRandom.current().nextInt(DICTIONARY.length + 1) == 1) {
            return null;
        }

        return DICTIONARY[ThreadLocalRandom.current().nextInt(DICTIONARY.length)];
    }

}
