package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task8Test {
    @Test void knightBoardCapture_BoardHasNotExactlyEightRows_ThrowIllegalArgumentException() {
        // given
        int[][] board = {
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
        };

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            Task8.knightBoardCapture(board);
        });

        // then
        assertThat(e.getMessage()).contains("exactly 8 rows");
    }

    @Test void knightBoardCapture_BoardHasNotExactlyEightColumns_ThrowIllegalArgumentException() {
        // given
        int[][] board = {
            {0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
        };

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            Task8.knightBoardCapture(board);
        });

        // then
        assertThat(e.getMessage()).contains("exactly 8 columns");
    }

    @Test void knightBoardCapture_BoardContainsElementsThatAreNotEqualToZeroOrOne_ThrowIllegalArgumentException() {
        // given
        int[][] board = {
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 2, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 9, 0, 0, 0, 0, 0},
        };

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            Task8.knightBoardCapture(board);
        });

        // then
        assertThat(e.getMessage()).contains("cannot contain");
    }

    @Test void knightBoardCapture_NoKnightCanCaptureAnyOtherKnight_ReturnTrue() {
        // given
        int[][] board = {
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0}
        };

        // when
        boolean actualAnswer = Task8.knightBoardCapture(board);

        // then
        assertThat(actualAnswer).isEqualTo(true);
    }

    @Test void knightBoardCapture_ThereIsKnightThatCanCaptureSomeOtherKnight_ReturnFalse() {
        int[][] board = {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1}
        };

        // when
        boolean actualAnswer = Task8.knightBoardCapture(board);

        // then
        assertThat(actualAnswer).isEqualTo(false);
    }
}
