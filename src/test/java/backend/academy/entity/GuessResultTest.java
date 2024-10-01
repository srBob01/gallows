package backend.academy.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static backend.academy.entity.GuessResult.MAX_COUNT_ATTEMPTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GuessResultTest {

    private GuessResult guessResult;
    private final int remains = MAX_COUNT_ATTEMPTS - Difficulty.MEDIUM.stepsToSkip();

    @BeforeEach
    void setUp() {
        // Arrange
        Word word = new Word(Difficulty.EASY, Category.SCIENCE, "gravity",
            "A force that pulls objects towards Earth");
        guessResult =
            new GuessResult(word, Difficulty.MEDIUM.stepsToSkip()); // Сложность уменьшает количество шагов на 3
    }

    @Test
    void testCorrectGuessLetter() {
        // Arrange
        String input = "g";

        // Act
        ResultEnter result = guessResult.resultEnter(input);

        // Assert
        assertEquals(ResultEnter.CharTrue, result);
        assertEquals('g', guessResult.character());
        assertEquals(remains, guessResult.remainingAttempts()); // Оставшиеся попытки не изменились
    }

    @Test
    void testIncorrectGuessLetter() {
        // Arrange
        String input = "x";

        // Act
        ResultEnter result = guessResult.resultEnter(input);

        // Assert
        assertEquals(ResultEnter.CharFalse, result);
        assertEquals('x', guessResult.character());
        assertEquals(remains - 1, guessResult.remainingAttempts()); // Одна попытка потеряна
    }

    @Test
    void testGuessLetterCaseInsensitive() {
        // Arrange
        String input = "G";

        // Act
        ResultEnter result = guessResult.resultEnter(input);

        // Assert
        assertEquals(ResultEnter.CharTrue, result);
        assertEquals('g', guessResult.character()); // Приведено к нижнему регистру
        assertEquals(remains, guessResult.remainingAttempts()); // Попытки не изменились
    }

    @Test
    void testHintUsage() {
        // Arrange
        String input = "!";

        // Act
        ResultEnter result = guessResult.resultEnter(input);

        // Assert
        assertEquals(ResultEnter.Hint, result);
        assertNull(guessResult.character());
        assertEquals(remains - 1, guessResult.remainingAttempts());
    }

    @Test
    void testInvalidCharacterInput() {
        // Arrange
        String input = "$";

        // Act
        ResultEnter result = guessResult.resultEnter(input);

        // Assert
        assertEquals(ResultEnter.Error, result); // Некорректный символ
        assertNull(guessResult.character());
        assertEquals(remains - 1, guessResult.remainingAttempts()); // Одна попытка потеряна
    }

    @Test
    void testEmptyStringInput() {
        // Arrange
        String input = "";

        // Act
        ResultEnter result = guessResult.resultEnter(input);

        // Assert
        assertEquals(ResultEnter.Missing, result); // Пустая строка
        assertNull(guessResult.character());
        assertEquals(remains, guessResult.remainingAttempts()); // Попытки не потеряны
    }

    @Test
    void testLongStringInput() {
        // Arrange
        String input = "longinput";

        // Act
        ResultEnter result = guessResult.resultEnter(input);

        // Assert
        assertEquals(ResultEnter.Missing, result); // Ввод строки длиной больше 1
        assertNull(guessResult.character());
        assertEquals(remains, guessResult.remainingAttempts()); // Попытки не потеряны
    }

    @Test
    void testInvalidLetterAndCaseInsensitive() {
        // Arrange
        String input = "X";

        // Act
        ResultEnter result = guessResult.resultEnter(input);

        // Assert
        assertEquals(ResultEnter.CharFalse, result); // Неправильная буква
        assertEquals('x', guessResult.character()); // Приведено к нижнему регистру
        assertEquals(remains - 1, guessResult.remainingAttempts()); // Попытка потеряна
    }

    @Test
    void testRepeatedIncorrectGuesses() {
        // Arrange
        String input = "x";

        // Act
        ResultEnter result1 = guessResult.resultEnter(input);

        // Assert
        assertEquals(ResultEnter.CharFalse, result1);
        assertEquals('x', guessResult.character());
        assertEquals(remains - 1, guessResult.remainingAttempts());

        // Act
        ResultEnter result2 = guessResult.resultEnter(input); // Повторно вводим ту же букву

        // Assert
        assertEquals(ResultEnter.CharFalse, result2);
        assertEquals(remains - 2, guessResult.remainingAttempts()); // Еще одна попытка потеряна
    }

    @Test
    void testMultipleCorrectGuesses() {
        // Arrange
        String input1 = "g";
        String input2 = "r";

        // Act
        ResultEnter result1 = guessResult.resultEnter(input1);
        ResultEnter result2 = guessResult.resultEnter(input2);

        // Assert
        assertEquals(ResultEnter.CharTrue, result1);
        assertEquals(remains, guessResult.remainingAttempts());

        assertEquals(ResultEnter.CharTrue, result2);
        assertEquals(remains, guessResult.remainingAttempts()); // Попытки не потеряны за правильные ответы
    }
}
