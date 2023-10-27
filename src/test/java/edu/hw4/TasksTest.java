package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Tasks.countAnimalsByTypes;
import static edu.hw4.Tasks.getKHeaviestAnimals;
import static edu.hw4.Tasks.sortByHeightAsc;
import static edu.hw4.Tasks.getAnimalWithTheLongestName;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static edu.hw4.Tasks.getMostFrequentSex;

public class TasksTest {
    /* Task 1.  */
    @Test
    void sortByHeightAsc_ListOfAnimalsGiven_ReturnSortedList() {
        // given
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 1, 10, 1, false)
        );

        List<Animal> expectedAnswer = Arrays.asList(
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 1, 10, 1, false),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false)
        );

        // when
        List<Animal> actualAnswer = sortByHeightAsc(animals);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    /* Task 2.  */
    @Test
    void getKHeaviestAnimals_ListOfAnimalsGiven_ReturnSortedList() {
        // given
        int k = 3;
        List<Animal> animals = Arrays.asList(
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 1, 10, 1, false),
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true)
        );

        List<Animal> expectedAnswer = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false)
        );

        // when
        List<Animal> actualAnswer = getKHeaviestAnimals(animals, k);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    /* Task 3.  */
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

    /* Task 4.  */
    @Test
    void getAnimalWithTheLongestName_ListOfAnimalsGiven_ReturnAnimalWithTheLongestName() {
        // given
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 1, 10, 1, false)
        );

        Optional<Animal> expectedAnswer = Optional.of(animals.get(1));

        // when
        Optional<Animal> actualAnswer = getAnimalWithTheLongestName(animals);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    /* Task 5.  */
    @Test
    void getMostFrequentSex_NotEmptyListOfAnimalsGiven_ReturnMostFrequentSex() {
        // given
        List<Animal> animals = Arrays.asList(
            new Animal("Sharik", Type.DOG, Sex.M, 4, 80, 30, true),
            new Animal("Snezhinka", Type.CAT, Sex.F, 2, 40, 4, false),
            new Animal("Ara", Type.BIRD, Sex.M, 3, 81, 1, false),
            new Animal("Tara", Type.SPIDER, Sex.F, 1, 4, 0, true),
            new Animal("Bubble", Type.FISH, Sex.M, 1, 10, 1, false)
        );

        Sex expectedAnswer = Sex.M;

        // when
        Sex actualAnswer = getMostFrequentSex(animals);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
