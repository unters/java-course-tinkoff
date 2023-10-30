package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task9Test {
    @Test
    void countAnimalsLegs_NotEmptyListOfAnimalsGiven_ReturnExpectedAnswer() {
        // given
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 1, 10, 1, false)
        );

        long expectedAnswer = 18;

        // when
        long actualAnswer = Task9.countAnimalsLegs(animals);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
