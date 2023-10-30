package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Task3.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Test {
    @Test
    void countAnimalsByTypes_NullListGiven_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            countAnimalsByTypes(null);
        });
    }

    @Test
    void countAnimalsByTypes_ListOfAnimalsGiven_ReturnMapWithTypeCount() {
        // given
        List<Animal> animals = Arrays.asList(
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false),
            new Animal("Bubble", Type.FISH, Sex.M, 1, 10, 1, false),
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Max", Type.DOG, Sex.M, 7, 100, 40, true)
        );

        Map<Type, Integer> expectedAnswer = new HashMap<>();
        expectedAnswer.put(Type.DOG, 2);
        expectedAnswer.put(Type.CAT, 1);
        expectedAnswer.put(Type.FISH, 1);
        expectedAnswer.put(Type.BIRD, 1);

        // when
        Map<Type, Integer> actualAnswer = countAnimalsByTypes(animals);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
