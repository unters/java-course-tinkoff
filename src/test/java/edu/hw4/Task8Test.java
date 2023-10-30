package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Task8.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task8Test {
    @Test
    void getHeaviestAnimalWithLimitedHeight_NullListGiven_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            getHeaviestAnimalWithLimitedHeight(null, 120);
        });
    }

    @Test
    void getHeaviestAnimalWithLimitedHeight_NotEmptyListOfAnimalsGiven_ReturnHeaviestAnimal() {
        // given
        int height = 30;
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 1, 10, 1, false)
        );

        Optional<Animal> expectedAnswer = Optional.of(new Animal("Bubble", Type.FISH, Sex.M, 1, 10, 1, false));

        // when
        Optional<Animal> actualAnswer = getHeaviestAnimalWithLimitedHeight(animals, height);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
