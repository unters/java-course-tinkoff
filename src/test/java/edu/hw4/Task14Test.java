package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Task14.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task14Test {
    @Test
    void hasDogWithHeightGreaterOrEqualThanGivenHeight_NullListGiven_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            hasDogWithHeightGreaterOrEqualThanGivenHeight(null, 20);
        });
    }

    @Test
    void hasDogWithHeightGreaterOrEqualThanGivenHeight_ListOfAnimalsWithDogHigherThanHeightGiven_ReturnTrue() {
        // given
        int height = 100;
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 120, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 2, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 0, 1, 0, false)
        );

        boolean expectedAnswer = true;

        // when
        boolean actualAnswer = hasDogWithHeightGreaterOrEqualThanGivenHeight(animals, height);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @Test
    void getDangerousAnimals_ListOfAnimalsWithDogsOfNotEnoughHeightGiven_ReturnFalse() {
        // given
        int height = 130;
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 120, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 2, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 0, 1, 0, false)
        );

        boolean expectedAnswer = false;

        // when
        boolean actualAnswer = hasDogWithHeightGreaterOrEqualThanGivenHeight(animals, height);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
