package backend.academy.gamestate;

import backend.academy.entity.GameState;
import java.util.List;

public interface GameStateManagerInterface {
    GameState isInProgress(List<Character> guessedChars, int remainingAttempts);
}
