package edu.project1;

import java.io.IOException;
import java.util.Scanner;

@SuppressWarnings("RegexpSinglelineJava")   // Suppressing is temporary. Or is it?
final class GameManager {
    /* Game settings.  */
    private static final int MAX_NUMBER_OF_WRONG_GUESSES = 6;

    /* Messages.  */
    private static final String WELCOME_MESSAGE = "Welcome to \"The Hangman\" game. Enter `help` to get the "
        + "list of commands.";
    private static final String HELP_MESSAGE = """
        command     description
        _______________________
        start       Start new game.
        help        Get list of commands.
        rules       Get "The Hangman" game rules.
        exit        Exit the program.
        stop        Stop the gaming session (available in the game session only).""";
    private static final String RULES_MESSAGE = "[rules placeholder]";
    private static final String USAGE_MESSAGE = "Wrong usage. Type in the command from the commands list.";
    private static final String EXIT_MESSAGE = "Thanks for playing \"The Hangman\" game.";
    private static final String WIN_MESSAGE = "You have won! Congratulations!";
    private static final String LOSS_MESSAGE = "You have lost. Enter `start` to try again.";
    private static final String GAME_STOPPED_MESSAGE = "Game session was stopped. Enter `start` to start a new game.";

    static void run() throws IOException {
        Dictionary dictionary = DictionaryManager.getDictionary();

        System.out.println(WELCOME_MESSAGE);
        try (Scanner scanner = new Scanner(System.in)) {
            String command;
            while (true) {
                System.out.print("> ");
                command = scanner.nextLine();
                switch (command) {
                    case "start" -> {
                        GameSession session = new GameSession(
                            dictionary.getRandomWord(),
                            MAX_NUMBER_OF_WRONG_GUESSES,
                            System.in,
                            System.out
                        );
                        GameSession.GameSessionResult result = session.start();
                        switch (result) {
                            case Win -> System.out.println(WIN_MESSAGE);
                            case Loss -> System.out.println(LOSS_MESSAGE);
                            case GameStopped -> System.out.println(GAME_STOPPED_MESSAGE);
                            default -> throw new IOException("Unknown game session result.");
                        }
                    }

                    case "help" -> {
                        System.out.println(HELP_MESSAGE);
                    }

                    case "exit" -> {
                        System.out.println(EXIT_MESSAGE);
                        scanner.close();
                        return;
                    }

                    case "rules" -> {
                        System.out.println(RULES_MESSAGE);
                    }

                    default -> {
                        System.out.println(USAGE_MESSAGE + "\n" + HELP_MESSAGE);
                    }
                }
            }
        }
    }

    private GameManager() {
    }
}
