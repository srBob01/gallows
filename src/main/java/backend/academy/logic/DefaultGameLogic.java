package backend.academy.logic;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import backend.academy.entity.GameState;
import backend.academy.entity.GuessResult;
import backend.academy.entity.ResultEnter;
import backend.academy.entity.Word;
import backend.academy.gamestate.DefaultGameStateManager;
import backend.academy.gamestate.GameStateManagerInterface;
import backend.academy.render.GallowsRenderInterface;
import backend.academy.render.GameIORenderInterface;
import backend.academy.wordrepository.WordRepositoryInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultGameLogic implements GameLogicInterface {
    private static final Logger logger = LoggerFactory.getLogger(DefaultGameLogic.class);
    private final GameIORenderInterface gameIoRenderInterface;
    private final GallowsRenderInterface gallowsRenderInterface;
    private final WordRepositoryInterface wordRepositoryInterface;

    public DefaultGameLogic(
        GameIORenderInterface gameIoRenderInterface,
        GallowsRenderInterface gallowsRenderInterface,
        WordRepositoryInterface wordRepositoryInterface,
        GameStateManagerInterface gameStateManager
    ) {
        this.gameIoRenderInterface = gameIoRenderInterface;
        this.gallowsRenderInterface = gallowsRenderInterface;
        this.wordRepositoryInterface = wordRepositoryInterface;
        logger.info("DefaultGameLogic initialized.");
    }

    public void game() {
        logger.info("Game started.");

        Difficulty difficulty = gameIoRenderInterface.selectDifficulty();
        logger.info("Selected difficulty: {}", difficulty);

        Category category = gameIoRenderInterface.selectCategory();
        logger.info("Selected category: {}", category);

        Word word = wordRepositoryInterface.getRandomWord(category, difficulty);
        logger.info("Random word selected: {}", word.word());

        GameStateManagerInterface defaultGameStateManager = new DefaultGameStateManager();
        GuessResult guessResult = new GuessResult(word, difficulty.stepsToSkip());
        List<Character> guessedChars = new ArrayList<>(Collections.nCopies(word.word().length(), '_'));
        GameState gameState = GameState.InProgress;

        while (gameState.equals(GameState.InProgress)) {
            gameIoRenderInterface.printInfo(guessedChars, guessResult.remainingAttempts());
            logger.debug("Current word: {}, Remaining attempts: {}", guessedChars, guessResult.remainingAttempts());

            ResultEnter resultEnter = guessResult.resultEnter(gameIoRenderInterface.selectNextCharacter());
            logger.debug("Result of character input: {}", resultEnter);

            switch (resultEnter) {
                case CharTrue -> {
                    word.enterCharacter(guessedChars, guessResult.character());
                    logger.info("Correct character guessed: {}", guessResult.character());
                }
                case CharFalse -> {
                    gallowsRenderInterface.printState(guessResult.remainingAttempts());
                    logger.info("Incorrect guess. Remaining attempts: {}", guessResult.remainingAttempts());
                }
                case Error -> {
                    gallowsRenderInterface.printState(guessResult.remainingAttempts());
                    logger.error("Error during input. Remaining attempts: {}", guessResult.remainingAttempts());
                }
                case Hint -> {
                    gameIoRenderInterface.print("Hint: " + word.hint());
                    gallowsRenderInterface.printState(guessResult.remainingAttempts());
                    logger.info("Hint used: {}", word.hint());
                }
                case Missing -> {
                    gameIoRenderInterface.print("Try again.");
                    logger.warn("Missing or invalid input.");
                }
            }
            gameState = defaultGameStateManager.isInProgress(guessedChars, guessResult.remainingAttempts());
        }

        gameIoRenderInterface.print(gameState.info() + ". It was word: " + word.word());
        logger.info("Game ended with result: {}. Word was: {}", gameState.info(), word.word());
    }
}
