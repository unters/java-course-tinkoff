package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @ParameterizedTest
    @CsvSource({"2, 3, 6", "10, 10, 100", "42, 0, 0", "-3, -4, 12", "-5, 5, -25"})
    void sum_methodCalled_ReturnsResultOfMultiplication(int a, int b, int expectedAnswer) {
        // given
        ByteBuddyAgent.install();
        new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(ElementMatchers.named("sum"))
            .intercept(MethodDelegation.to(ArithmeticUtilsSubstitute.class))
            .make()
            .load(ClassLoader.getSystemClassLoader(), ClassReloadingStrategy.fromInstalledAgent())
            .getLoaded();

        // when
        int actualAnswer = ArithmeticUtils.sum(a, b);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    public class ArithmeticUtils {
        public static int sum(int a, int b) {
            return a + b;
        }
    }

    private static final class ArithmeticUtilsSubstitute {
        @BindingPriority(3)
        public static int multiply(int a, int b) {
            return a * b;
        }

        @BindingPriority(2)
        public static int substract(int a, int b) {
            return a - b;
        }
    }
}
