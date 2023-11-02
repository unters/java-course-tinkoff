package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Task7.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task7Test {
    @Test
    void getKthOldestAnimal_NullListGiven_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            getKthOldestAnimal(null, 2);
        });
    }

    @Test
    void getKthOldestAnimal_NotEmptyListOfAnimalsGiven_ReturnKthOldestAnimal() {
        // given
        int k = 3;
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 1, 10, 1, false)
        );

        Animal expectedAnswer = new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false);

        // when
        Animal actualAnswer = getKthOldestAnimal(animals, k);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
