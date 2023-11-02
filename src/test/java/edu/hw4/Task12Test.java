package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Task12.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task12Test {
    @Test
    void getNumberOfAnimalsWithWeightGreaterThanHeight_NullListGiven_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            getNumberOfAnimalsWithWeightGreaterThanHeight(null);
        });
    }

    @Test
    void getNumberOfAnimalsWithWeightGreaterThanHeight_ListOfAnimalsGiven_ReturnExpectedAnswer() {
        // given
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 2, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 0, 1, 0, false),
            new Animal("Shark Bob", Type.FISH, Sex.M, 0, 120, 200, false)
        );

        long expectedAnswer = 1;

        // when
        long actualAnswer = getNumberOfAnimalsWithWeightGreaterThanHeight(animals);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
