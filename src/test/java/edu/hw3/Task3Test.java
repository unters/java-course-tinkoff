package edu.hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static edu.hw3.Task3.buildFrequencyDictionary;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Test {
    private static final class ValidParenthesesStringArgumentsProvider implements ArgumentsProvider {
        List<String> testList1() {
            return Arrays.asList("a", "bb", "a", "bb");
        }

        Map<String, Integer> testAnswer1() {
            Map<String, Integer> map = new HashMap<>();
            map.put("bb", 2);
            map.put("a", 2);
            return map;
        }

        List<String> testList2() {
            return Arrays.asList("this", "and", "that", "and");
        }

        Map<String, Integer> testAnswer2() {
            Map<String, Integer> map = new HashMap<>();
            map.put("this", 1);
            map.put("and", 2);
            map.put("that", 1);
            return map;
        }

        List<Integer> testList3() {
            return Arrays.asList(1, 1, 2, 2);
        }

        Map<Integer, Integer> testAnswer3() {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(1, 2);
            map.put(2, 2);
            return map;
        }

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(testList1(), testAnswer1()),
                Arguments.of(testList2(), testAnswer2()),
                Arguments.of(testList3(), testAnswer3())
            );
        }
    }

    @Test
    void buildFrequencyDictionary_EmptyListPassed_ReturnEmptyMap() {
        // given
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> expectedAnswer = new HashMap<>();

        // when
        Map<Integer, Integer> actualAnswer = buildFrequencyDictionary(list);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @NullSource
    void buildFrequencyDictionary_NullReferencePassed_ThrowIllegalArgumentException(List<? super Object> list) {
        assertThrows(IllegalArgumentException.class, () -> {
            buildFrequencyDictionary(list);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ValidParenthesesStringArgumentsProvider.class)
    <T> void buildFrequencyDictionary_ListOfObjectsPassed_ReturnExpectedAnswer(
        List<T> list,
        Map<T, Integer> expectedAnswer
    ) {
        Map<T, Integer> actualAnswer = buildFrequencyDictionary(list);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
