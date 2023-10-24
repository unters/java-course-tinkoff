package edu.hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw3.Task8.BackwardIterator;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task8Test {
    private static final class ListArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new ArrayList<>(List.of(1, 2, 3)), List.of(3, 2, 1)),
                Arguments.of(new LinkedList<>(List.of(1, 2, 3)), List.of(3, 2, 1))
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ListArgumentsProvider.class)
    <T> void next_IterateOverTheList_ReturnElementsInReverseOrder(List<T> list, List<T> expectedAnswer) {
        // given
        BackwardIterator<T> it = new BackwardIterator<>(list);

        // when
        List<T> actualAnswer = new ArrayList<>();
        while (it.hasNext()) {
            actualAnswer.add(it.next());
        }

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @Test
    void hasNext_EmptyListGiven_ReturnFalse() {
        // given
        List<Object> list = new ArrayList<>();
        BackwardIterator<Object> it = new BackwardIterator<>(list);

        // when
        boolean actualAnswer = it.hasNext();

        // then
        assertThat(actualAnswer).isFalse();
    }
}
