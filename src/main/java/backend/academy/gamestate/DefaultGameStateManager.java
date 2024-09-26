package backend.academy.gamestate;

import backend.academy.entity.GameState;
import java.util.List;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
public class DefaultGameStateManager implements GameStateManagerInterface {
    private static final Logger logger = LoggerFactory.getLogger(DefaultGameStateManager.class);

    @Override
    public GameState isInProgress(List<Character> guessedChars, int remainingAttempts) {
        if (remainingAttempts <= 0) {
            logger.info("Game lost. No remaining attempts.");
            return GameState.Lose;
        } else if (!guessedChars.contains('_')) {
            logger.info("Game won! All characters guessed.");
            return GameState.Win;
        } else {
            logger.debug("Game in progress. Remaining attempts: " + remainingAttempts);
            return GameState.InProgress;
        }
    }
}
