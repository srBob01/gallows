package backend.academy.wordrepository;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import backend.academy.entity.Word;
import backend.academy.random.RandomGeneratorInterface;
import backend.academy.wordloader.WordLoaderInterface;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultWordRepository implements WordRepositoryInterface {
    private final static int MAX_COUNT_ATTEMPTS = 15;
    private static final Logger logger = LoggerFactory.getLogger(DefaultWordRepository.class);
    private final List<Word> words;
    private final RandomGeneratorInterface random;

    public DefaultWordRepository(WordLoaderInterface wordLoaderInterface, RandomGeneratorInterface random)
        throws IOException {
        this.words = wordLoaderInterface.loadWords();
        this.random = random;
        logger.info("WordRepositoryInterface initialized");
    }

    @Override
    public Word getRandomWord(Category category, Difficulty difficulty) {
        List<Word> filteredWords = words.stream()
            .filter(word -> word.category() == category && word.difficulty() == difficulty)
            .toList();

        if (filteredWords.isEmpty()) {
            logger.error("No words found for category: {}, difficulty: {}", category, difficulty);
            throw new IllegalArgumentException("No words found for the specified category and difficulty");
        }

        Word word = filteredWords.get(random.nextInt(filteredWords.size()));

        int uniqueCharCount = word.charPositions().keySet().size();
        int remainingAttempts = MAX_COUNT_ATTEMPTS - word.difficulty().stepsToSkip();
        if (uniqueCharCount > remainingAttempts) {
            logger.error("Selected word '{}' has too many unique characters ({}) for the available attempts ({})",
                word.word(), uniqueCharCount, remainingAttempts);
            throw new IllegalStateException("The word has too many unique characters for the available attempts");
        }

        logger.info("Selected random word: {} (Category: {}, Difficulty: {})", word.word(), category, difficulty);
        return word;
    }
}
