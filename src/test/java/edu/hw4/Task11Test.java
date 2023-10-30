package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Task11.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task11Test {
    @Test
    void getDangerousAnimals_ListOfAnimalsGiven_ReturnExpectedAnswer() {
        // given
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 120, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 2, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 0, 1, 0, false)
        );

        List<Animal> expectedAnswer = List.of(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 120, 30, true)
        );

        // when
        List<Animal> actualAnswer = getDangerousAnimals(animals);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
