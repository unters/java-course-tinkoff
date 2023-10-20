package edu.project1;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

final class GameSession {
    private static final String STOP_COMMAND = "stop";
    private static final String GUESS_REGEX = "[a-z]";

    private static final String WRONG_INPUT_MESSAGE =
        "Wrong input. Type in a single lowercase english letter to make a guess. Type `" + STOP_COMMAND
            + "` to interrupt the game.";
    private static final String SUCCESSFUL_GUESS_MESSAGE = "That's a hit!";
    private static final String UNSUCCESSFUL_GUESS_MESSAGE = "N-n-nope. Wrong one.";
    private static final String LETTER_HAS_ALREADY_BEEN_PICKED_SUCCESSFULLY_MESSAGE =
        "This letter has been already picked.";
    private static final String LETTER_HAS_ALREADY_BEEN_PICKED_UNSUCCESSFULLY_MESSAGE =
        "You have already picked this letter and it was wrong.";

    private final String answer;
    private final LowercaseEnglishLettersSet lettersPresentedInAnswer = new LowercaseEnglishLettersSet();
    /* `guessedWord` is an array of characters of size answer.length(). It contains characters of string `answer` at
     * corresponding positions that have been guessed by player. Initially, the first and the last characters of this
     * array are equal to the corresponding characters of string answer; other characters are set to '_'.  */
    private final char[] guessedWord;
    private final LowercaseEnglishLettersSet lettersPickedByPlayer = new LowercaseEnglishLettersSet();
    /* `maxAttempts` is a positive integer that has a meaning of number of wrong guesses that a player had at the
     * beginning of the game.  */
    private final int maxAttempts;
    /* `attempts` is a positive integer that has a meaning of number of wrong guesses that a player can make at the
     * current moment.  */
    private final InputStream inputStream;
    private final PrintStream printStream;
    private int attempts;
    private int guessedCharactersCount;

    enum GameSessionResult {
        GameStopped, Win, Loss
    }

    GameSession(RiddleWord riddleWord, int attempts, InputStream inputStream, PrintStream printStream) {
        this.answer = riddleWord.word();
        for (char c : answer.toCharArray()) {
            lettersPresentedInAnswer.add(c);
        }

        this.attempts = attempts;
        maxAttempts = attempts;
        this.inputStream = inputStream;
        this.printStream = printStream;

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

    GameSessionResult start() {
        printStream.println(getGameState());

        Scanner scanner = new Scanner(inputStream);
        while (attempts > 0 && guessedCharactersCount != answer.length()) {
            printStream.print("> ");
            String input = scanner.nextLine();
            if (STOP_COMMAND.equals(input)) {
                return GameSessionResult.GameStopped;
            } else if (!Pattern.matches(GUESS_REGEX, input)) {
                printStream.println(WRONG_INPUT_MESSAGE);
                printStream.println(getGameState());
                continue;
            }

            char letter = input.charAt(0);
            if (lettersPresentedInAnswer.contains(letter)) {
                if (lettersPickedByPlayer.contains(letter)) {
                    printStream.println(LETTER_HAS_ALREADY_BEEN_PICKED_SUCCESSFULLY_MESSAGE);
                } else {
                    applyGuess(letter);
                    lettersPickedByPlayer.add(letter);
                    printStream.println(SUCCESSFUL_GUESS_MESSAGE);
                }
            } else {
                if (lettersPickedByPlayer.contains(letter)) {
                    printStream.println(LETTER_HAS_ALREADY_BEEN_PICKED_UNSUCCESSFULLY_MESSAGE);
                } else {
                    --attempts;
                    lettersPickedByPlayer.add(letter);
                    printStream.println(UNSUCCESSFUL_GUESS_MESSAGE);
                }
            }

            printStream.println(getGameState());
        }

        return (attempts == 0) ? GameSessionResult.Loss : GameSessionResult.Win;
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
}
