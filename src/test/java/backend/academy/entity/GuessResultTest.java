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
        Word word = new Word(Difficulty.EASY, Category.SCIENCE, "gravity", "A force that pulls objects towards Earth");
        guessResult =
            new GuessResult(word, Difficulty.MEDIUM.stepsToSkip()); // Сложность уменьшает количество шагов на 3
    }

    @Test
    void testCorrectGuessLetter() {
        ResultEnter result = guessResult.resultEnter("g");
        assertEquals(ResultEnter.CharTrue, result);
        assertEquals('g', guessResult.character());
        assertEquals(remains, guessResult.remainingAttempts()); // Оставшиеся попытки не изменились
    }

    @Test
    void testIncorrectGuessLetter() {
        ResultEnter result = guessResult.resultEnter("x");
        assertEquals(ResultEnter.CharFalse, result);
        assertEquals('x', guessResult.character());
        assertEquals(remains - 1, guessResult.remainingAttempts()); // Одна попытка потеряна
    }

    @Test
    void testGuessLetterCaseInsensitive() {
        ResultEnter result = guessResult.resultEnter("G"); // Вводим с другой регистром
        assertEquals(ResultEnter.CharTrue, result);
        assertEquals('g', guessResult.character()); // Приведено к нижнему регистру
        assertEquals(remains, guessResult.remainingAttempts()); // Попытки не изменились
    }

    @Test
    void testHintUsage() {
        ResultEnter result = guessResult.resultEnter("!");
        assertEquals(ResultEnter.Hint, result);
        assertNull(guessResult.character());
        assertEquals(remains - 1, guessResult.remainingAttempts()); // Одна попытка потеряна за использование подсказки
    }

    @Test
    void testInvalidCharacterInput() {
        ResultEnter result = guessResult.resultEnter("$");
        assertEquals(ResultEnter.Error, result); // Некорректный символ
        assertNull(guessResult.character());
        assertEquals(remains - 1, guessResult.remainingAttempts()); // Одна попытка потеряна
    }

    @Test
    void testEmptyStringInput() {
        ResultEnter result = guessResult.resultEnter("");
        assertEquals(ResultEnter.Missing, result); // Пустая строка
        assertNull(guessResult.character());
        assertEquals(remains, guessResult.remainingAttempts()); // Попытки не потеряны
    }

    @Test
    void testLongStringInput() {
        ResultEnter result = guessResult.resultEnter("longinput");
        assertEquals(ResultEnter.Missing, result); // Ввод строки длиной больше 1
        assertNull(guessResult.character());
        assertEquals(remains, guessResult.remainingAttempts()); // Попытки не потеряны
    }

    @Test
    void testInvalidLetterAndCaseInsensitive() {
        ResultEnter result = guessResult.resultEnter("X");
        assertEquals(ResultEnter.CharFalse, result); // Неправильная буква
        assertEquals('x', guessResult.character()); // Приведено к нижнему регистру
        assertEquals(remains - 1, guessResult.remainingAttempts()); // Попытка потеряна
    }

    @Test
    void testRepeatedIncorrectGuesses() {
        // Повторно вводим неверную букву
        ResultEnter result1 = guessResult.resultEnter("x");
        assertEquals(ResultEnter.CharFalse, result1);
        assertEquals('x', guessResult.character());
        assertEquals(remains - 1, guessResult.remainingAttempts());

        ResultEnter result2 = guessResult.resultEnter("x"); // Повторно вводим ту же букву
        assertEquals(ResultEnter.CharFalse, result2);
        assertEquals(remains - 2, guessResult.remainingAttempts()); // Еще одна попытка потеряна
    }

    @Test
    void testMultipleCorrectGuesses() {
        ResultEnter result1 = guessResult.resultEnter("g");
        assertEquals(ResultEnter.CharTrue, result1);
        assertEquals(remains, guessResult.remainingAttempts());

        ResultEnter result2 = guessResult.resultEnter("r");
        assertEquals(ResultEnter.CharTrue, result2);
        assertEquals(remains, guessResult.remainingAttempts()); // Попытки не потеряны за правильные ответы
    }
}
