package edu.hw1;

public class Task8 {
    private static final int[][] MOVES = {
        {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}
    };

    @SuppressWarnings("MagicNumber")
    public static boolean knightBoardCapture(int[][] board) {
        if (board.length != 8) {
            throw new IllegalArgumentException("board should have exactly 8 rows.");
        }

        for (int i = 0; i < 8; ++i) {
            if (board[i].length != 8) {
                throw new IllegalArgumentException("board should have exactly 8 columns.");
            }
        }

        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                if (board[y][x] == 0) {
                    continue;
                }

                if (board[y][x] != 1) {
                    throw new IllegalArgumentException("board cannot contain any values except for 0 and 1.");
                }

                for (int[] move : MOVES) {
                    int yNew = y + move[0];
                    int xNew = x + move[1];
                    if (yNew >= 0 && yNew < 8 && xNew >= 0 && xNew < 8 && board[yNew][xNew] == 1) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private Task8() {
    }
}
