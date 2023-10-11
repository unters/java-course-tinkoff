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
        public static CallingInfo methodOne() {
            return Task4.getCallingInfo();
        }

        public static CallingInfo methodTwo() {
            return Task4.getCallingInfo();
        }

        public static CallingInfo methodWithLambda() {
            /* IDE hint says that the following functional interface implementation can be replaced with lambda
             * expression or even a reference to a method. However, I have intentionally implemented
             * Supplier<CallingInfo> the way I did to see what name is assigned to an anonymous class implementing
             * a functional interface (this name appeared to be "1").  */
            Supplier<CallingInfo> caller = new Supplier<CallingInfo>() {
                @Override
                public CallingInfo get() {
                    return Task4.getCallingInfo();
                }
            };

            return caller.get();
        }
    }

    /* Class to test Task4.getCallingInfo().  */
    private static class ClassB {
        public CallingInfo callingInfo;

        private ClassB() {
            callingInfo = Task4.getCallingInfo();
        }

        public static CallingInfo methodOne() {
            return Task4.getCallingInfo();
        }

        public static CallingInfo methodTwo() {
            ClassB b = new ClassB();
            return b.callingInfo;
        }
    }

    /* Parameterized tests for getCallingInfo() is probably an overengeneering, but it was a useful experience.  */
    private static final class CallerArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(
                    (Supplier<CallingInfo>) ClassA::methodOne,
                    new CallingInfo("edu.hw2.Task4Test$ClassA", "methodOne")
                ),
                Arguments.of(
                    (Supplier<CallingInfo>) ClassA::methodTwo,
                    new CallingInfo("edu.hw2.Task4Test$ClassA", "methodTwo")
                ),
                Arguments.of(
                    (Supplier<CallingInfo>) ClassA::methodWithLambda,
                    new CallingInfo("edu.hw2.Task4Test$ClassA$1", "get")
                ),
                Arguments.of(
                    (Supplier<CallingInfo>) ClassB::methodOne,
                    new CallingInfo("edu.hw2.Task4Test$ClassB", "methodOne")
                ),
                Arguments.of(
                    (Supplier<CallingInfo>) ClassB::methodTwo,
                    new CallingInfo("edu.hw2.Task4Test$ClassB", "<init>")
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
