package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Test {
    @Test void isNestable_OneOrBothArgumentsAreNull_ThrowIllegalArgumentException() {
        int[][] inputA = {{1, 2, 3}, null, null};
        int[][] inputB = {null, {1, 2, 3}, null};
        for (int i = 0; i < inputA.length; ++i) {
            // given
            int[] a = inputA[i];
            int[] b = inputB[i];

            // when
            Exception e = assertThrows(IllegalArgumentException.class, () -> {
                Task3.isNestable(a, b);
            });

            // then
            assertThat(e.getMessage()).contains("cannot be null");
        }
    }

    @Test void isNestable_OneOrBothInputArraysAreEmpty_ReturnFalse() {
        int[][] inputA = {{}, {1, 2}, {}};
        int[][] inputB = {{3, 4}, {}, {}};
        for (int i = 0; i < inputA.length; ++i) {
            // given
            int[] a = inputA[i];
            int[] b = inputB[i];

            // when
            boolean actualAnswer = Task3.isNestable(a, b);

            // then
            assertThat(actualAnswer).isEqualTo(false);
        }
    }

    @Test void isNestable_AIsNestableInB_ReturnTrue() {
        int[][] inputA = {{1, 2, 3, 4}, {3, 1}, {0}, {-10, 10}};
        int[][] inputB = {{0, 6}, {4, 0}, {-1, 1}, {Integer.MIN_VALUE, -11, -10, -9, 9, 10, 11, Integer.MAX_VALUE}};
        for (int i = 0; i < inputA.length; ++i) {
            // given
            int[] a = inputA[i];
            int[] b = inputB[i];

            // when
            boolean actualAnswer = Task3.isNestable(a, b);

            // then
            assertThat(actualAnswer).isEqualTo(true);
        }
    }

    @Test void isNestable_AIsNotNestableInB_ReturnFalse() {
        int[][] inputA =
            {{9, 9, 8}, {1, 2, 3, 4}, {1, 2}, {Integer.MIN_VALUE, -11, -10, -9, 9, 10, 11, Integer.MAX_VALUE}};
        int[][] inputB = {{8, 9}, {2, 3}, {0}, {0, 1}};
        for (int i = 0; i < inputA.length; ++i) {
            // given
            int[] a = inputA[i];
            int[] b = inputB[i];

            // when
            boolean actualAnswer = Task3.isNestable(a, b);

            // then
            assertThat(actualAnswer).isEqualTo(false);
        }
    }
}
