package backend.academy.logic;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import backend.academy.entity.Word;
import backend.academy.render.GallowsRenderInterface;
import backend.academy.render.GameIORenderInterface;
import backend.academy.wordrepository.WordRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultGameLogicTest {

    private GameIORenderInterface gameIoRenderInterface;
    private DefaultGameLogic logicRender;

    @BeforeEach
    void setUp() {
        gameIoRenderInterface = mock(GameIORenderInterface.class);
        GallowsRenderInterface gallowsRenderInterface = mock(GallowsRenderInterface.class);
        WordRepositoryInterface wordRepositoryInterface = mock(WordRepositoryInterface.class);

        Word word = new Word(Difficulty.MEDIUM, Category.SCIENCE, "gravity",
            "A force that pulls objects towards Earth");

        logicRender = new DefaultGameLogic(gameIoRenderInterface, gallowsRenderInterface, wordRepositoryInterface);

        when(wordRepositoryInterface.getRandomWord(any(Category.class), any(Difficulty.class))).thenReturn(word);
    }

    @Test
    void testGameEndsWithWin() throws Exception {
        // Мокируем ввод правильных символов, чтобы завершить игру победой
        when(gameIoRenderInterface.selectDifficulty()).thenReturn(Difficulty.MEDIUM);
        when(gameIoRenderInterface.selectCategory()).thenReturn(Category.SCIENCE);
        when(gameIoRenderInterface.selectNextCharacter()).thenReturn("g", "r", "a", "v", "i", "t", "y");

        logicRender.game();

        // Проверяем, что игра закончилась победой
        verify(gameIoRenderInterface).print("You are win!. It was word: gravity");
    }

    @Test
    void testGameEndsWithLose() throws Exception {
        // Мокируем неверные попытки, чтобы завершить игру поражением
        when(gameIoRenderInterface.selectDifficulty()).thenReturn(Difficulty.MEDIUM);
        when(gameIoRenderInterface.selectCategory()).thenReturn(Category.SCIENCE);
        when(gameIoRenderInterface.selectNextCharacter()).thenReturn("x", "z", "q", "w", "e", "f", "b", "z",
            "q", "w", "e", "f", "b"); // Все попытки неверные

        logicRender.game();

        // Проверяем, что игра закончилась поражением
        verify(gameIoRenderInterface).print("You are lose. It was word: gravity");
    }

    @Test
    void testHintUsage() throws Exception {
        // Мокируем ввод символов и подсказки
        when(gameIoRenderInterface.selectDifficulty()).thenReturn(Difficulty.MEDIUM);
        when(gameIoRenderInterface.selectCategory()).thenReturn(Category.SCIENCE);
        when(gameIoRenderInterface.selectNextCharacter()).thenReturn("g", "!", "r", "a", "v", "i", "t", "y");

        logicRender.game();

        // Проверяем, что подсказка была выведена
        verify(gameIoRenderInterface).print("Hint: A force that pulls objects towards Earth");
    }

    @Test
    void testValidAndInvalidCharacterInput() throws Exception {
        // Мокируем правильные и неправильные вводы
        when(gameIoRenderInterface.selectDifficulty()).thenReturn(Difficulty.MEDIUM);
        when(gameIoRenderInterface.selectCategory()).thenReturn(Category.SCIENCE);
        when(gameIoRenderInterface.selectNextCharacter()).thenReturn("g", "x", "r", "z", "a", "v", "i", "t",
            "y");

        logicRender.game();

        // Проверяем, что правильные буквы были угаданы
        verify(gameIoRenderInterface).print("You are win!. It was word: gravity");
    }
}
