package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SampleTest {
    @Test
    @DisplayName("Фильтрация четных чисел")
    void filterEvenNumbers() {
        // given
        int[] numbers = new int[] {1, 2, 3, 4, 5};

        // when
        int[] evenNumbers = EvenArrayUtils.filter(numbers);

        // then
        assertThat(evenNumbers)
            .containsExactly(2, 4)
            .hasSize(2);
    }

//    @Test
//    void failedTest() {
//        assertThat(5).isEqualTo(2 * 2);
//    }
}
