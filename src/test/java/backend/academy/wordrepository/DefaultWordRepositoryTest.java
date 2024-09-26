package backend.academy.wordrepository;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import backend.academy.entity.Word;
import backend.academy.random.RandomGeneratorInterface;
import backend.academy.wordloader.ArrayWordLoader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultWordRepositoryTest {
    private RandomGeneratorInterface randomGeneratorInterface;
    private DefaultWordRepository wordRepository;

    @BeforeEach
    void setUp() throws IOException {
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
        when(randomGeneratorInterface.nextInt(anyInt())).thenReturn(0);

        Word word = wordRepository.getRandomWord(Category.TECHNOLOGY, Difficulty.MEDIUM);

        assertNotNull(word);
        assertEquals("algorithm", word.word());
        assertEquals("A set of rules to solve problems", word.hint());
        assertEquals(Category.TECHNOLOGY, word.category());
        assertEquals(Difficulty.MEDIUM, word.difficulty());
    }

    @Test
    void testGetRandomWordThrowsExceptionWhenNoWordsFound() {
        // Сценарий: нет слов для конкретной категории и сложности
        assertThrows(IllegalArgumentException.class, () -> {
            wordRepository.getRandomWord(Category.ART, Difficulty.EASY);
        });
    }

    @Test
    void testGetRandomWordWithTooManyUniqueCharactersThrowsException() {
        when(randomGeneratorInterface.nextInt(anyInt())).thenReturn(0);

        assertThrows(IllegalStateException.class, () -> {
            wordRepository.getRandomWord(Category.SCIENCE, Difficulty.HARD);
        });
    }

    @Test
    void testEmptyWordListThrowsException() throws IOException {
        // Сценарий: пустой список слов
        DefaultWordRepository emptyRepo =
            new DefaultWordRepository(new ArrayWordLoader(List.of()), randomGeneratorInterface);

        assertThrows(IllegalArgumentException.class, () -> {
            emptyRepo.getRandomWord(Category.SCIENCE, Difficulty.EASY);
        });
    }

    @Test
    void testGetWordWithExactAttemptLimit() throws IOException {
        // Добавляем слово с точным количеством уникальных символов равным количеству доступных попыток
        List<Word> testWords = List.of(
            new Word(Difficulty.MEDIUM, Category.TECHNOLOGY, "abcdefgqwert", "Unique letters test")
        );

        ArrayWordLoader wordLoader = new ArrayWordLoader(testWords);
        wordRepository = new DefaultWordRepository(wordLoader, randomGeneratorInterface);

        when(randomGeneratorInterface.nextInt(anyInt())).thenReturn(0);

        Word word = wordRepository.getRandomWord(Category.TECHNOLOGY, Difficulty.MEDIUM);

        assertNotNull(word);
        assertEquals("abcdefgqwert", word.word());
        assertEquals("Unique letters test", word.hint());
    }
}
