package backend.academy.gamestate;

import backend.academy.entity.GameState;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultGameStateManagerTest {

    @Test
    void testGameLostWhenNoAttemptsLeft() {
        // Arrange
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('_', '_', '_'); // слово не угадано
        int remainingAttempts = 0; // 0 оставшихся попыток

        // Act
        GameState result = defaultGameStateManager.isInProgress(guessedChars, remainingAttempts);

        // Assert
        assertEquals(GameState.Lose, result);
    }

    @Test
    void testGameLostWhenNegativeAttempts() {
        // Arrange
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('_', '_', '_'); // слово не угадано
        int remainingAttempts = -1; // отрицательное количество оставшихся попыток

        // Act
        GameState result = defaultGameStateManager.isInProgress(guessedChars, remainingAttempts);

        // Assert
        assertEquals(GameState.Lose, result);
    }

    @Test
    void testGameWonWhenAllCharactersGuessed() {
        // Arrange
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('g', 'r', 'a', 'v', 'i', 't', 'y'); // слово угадано
        int remainingAttempts = 3; // Оставшиеся попытки могут быть любыми

        // Act
        GameState result = defaultGameStateManager.isInProgress(guessedChars, remainingAttempts);

        // Assert
        assertEquals(GameState.Win, result);
    }

    @Test
    void testGameInProgressWhenCharactersRemaining() {
        // Arrange
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('g', '_', '_', 'v', '_', '_', '_'); // часть слова угадана
        int remainingAttempts = 5; // Есть оставшиеся попытки

        // Act
        GameState result = defaultGameStateManager.isInProgress(guessedChars, remainingAttempts);

        // Assert
        assertEquals(GameState.InProgress, result);
    }

    @Test
    void testGameInProgressWithSingleUnderscoreAndAttemptsRemaining() {
        // Arrange
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('g', 'r', 'a', 'v', 'i', 't', '_'); // осталась одна буква
        int remainingAttempts = 2; // Есть оставшиеся попытки

        // Act
        GameState result = defaultGameStateManager.isInProgress(guessedChars, remainingAttempts);

        // Assert
        assertEquals(GameState.InProgress, result);
    }

    @Test
    void testGameLostWithUnderscoresAndNoAttemptsLeft() {
        // Arrange
        DefaultGameStateManager defaultGameStateManager = new DefaultGameStateManager();
        List<Character> guessedChars = List.of('g', '_', '_', 'v', '_', '_', '_'); // часть слова угадана
        int remainingAttempts = 0; // 0 оставшихся попыток

        // Act
        GameState result = defaultGameStateManager.isInProgress(guessedChars, remainingAttempts);

        // Assert
        assertEquals(GameState.Lose, result);
    }
}
