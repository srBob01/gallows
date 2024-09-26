package backend.academy.gamestate;

import backend.academy.entity.GameState;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DefaultGameStateManagerTest {
    @Test
    void testGameLostWhenNoAttemptsLeft() {
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('_', '_', '_'); // слово не угадано

        // 0 оставшихся попыток
        GameState result = defaultGameStateManager.isInProgress(guessedChars, 0);

        assertEquals(GameState.Lose, result);
    }

    @Test
    void testGameLostWhenNegativeAttempts() {
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('_', '_', '_'); // слово не угадано

        // отрицательное количество оставшихся попыток
        GameState result = defaultGameStateManager.isInProgress(guessedChars, -1);

        assertEquals(GameState.Lose, result);
    }

    @Test
    void testGameWonWhenAllCharactersGuessed() {
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('g', 'r', 'a', 'v', 'i', 't', 'y'); // слово угадано

        // Оставшиеся попытки могут быть любыми
        GameState result = defaultGameStateManager.isInProgress(guessedChars, 3);

        assertEquals(GameState.Win, result);
    }

    @Test
    void testGameInProgressWhenCharactersRemaining() {
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('g', '_', '_', 'v', '_', '_', '_'); // часть слова угадана

        // Есть оставшиеся попытки
        GameState result = defaultGameStateManager.isInProgress(guessedChars, 5);

        assertEquals(GameState.InProgress, result);
    }

    @Test
    void testGameInProgressWithSingleUnderscoreAndAttemptsRemaining() {
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('g', 'r', 'a', 'v', 'i', 't', '_'); // осталась одна буква

        // Есть оставшиеся попытки
        GameState result = defaultGameStateManager.isInProgress(guessedChars, 2);

        assertEquals(GameState.InProgress, result);
    }

    @Test
    void testGameLostWithUnderscoresAndNoAttemptsLeft() {
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('g', '_', '_', 'v', '_', '_', '_'); // часть слова угадана

        // 0 оставшихся попыток
        GameState result = defaultGameStateManager.isInProgress(guessedChars, 0);

        assertEquals(GameState.Lose, result);
    }
}
