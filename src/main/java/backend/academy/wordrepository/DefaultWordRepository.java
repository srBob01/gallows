package backend.academy.wordrepository;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import backend.academy.entity.Word;
import backend.academy.random.RandomGeneratorInterface;
import backend.academy.wordloader.WordLoaderInterface;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultWordRepository implements WordRepositoryInterface {
    private static final int MAX_COUNT_ATTEMPTS = 15;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultWordRepository.class);
    private final List<Word> words;
    private final RandomGeneratorInterface random;

    public DefaultWordRepository(WordLoaderInterface wordLoaderInterface, RandomGeneratorInterface random) {
        this.words = wordLoaderInterface.loadWords();
        this.random = random;
        LOGGER.info("WordRepositoryInterface initialized");
    }

    @Override
    public Word getRandomWord(Category category, Difficulty difficulty) {
        int maxAttempts = MAX_COUNT_ATTEMPTS - difficulty.stepsToSkip();
        List<Word> filteredWords = words.stream()
            .filter(word -> word.category() == category && word.difficulty() == difficulty)
            .filter(word -> !word.word().isEmpty())
            .filter(word -> word.charPositions().size() <= maxAttempts)
            .toList();

        if (filteredWords.isEmpty()) {
            LOGGER.error("No suitable  words found for category: {}, difficulty: {}", category, difficulty);
            throw new IllegalArgumentException("No suitable words found for the specified category and difficulty");
        }

        Word word = filteredWords.get(random.nextInt(filteredWords.size()));
        LOGGER.info("Selected random word: {} (Category: {}, Difficulty: {})", word.word(), category, difficulty);

        return word;
    }
}
