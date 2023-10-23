package edu.hw3;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw3.Task4.convertToRoman;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task4Test {
    private static final class ValidParameterArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(2, "II"),
                Arguments.of(10, "X"),
                Arguments.of(12, "XII"),
                Arguments.of(16, "XVI"),
                Arguments.of(88, "LXXXVIII"),
                Arguments.of(91, "XCI"),
                Arguments.of(152, "CLII"),
                Arguments.of(411, "CDXI"),
                Arguments.of(756, "DCCLVI"),
                Arguments.of(935, "CMXXXV"),
                Arguments.of(1000, "M"),
                Arguments.of(1006, "MVI"),
                Arguments.of(2012, "MMXII"),
                Arguments.of(2789, "MMDCCLXXXIX"),
                Arguments.of(3999, "MMMCMXCIX")
            );
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-4001, -4000, -3999, -123, -1, 0, 4000, 4001, 123492})
    void convertToRoman_InvalidParameterPassed_ThrowIllegalArgumentException(int value) {
        assertThrows(IllegalArgumentException.class, () -> {
            convertToRoman(value);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ValidParameterArgumentsProvider.class)
    void convertToRoman_ValidParameterPassed_ReturnExpectedAnswer(int value, String expectedAnswer) {
        String actualAnswer = convertToRoman(value);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
