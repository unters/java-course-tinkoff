package edu.hw10;

import edu.hw10.task1.RandomObjectGenerator;
import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;
import lombok.Getter;
import org.junit.jupiter.api.RepeatedTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task1Test {
    private static final int DEMO_CLASS_VALUE_MIN = 10;
    private static final int DEMO_CLASS_VALUE_MAX = 20;

    @Getter
    public static class DemoClass {
        private final boolean flag;
        private final int value;
        private final String data1;
        private final String data2;

        public DemoClass(
            boolean flag,
            @Min(DEMO_CLASS_VALUE_MIN) @Max(DEMO_CLASS_VALUE_MAX) int value,
            String data1,
            @NotNull String data2
        ) {
            this.flag = flag;
            this.value = value;
            this.data1 = data1;
            this.data2 = data2;
        }

        public static DemoClass create(
            @Min(DEMO_CLASS_VALUE_MIN) @Max(DEMO_CLASS_VALUE_MAX) int value,
            @NotNull String data2
        ) {
            return new DemoClass(true, value, null, data2);
        }
    }

    public record DemoRecord(
        @Min(DEMO_CLASS_VALUE_MIN) @Max(DEMO_CLASS_VALUE_MAX) int value,
        @NotNull String data
    ) {
    }

    @RepeatedTest(100)
    void nextObject_ConstructorIsUsed_GeneratedObjectHasFieldsThatSatisfyAnnotations() {
        DemoClass demoClassObject = RandomObjectGenerator.nextObject(DemoClass.class);

        assertTrue(demoClassObject.getValue() >= DEMO_CLASS_VALUE_MIN);
        assertTrue(demoClassObject.getValue() < DEMO_CLASS_VALUE_MAX);
        assertThat(demoClassObject.data2).isNotNull();
    }

    @RepeatedTest(100)
    void nextObject_FabricMethodIsUsed_GeneratedObjectHasFieldsThatSatisfyAnnotations() throws NoSuchMethodException {
        DemoClass demoClassObject = RandomObjectGenerator.nextObject(DemoClass.class, "create");

        assertTrue(demoClassObject.getValue() >= DEMO_CLASS_VALUE_MIN);
        assertTrue(demoClassObject.getValue() < DEMO_CLASS_VALUE_MAX);
        assertThat(demoClassObject.data2).isNotNull();
    }

    @RepeatedTest(100)
    void nextObject_CanonicalRecordConstructorIsUsed_GeneratedObjectHasFieldsThatSatisfyAnnotations() {
        DemoRecord demoRecordObject = RandomObjectGenerator.nextObject(DemoRecord.class);

        assertTrue(demoRecordObject.value >= DEMO_CLASS_VALUE_MIN);
        assertTrue(demoRecordObject.value < DEMO_CLASS_VALUE_MAX);
        assertThat(demoRecordObject.data).isNotNull();
    }
}
