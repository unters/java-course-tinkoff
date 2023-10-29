package edu.hw3;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Task2 {
    private static final char LEFT_PARENTHESIS = '(';
    private static final char RIGHT_PARENTHESIS = ')';

    private static final String INVALID_ARGUMENT_MESSAGE = "String s must be a \"valid parentheses\" string.";

    public static List<String> clusterizeString(String s) {
        if (s == null) {
            throw new IllegalArgumentException("s cannot be null");
        }

        List<String> clusters = new ArrayList<>();
        /* Here Deque is used as a stack. I know there is a Stack implementation in Java, but as far as I understand, it
         * is considered obsolete - in many sources it is strongly recommended to use Deque instead of Stack.  */
        Deque<Character> deque = new LinkedList<>();
        int lastIndex = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == LEFT_PARENTHESIS) {
                deque.addLast(c);
            } else if (c == RIGHT_PARENTHESIS) {
                if (deque.isEmpty() || deque.getLast() != LEFT_PARENTHESIS) {
                    throw new IllegalArgumentException(INVALID_ARGUMENT_MESSAGE);
                }

                deque.removeLast();
                if (deque.isEmpty()) {
                    clusters.add(s.substring(lastIndex, i + 1));
                    lastIndex = i + 1;
                }
            } else {
                throw new IllegalArgumentException(INVALID_ARGUMENT_MESSAGE);
            }
        }

        if (!deque.isEmpty()) {
            throw new IllegalArgumentException(INVALID_ARGUMENT_MESSAGE);
        }

        return clusters;
    }

    private Task2() {
    }
}
