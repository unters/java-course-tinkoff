package edu.hw2;

import static edu.hw2.Task4.CallingInfo;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.function.Supplier;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    /* Class to test Task4.getCallingInfo().  */
    private static class ClassA {
        public static CallingInfo method_1() {
            return Task4.getCallingInfo();
        }

        public static CallingInfo method_2() {
            return Task4.getCallingInfo();
        }
    }

    /* Class to test Task4.getCallingInfo().  */
    private static class ClassB {
        public static CallingInfo method_1() {
            return Task4.getCallingInfo();
        }
    }

    /* Parameterized tests for getCallingInfo() is probably an overengeneering, but it was a useful experience.  */
    private static final class CallerArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(
                    (Supplier<CallingInfo>) ClassA::method_1,
                    new CallingInfo("edu.hw2.Task4Test$ClassA", "method_1")
                ),
                Arguments.of(
                    (Supplier<CallingInfo>) ClassA::method_2,
                    new CallingInfo("edu.hw2.Task4Test$ClassA", "method_2")
                ),
                Arguments.of(
                    (Supplier<CallingInfo>) ClassB::method_1,
                    new CallingInfo("edu.hw2.Task4Test$ClassB", "method_1")
                )
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(CallerArgumentsProvider.class)
    void getCallingInfo_methodCalled_ReturnExpectedAnswer(Supplier<CallingInfo> caller, CallingInfo expectedAnswer) {
        CallingInfo actualAnswer = caller.get();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
