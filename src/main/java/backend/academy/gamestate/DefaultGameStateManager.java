package backend.academy.gamestate;

import backend.academy.entity.GameState;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultGameStateManager implements GameStateManagerInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGameStateManager.class);

    @Override public GameState isInProgress(List<Character> guessedChars, int remainingAttempts) {
        if (remainingAttempts <= 0) {
            LOGGER.info("Game lost. No remaining attempts.");
            return GameState.Lose;
        } else if (!guessedChars.contains('_')) {
            LOGGER.info("Game won! All characters guessed.");
            return GameState.Win;
        } else {
            LOGGER.debug("Game in progress. Remaining attempts: {}", remainingAttempts);
            return GameState.InProgress;
        }
    }
}
