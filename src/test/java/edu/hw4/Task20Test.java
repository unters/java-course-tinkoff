package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Task20.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task20Test {
    @Test
    void getValidationResults_ListOfAnimalsGiven_ReturnExpectedAnswer() {
        // given
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, -4, -120, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 2, 1, -1, false),
            new Animal(null, Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 0, 1, 0, false)
        );
        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put("Sharik", "age is negative; height is non-positive");
        expectedAnswer.put("Ara", "weight is negative");
        expectedAnswer.put("null", "name is null");

        // when
        Map<String, String> actualAnswer = getValidationResults(animals);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @Test
    void getValidationResults_ListOfAnimalsWithSameNameGiven_ReturnExpectedAnswer() {
        // given
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, -4, 120, 30, true),
            new Animal("Sharik", Type.DOG, Sex.M, 4, -120, 30, false)
        );

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put("Sharik", "age is negative");
        expectedAnswer.put("Sharik 1", "height is non-positive");

        // when
        Map<String, String> actualAnswer = getValidationResults(animals);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
