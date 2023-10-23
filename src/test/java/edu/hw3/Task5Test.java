package edu.hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw3.Task5.Contact;
import static edu.hw3.Task5.buildSortedContactsList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task5Test {
    private static final class InvalidArgumentsArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(List.of("Donald Knut", "J. Brown"), "ASC"),
                Arguments.of(List.of("Alexander Biryukov", "unters"), "DESC"),
                Arguments.of(List.of("John Locke", "Thomas Aquinas", "David", "Rene Descartes"), "As you wish")
            );
        }
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void buildSortedContactsList_NullOrEmptyListGiven_ReturnEmptyList(List<String> fullNamesList) {
        List<Contact> actualAnswer = buildSortedContactsList(fullNamesList, "ASC");
        assertThat(actualAnswer).isEqualTo(new ArrayList<Contact>());
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidArgumentsArgumentsProvider.class)
    void buildSortedContactsList_InvalidParametersPassed_ThrowIllegalArgumentException(
        List<String> fullNamesList,
        String sortingOrder
    ) {
        assertThrows(IllegalArgumentException.class, () -> {
            buildSortedContactsList(fullNamesList, sortingOrder);
        });
    }

    @Test
    void buildSortedContactsList_ValidListAscSort_ReturnExpectedAnswer() {
        // given
        List<String> fullNamesList = List.of("John Locke", "Thomas Aquinas", "David", "Rene Descartes");
        List<Contact> expectedAnswer = List.of(
            new Contact("Thomas", "Aquinas"),
            new Contact("David", null),
            new Contact("Rene", "Descartes"),
            new Contact("John", "Locke")
        );

        // when
        List<Contact> actualAnswer = buildSortedContactsList(fullNamesList, "ASC");

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @Test
    void buildSortedContactsList_ValidListDescSort_ReturnExpectedAnswer() {
        // given
        List<String> fullNamesList = List.of("Paul Erdos", "Leonhard Euler", "Carl");
        List<Contact> expectedAnswer = List.of(
            new Contact("Leonhard", "Euler"),
            new Contact("Paul", "Erdos"),
            new Contact("Carl", null)
        );

        // when
        List<Contact> actualAnswer = buildSortedContactsList(fullNamesList, "DESC");

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
