package edu.project1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameSessionTest {
    private static final int ATTEMPTS = 6;

    @Test
    void run_WinInputSequenceWithoutMistakesWithoutDuplicatingInputLetters_ReturnWin() throws IOException {
        // given
        RiddleWord riddleWord = new RiddleWord("kettle");
        String inputString = "t\nl";
        try (InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
             OutputStream outputStream = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(outputStream)) {
            // when
            GameSession session = new GameSession(riddleWord, ATTEMPTS, inputStream, printStream);
            GameSession.GameSessionResult result = session.start();

            // then
            assertThat(result).isEqualTo(GameSession.GameSessionResult.Win);
        }
    }

    @Test
    void run_WinInputSequenceWithMistakesWithDuplicatingInputLetters_ReturnWin() throws IOException {
        // given
        RiddleWord riddleWord = new RiddleWord("kettle");
        String inputString = "g\ni\ng\no\nh\ng\nw\nt\nt\nt\nt\nt\nt\nt\nt\nt\nt\nt\nt\nt\nt\nt\nt\nt\nt\nl";
        try (InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
             OutputStream outputStream = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(outputStream)) {
            // when
            GameSession session = new GameSession(riddleWord, ATTEMPTS, inputStream, printStream);
            GameSession.GameSessionResult result = session.start();

            // then
            assertThat(result).isEqualTo(GameSession.GameSessionResult.Win);
        }
    }

    @Test
    void run_LossInputSequence_ReturnLoss() throws IOException {
        // given
        RiddleWord riddleWord = new RiddleWord("assertion");
        String inputString = "s\nt\na\nz\nx\nc\nv\nb\np";
        try (InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
             OutputStream outputStream = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(outputStream)) {
            // when
            GameSession session = new GameSession(riddleWord, ATTEMPTS, inputStream, printStream);
            GameSession.GameSessionResult result = session.start();

            // then
            assertThat(result).isEqualTo(GameSession.GameSessionResult.Loss);
        }
    }

    @Test
    void run_stopInputSequence_ReturnLoss() throws IOException {
        // given
        RiddleWord riddleWord = new RiddleWord("assertion");
        String inputString = "s\nt\ns\nz\nx\nc\nstop";
        try (InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
             OutputStream outputStream = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(outputStream)) {
            // when
            GameSession session = new GameSession(riddleWord, ATTEMPTS, inputStream, printStream);
            GameSession.GameSessionResult result = session.start();

            // then
            assertThat(result).isEqualTo(GameSession.GameSessionResult.GameStopped);
        }
    }
}
