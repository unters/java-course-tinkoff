package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {
    @Test
    void getHeaviestAnimalsByType_NotEmptyListOfAnimalsGiven_ReturnExpectedAnswer() {
        // given
        List<Animal> animals = Arrays.asList(
            new Animal("Diana", Type.DOG, Sex.M, 2, 60, 19, false),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false),
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Max", Type.DOG, Sex.M, 7, 100, 40, true)
        );

        Map<Type, Animal> expectedAnswer = new HashMap<>();
        expectedAnswer.put(Type.DOG, new Animal("Max", Type.DOG, Sex.M, 7, 100, 40, true));
        expectedAnswer.put(Type.BIRD, new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false));

        // when
        Map<Type, Animal> actualAnswer = Task6.getHeaviestAnimalsByType(animals);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
