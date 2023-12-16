package edu.hw10.task1.generators;

import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class BooleanGenerator implements Generator {
    @Override
    public Object generate(Annotation[] annotations) {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
