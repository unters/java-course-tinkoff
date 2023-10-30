package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Task5.getMostFrequentSex;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task5Test {
    @Test
    void getMostFrequentSex_NullListGiven_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            getMostFrequentSex(null);
        });
    }

    @Test
    void getMostFrequentSex_NotEmptyListOfAnimalsGiven_ReturnMostFrequentSex() {
        // given
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 1, 10, 1, false)
        );

        Sex expectedAnswer = Sex.M;

        // when
        Sex actualAnswer = getMostFrequentSex(animals);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
