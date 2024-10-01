package backend.academy.wordrepository;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import backend.academy.entity.Word;
import backend.academy.random.RandomGeneratorInterface;
import backend.academy.wordloader.ArrayWordLoader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultWordRepositoryTest {
    private RandomGeneratorInterface randomGeneratorInterface;
    private DefaultWordRepository wordRepository;

    @BeforeEach
    void setUp() {
        // Arrange
        randomGeneratorInterface = mock(RandomGeneratorInterface.class);

        List<Word> testWords = List.of(
            new Word(Difficulty.EASY, Category.SCIENCE, "gravity",
                "A force that pulls objects towards Earth"),
            new Word(Difficulty.MEDIUM, Category.TECHNOLOGY, "algorithm",
                "A set of rules to solve problems"),
            new Word(Difficulty.HARD, Category.ART, "impressionism",
                "A 19th-century art movement"),
            new Word(Difficulty.EASY, Category.MUSIC, "note",
                "A musical sound"),
            new Word(Difficulty.MEDIUM, Category.SPORTS, "marathon",
                "A long-distance race"),
            // Добавляем слово с большим количеством уникальных символов для теста
            new Word(Difficulty.HARD, Category.SCIENCE, "encyclopediaqwerty",
                "A comprehensive reference work"),
            new Word(Difficulty.EASY, Category.TECHNOLOGY, "aaa", "All letters are the same")
        );

        ArrayWordLoader wordLoader = new ArrayWordLoader(testWords);
        wordRepository = new DefaultWordRepository(wordLoader, randomGeneratorInterface);
    }

    @Test
    void testGetRandomWordWithValidCategoryAndDifficulty() {
        // Arrange
        when(randomGeneratorInterface.nextInt(anyInt())).thenReturn(0);
        Category category = Category.TECHNOLOGY;
        Difficulty difficulty = Difficulty.MEDIUM;

        // Act
        Word word = wordRepository.getRandomWord(category, difficulty);

        // Assert
        assertNotNull(word);
        assertEquals("algorithm", word.word());
        assertEquals("A set of rules to solve problems", word.hint());
        assertEquals(Category.TECHNOLOGY, word.category());
        assertEquals(Difficulty.MEDIUM, word.difficulty());
    }

    @Test
    void testGetRandomWordThrowsExceptionWhenNoWordsFound() {
        // Arrange
        Category category = Category.ART;
        Difficulty difficulty = Difficulty.EASY;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            wordRepository.getRandomWord(category, difficulty);
        });
    }

    @Test
    void testGetRandomWordWithTooManyUniqueCharactersThrowsException() {
        // Arrange
        when(randomGeneratorInterface.nextInt(anyInt())).thenReturn(0);
        Category category = Category.SCIENCE;
        Difficulty difficulty = Difficulty.HARD;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            wordRepository.getRandomWord(category, difficulty);
        });
    }

    @Test
    void testEmptyWordListThrowsException() {
        // Arrange
        ArrayWordLoader emptyWordLoader = new ArrayWordLoader(List.of());
        DefaultWordRepository emptyRepo = new DefaultWordRepository(emptyWordLoader, randomGeneratorInterface);
        Category category = Category.SCIENCE;
        Difficulty difficulty = Difficulty.EASY;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            emptyRepo.getRandomWord(category, difficulty);
        });
    }

    @Test
    void testGetWordWithExactAttemptLimit() {
        // Arrange
        List<Word> exactLimitWords = List.of(
            new Word(Difficulty.MEDIUM, Category.TECHNOLOGY, "abcdefgqwert", "Unique letters test")
        );
        ArrayWordLoader wordLoader = new ArrayWordLoader(exactLimitWords);
        DefaultWordRepository exactLimitRepo = new DefaultWordRepository(wordLoader, randomGeneratorInterface);
        when(randomGeneratorInterface.nextInt(anyInt())).thenReturn(0);
        Category category = Category.TECHNOLOGY;
        Difficulty difficulty = Difficulty.MEDIUM;

        // Act
        Word word = exactLimitRepo.getRandomWord(category, difficulty);

        // Assert
        assertNotNull(word);
        assertEquals("abcdefgqwert", word.word());
        assertEquals("Unique letters test", word.hint());
    }

    @Test
    void testGetRandomWordSkipsEmptyWords() {
        // Arrange
        List<Word> wordsWithEmpty = List.of(
            new Word(Difficulty.EASY, Category.SPORTS, "", "Empty word should be skipped"),
            new Word(Difficulty.EASY, Category.SPORTS, "running", "A common sport activity")
        );
        ArrayWordLoader wordLoader = new ArrayWordLoader(wordsWithEmpty);
        DefaultWordRepository repoWithEmpty = new DefaultWordRepository(wordLoader, randomGeneratorInterface);
        when(randomGeneratorInterface.nextInt(anyInt())).thenReturn(0);
        Category category = Category.SPORTS;
        Difficulty difficulty = Difficulty.EASY;

        // Act
        Word word = repoWithEmpty.getRandomWord(category, difficulty);

        // Assert
        assertNotNull(word);
        assertNotEquals("", word.word());
        assertEquals("running", word.word());
        assertEquals("A common sport activity", word.hint());
    }

    @Test
    void testGetRandomWordThrowsExceptionForOnlyEmptyWords() {
        // Arrange
        List<Word> onlyEmptyWords = List.of(
            new Word(Difficulty.EASY, Category.TECHNOLOGY, "", "Empty word"),
            new Word(Difficulty.MEDIUM, Category.TECHNOLOGY, "", "Another empty word"),
            new Word(Difficulty.HARD, Category.TECHNOLOGY, "", "Yet another empty word")
        );
        ArrayWordLoader wordLoader = new ArrayWordLoader(onlyEmptyWords);
        DefaultWordRepository repoWithOnlyEmpty = new DefaultWordRepository(wordLoader, randomGeneratorInterface);
        Category category = Category.TECHNOLOGY;
        Difficulty difficulty = Difficulty.EASY;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            repoWithOnlyEmpty.getRandomWord(category, difficulty);
        });
    }
}
