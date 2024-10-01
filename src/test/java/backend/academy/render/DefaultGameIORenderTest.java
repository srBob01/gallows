package backend.academy.render;

import backend.academy.entity.Category;
import backend.academy.input.InputInterface;
import backend.academy.output.OutputInterface;
import backend.academy.random.RandomGeneratorInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultGameIORenderTest {

    private InputInterface inputInterface;
    private RandomGeneratorInterface randomGeneratorInterface;
    private DefaultGameIORender ioRender;

    @BeforeEach
    void setUp() {
        // Arrange
        inputInterface = mock(InputInterface.class);
        OutputInterface outputInterface = mock(OutputInterface.class);
        randomGeneratorInterface = mock(RandomGeneratorInterface.class);
        ioRender = new DefaultGameIORender(inputInterface, outputInterface, randomGeneratorInterface);
    }

    @Test
    void testSelectCategoryValidInput() {
        // Arrange
        when(inputInterface.read()).thenReturn("1");
        Category expectedCategory = Category.values()[1];

        // Act
        Category actualCategory = ioRender.selectCategory();

        // Assert
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void testSelectCategoryInvalidIndex() {
        // Arrange
        when(inputInterface.read()).thenReturn("10"); // Предполагаемый недопустимый индекс
        when(randomGeneratorInterface.nextInt(anyInt())).thenReturn(
            0); // Возвращаемый индекс для категории по умолчанию
        Category expectedCategory = Category.values()[0]; // Ожидаемая категория по умолчанию

        // Act
        Category actualCategory = ioRender.selectCategory();

        // Assert
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void testSelectCategoryInvalidFormat() {
        // Arrange
        when(inputInterface.read()).thenReturn("invalid"); // Недопустимый формат ввода
        when(randomGeneratorInterface.nextInt(anyInt())).thenReturn(2); // Возвращаемый индекс для корректной категории
        Category expectedCategory = Category.values()[2]; // Ожидаемая категория после обработки некорректного ввода

        // Act
        Category actualCategory = ioRender.selectCategory();

        // Assert
        assertEquals(expectedCategory, actualCategory);
    }
}
