package edu.project1;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

@SuppressWarnings("RegexpSinglelineJava")   // Suppressing is temporary. Or is it?
final class GameSession {
    private static final String STOP_COMMAND = "stop";
    private static final String GUESS_REGEX = "[a-z]";

    private static final String WRONG_INPUT_MESSAGE =
        "Wrong input. Type in a single lowercase english letter to make a guess. Type `" + STOP_COMMAND
            + "` to interrupt the game.";
    private static final String GAME_STOPPED_MESSAGE = "Game session was stopped.";
    private static final String SUCCESSFUL_GUESS_MESSAGE = "That's a hit!";
    private static final String UNSUCCESSFUL_GUESS_MESSAGE = "N-n-nope. Wrong one.";
    private static final String LETTER_HAS_ALREADY_BEEN_PICKED_SUCCESSFULLY_MESSAGE =
        "This letter has been already picked.";
    private static final String LETTER_HAS_ALREADY_BEEN_PICKED_UNSUCCESSFULLY_MESSAGE =
        "You have already picked this letter and it was wrong.";
    private static final String LOSE_MESSAGE = "You have lost. Enter `start` to try again.";
    private static final String WIN_MESSAGE = "You have won! Congratulations!";

    private final String answer;
    private final LowercaseEnglishCharactersSet lettersPresentedInAnswer = new LowercaseEnglishCharactersSet();
    /* `guessedWord` is an array of characters of size answer.length(). It contains characters of string `answer` at
     * corresponding positions that have been guessed by player. Initially, the first and the last characters of this
     * array are equal to the corresponding characters of string answer; other characters are set to '_'.  */
    private final char[] guessedWord;
    private final LowercaseEnglishCharactersSet lettersPickedByPlayer = new LowercaseEnglishCharactersSet();
    /* `maxAttempts` is a positive integer that has a meaning of number of wrong guesses that a player had at the
     * beginning of the game.  */
    private final int maxAttempts;
    /* `attempts` is a positive integer that has a meaning of number of wrong guesses that a player can make at the
     * current moment.  */
    private int attempts;
    private int guessedCharactersCount;

    GameSession(RiddleWord riddleWord, int attempts) {
        this.answer = riddleWord.word();
        for (char c : answer.toCharArray()) {
            lettersPresentedInAnswer.add(c);
        }

        this.attempts = attempts;
        maxAttempts = attempts;

        /* Initialize guessedWord according to the rules.  */
        guessedWord = new char[answer.length()];
        Arrays.fill(guessedWord, '_');

        for (int i = 0; i < answer.length(); ++i) {
            if (answer.charAt(i) == answer.charAt(0)) {
                guessedWord[i] = answer.charAt(0);
            }
        }

        for (int i = 0; i < answer.length(); ++i) {
            if (answer.charAt(i) == answer.charAt(answer.length() - 1)) {
                guessedWord[i] = answer.charAt(answer.length() - 1);
            }
        }

        lettersPickedByPlayer.add(guessedWord[0]);
        lettersPickedByPlayer.add(guessedWord[answer.length() - 1]);

        guessedCharactersCount = 0;
        for (char c : guessedWord) {
            if (c != '_') {
                ++ guessedCharactersCount;
            }
        }
    }

    void start() {
        System.out.println(getGameState());

        Scanner scanner = new Scanner(System.in);
        while (attempts > 0 && guessedCharactersCount != answer.length()) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (STOP_COMMAND.equals(input)) {
                System.out.println(GAME_STOPPED_MESSAGE);
                return;
            } else if (!Pattern.matches(GUESS_REGEX, input)) {
                System.out.println(WRONG_INPUT_MESSAGE);
                System.out.println(getGameState());
                continue;
            }

            char letter = input.charAt(0);
            if (lettersPresentedInAnswer.contains(letter)) {
                if (lettersPickedByPlayer.contains(letter)) {
                    System.out.println(LETTER_HAS_ALREADY_BEEN_PICKED_SUCCESSFULLY_MESSAGE);
                } else {
                    applyGuess(letter);
                    lettersPickedByPlayer.add(letter);
                    System.out.println(SUCCESSFUL_GUESS_MESSAGE);
                }
            } else {
                if (lettersPickedByPlayer.contains(letter)) {
                    System.out.println(LETTER_HAS_ALREADY_BEEN_PICKED_UNSUCCESSFULLY_MESSAGE);
                } else {
                    --attempts;
                    lettersPickedByPlayer.add(letter);
                    System.out.println(UNSUCCESSFUL_GUESS_MESSAGE);
                }
            }

            System.out.println(getGameState());
        }

        if (attempts == 0) {
            System.out.println(LOSE_MESSAGE);
        } else {
            System.out.println(WIN_MESSAGE);
        }
    }

    private void applyGuess(char guess) {
        for (int i = 0; i < answer.length(); ++i) {
            if (answer.charAt(i) == guess) {
                ++guessedCharactersCount;
                guessedWord[i] = guess;
            }
        }
    }

    private String getGameState() {
        String guessedSequence = new String(guessedWord);
        return guessedSequence + " | " + attempts + " / " + maxAttempts + " left.";
    }

    private static final class LowercaseEnglishCharactersSet {
        private static final String ILLEGAL_ARGUMENT_MESSAGE =
            "Illegal argument. `LowercaseEnglishCharactersSet` can store only lowercase english letters.";

        private final boolean[] characterIsPresent = new boolean[26];

        void add(char c) {
            if (!Character.isLowerCase(c)) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
            }

            characterIsPresent[c - 'a'] = true;
        }

        boolean contains(char c) {
            if (!Character.isLowerCase(c)) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
            }

            return characterIsPresent[c - 'a'];
        }
    }
}
