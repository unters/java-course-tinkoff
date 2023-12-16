package edu.hw10.task1.generators;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class IntGenerator implements Generator {
    @Override
    public Integer generate(Annotation[] annotations) {
        int minValue = Integer.MIN_VALUE;
        int maxValue = Integer.MAX_VALUE;
        for (var annotation : annotations) {
            if (annotation instanceof Min minAnno && minAnno.value() >= Integer.MIN_VALUE &&
                minAnno.value() <= Integer.MAX_VALUE && minAnno.value() <= maxValue) {
                minValue = (int) minAnno.value();
            } else if (annotation instanceof Max maxAnno && maxAnno.value() >= Integer.MIN_VALUE &&
                maxAnno.value() <= Integer.MAX_VALUE && maxAnno.value() >= minValue) {
                maxValue = (int) maxAnno.value();
            }
        }

        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }
}
