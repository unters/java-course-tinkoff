package edu.hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.TreeMap;
import java.util.stream.Stream;
import static edu.hw3.Task7.CustomTreeMapComparator;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    private static final class KeyValueArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of("Donald Knut", "Professor"),
                Arguments.of(42, "Answer to the most important question."),
                Arguments.of('P', 3.14)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(KeyValueArgumentsProvider.class)
    <K extends Comparable<K>, V> void containsKey_KeyValuePairHasBeenAdded_ReturnTrue(K key, V value) {
        // given
        TreeMap<K, V> tree = new TreeMap<>(new CustomTreeMapComparator<K>());

        // when
        tree.put(key, value);
        boolean actualAnswer = tree.containsKey(key);

        // then
        assertThat(actualAnswer).isTrue();
    }

    @Test
    void containsKey_PairWithNullKeyHasBeenAdded_ReturnsTrue() {
        // given
        TreeMap<String, String> tree = new TreeMap<>(new CustomTreeMapComparator<String>());
        String key = null;
        String value = "That is a value for a `null` key.";

        // when
        tree.put(key, value);
        boolean actualAnswer = tree.containsKey(key);

        // then
        assertThat(actualAnswer).isTrue();
    }
}
