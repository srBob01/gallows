package backend.academy.render;

import backend.academy.output.OutputInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

class DefaultGallowsRenderTest {

    private OutputInterface outputInterface;
    private DefaultGallowsRender defaultGallowsRender;

    @BeforeEach
    void setUp() {
        // Arrange
        outputInterface = mock(OutputInterface.class);
        defaultGallowsRender = new DefaultGallowsRender(outputInterface);
    }

    @Test
    void testPrintStateValidState() {
        // Arrange
        int totalStates = defaultGallowsRender.steps().length; // Количество состояний виселицы

        for (int i = 0; i < totalStates; i++) {
            // Act
            defaultGallowsRender.printState(i);

            // Assert
            // Захватываем аргумент, переданный в outputInterface.print
            ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
            verify(outputInterface).print(captor.capture());

            // Проверяем, что был вызван правильный вывод для текущего состояния
            String expectedOutput = defaultGallowsRender.steps()[i];
            assertEquals(expectedOutput, captor.getValue());

            // Reset для следующей итерации
            reset(outputInterface);
        }
    }

    @Test
    void testPrintStateInvalidStateBelowRange() {
        // Arrange
        int invalidState = -1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            defaultGallowsRender.printState(invalidState);
        });
    }

    @Test
    void testPrintStateInvalidStateAboveRange() {
        // Arrange
        int invalidState = 15; // Максимальное состояние 14

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            defaultGallowsRender.printState(invalidState);
        });
    }

    @Test
    void testIntermediateState() {

        String expectedOutputState7 = """
               +---+
               |   |
               O   |
              /|\\  |
               |   |
                   |
            /=====\\
              =========
            """;

        // Act
        defaultGallowsRender.printState(7);

        // Assert
        verify(outputInterface).print(expectedOutputState7);
    }
}
