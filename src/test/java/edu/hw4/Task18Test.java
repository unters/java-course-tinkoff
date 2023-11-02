package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Task18.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task18Test {
    @Test
    void getHeaviestFishInMultipleLists_NullListGiven_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            getHeaviestFishInMultipleLists(null);
        });
    }

    @Test
    void getHeaviestFishInMultipleLists_ListOfAnimalsGiven_ReturnExpectedAnswer() {
        // given
        List<Animal> animals1 = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Petya", Type.FISH, Sex.M, 1, 5, 0, false)
        );


        List<Animal> animals2 = Arrays.asList(
            new Animal("Ara", Type.BIRD, Sex.M, 2, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 0, 1, 0, false)
        );

        List<Animal> animals3 = Arrays.asList(
            new Animal("Shark Kevin", Type.FISH, Sex.M, 5, 120, 200, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 0, 1, 0, false)
        );

        Optional<Animal> expectedAnswer = Optional.of(new Animal("Shark Kevin", Type.FISH, Sex.M, 5, 120, 200, false));

        // when
        Optional<Animal> actualAnswer = getHeaviestFishInMultipleLists(animals1, animals2, animals3);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
