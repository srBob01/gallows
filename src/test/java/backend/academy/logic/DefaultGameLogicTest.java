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
        // Arrange
        gameIoRenderInterface = mock(GameIORenderInterface.class);
        GallowsRenderInterface gallowsRenderInterface = mock(GallowsRenderInterface.class);
        WordRepositoryInterface wordRepositoryInterface = mock(WordRepositoryInterface.class);

        // Создаем тестовое слово
        Word word = new Word(Difficulty.MEDIUM, Category.SCIENCE, "gravity",
            "A force that pulls objects towards Earth");

        // Инициализируем объект логики игры
        logicRender = new DefaultGameLogic(gameIoRenderInterface, gallowsRenderInterface, wordRepositoryInterface);

        // Настраиваем поведение моков
        when(wordRepositoryInterface.getRandomWord(any(Category.class), any(Difficulty.class))).thenReturn(word);
    }

    @Test
    void testGameEndsWithWin() {
        // Arrange
        when(gameIoRenderInterface.selectDifficulty()).thenReturn(Difficulty.MEDIUM);
        when(gameIoRenderInterface.selectCategory()).thenReturn(Category.SCIENCE);

        when(gameIoRenderInterface.selectNextCharacter()).thenReturn("g", "r", "a", "v", "i", "t",
            "y");// Мокируем последовательные правильные вводы символов для завершения игры победой

        // Act
        logicRender.game();

        // Assert
        verify(gameIoRenderInterface).print(
            "You are win!. It was word: gravity");// Проверяем, что сообщение о победе было выведено
    }

    @Test
    void testGameEndsWithLose() {
        // Arrange
        when(gameIoRenderInterface.selectDifficulty()).thenReturn(Difficulty.MEDIUM);
        when(gameIoRenderInterface.selectCategory()).thenReturn(Category.SCIENCE);
        when(gameIoRenderInterface.selectNextCharacter()).thenReturn(
            "x", "z", "q", "w", "e", "f", "b", "z",
            "q", "w", "e", "f", "b"
        ); // Все попытки неверные

        // Act
        logicRender.game();

        // Assert
        verify(gameIoRenderInterface).print(
            "You are lose. It was word: gravity");// Проверяем, что сообщение о поражении было выведено
    }

    @Test
    void testHintUsage() {
        // Arrange
        when(gameIoRenderInterface.selectDifficulty()).thenReturn(Difficulty.MEDIUM);
        when(gameIoRenderInterface.selectCategory()).thenReturn(Category.SCIENCE);
        when(gameIoRenderInterface.selectNextCharacter()).thenReturn("g", "!", "r", "a", "v", "i", "t",
            "y");// Мокируем ввод символов, включая запрос подсказки ("!")

        // Act
        logicRender.game();

        // Assert
        verify(gameIoRenderInterface).print(
            "Hint: A force that pulls objects towards Earth");// Проверяем, что подсказка была выведена
    }

    @Test
    void testValidAndInvalidCharacterInput() {
        // Arrange
        when(gameIoRenderInterface.selectDifficulty()).thenReturn(Difficulty.MEDIUM);
        when(gameIoRenderInterface.selectCategory()).thenReturn(Category.SCIENCE);
        when(gameIoRenderInterface.selectNextCharacter()).thenReturn("g", "x", "r", "z", "a", "v", "i", "t",
            "y");// Мокируем смешанные правильные и неправильные вводы символов

        // Act
        logicRender.game();

        // Assert
        verify(gameIoRenderInterface).print(
            "You are win!. It was word: gravity");// Проверяем, что игра закончилась победой
    }
}
