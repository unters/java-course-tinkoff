package edu.hw1;

public class Task8 {
    private static final int[][] moves = {
        {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}
    };

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

                for (int[] move : moves) {
                    int y_ = y + move[0];
                    int x_ = x + move[1];
                    if (y_ >= 0 && y_ < 8 && x_ >= 0 && x_ < 8 && board[y_][x_] == 1)
                        return false;
                }
            }
        }

        return true;
    }
}
