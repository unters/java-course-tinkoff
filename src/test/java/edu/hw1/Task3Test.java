package edu.hw1;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Test {
    private static final class NullArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(null, new int[] {1, 2, 3}),
                Arguments.of(new int[] {1, 2, 3}, null),
                Arguments.of(null, null)
            );
        }
    }

    private static final class EmptyArraysArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new int[] {}, new int[] {1, 2, 3}),
                Arguments.of(new int[] {1, 2, 3}, new int[] {}),
                Arguments.of(new int[] {}, new int[] {})
            );
        }
    }

    private static final class NestableArraysArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new int[] {1, 2, 3, 4}, new int[] {0, 6}),
                Arguments.of(new int[] {3, 1}, new int[] {4, 0}),
                Arguments.of(new int[] {0}, new int[] {-1, 1}),
                Arguments.of(new int[] {-10, 10}, new int[] {Integer.MIN_VALUE, -11, -10, 9, 11, Integer.MAX_VALUE})
            );
        }
    }

    private static final class NonNestableArraysArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new int[] {1, 2}, new int[] {1, 2}),
                Arguments.of(new int[] {0, 6}, new int[] {1, 2, 3, 4}),
                Arguments.of(new int[] {4, 0}, new int[] {3, 1}),
                Arguments.of(new int[] {-1, 1}, new int[] {0}),
                Arguments.of(new int[] {Integer.MIN_VALUE, -11, -10, 9, 11, Integer.MAX_VALUE}, new int[] {-10, 10})
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(NullArgumentsProvider.class)
    void isNestable_OneOrBothArgumentsAreNull_ThrowIllegalArgumentException(int[] a, int[] b) {
        assertThrows(IllegalArgumentException.class, () -> {
            Task3.isNestable(a, b);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(EmptyArraysArgumentsProvider.class)
    void isNestable_OneOrBothInputArraysAreEmpty_ReturnFalse(int[] a, int[] b) {
        boolean actualAnswer = Task3.isNestable(a, b);
        assertThat(actualAnswer).isEqualTo(false);
    }

    @ParameterizedTest
    @ArgumentsSource(NestableArraysArgumentsProvider.class)
    void isNestable_AIsNestableInB_ReturnTrue(int[] a, int[] b) {
        boolean actualAnswer = Task3.isNestable(a, b);
        assertThat(actualAnswer).isEqualTo(true);
    }

    @ParameterizedTest
    @ArgumentsSource(NonNestableArraysArgumentsProvider.class)
    void isNestable_AIsNotNestableInB_ReturnFalse(int[] a, int[] b) {
        boolean actualAnswer = Task3.isNestable(a, b);
        assertThat(actualAnswer).isEqualTo(false);
    }
}
