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
        outputInterface = mock(OutputInterface.class);
        defaultGallowsRender = new DefaultGallowsRender(outputInterface);
    }

    @Test
    void testPrintStateValidState() {
        // Проверяем корректность вывода для каждого шага виселицы
        for (int i = 0; i < 15; i++) {
            defaultGallowsRender.printState(i);

            // Захватываем аргумент, переданный в outputInterface.print
            ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
            verify(outputInterface).print(captor.capture());

            // Проверяем, что был вызван правильный вывод для текущего состояния
            String expectedOutput = defaultGallowsRender.steps()[i];
            assertEquals(expectedOutput, captor.getValue());

            // Сбрасываем взаимодействия с outputInterface перед следующим шагом
            reset(outputInterface);
        }
    }

    @Test
    void testPrintStateInvalidStateBelowRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            defaultGallowsRender.printState(-1);
        });
    }

    @Test
    void testPrintStateInvalidStateAboveRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            defaultGallowsRender.printState(15);
        });
    }

    @Test
    void testIntermediateStates() {
        defaultGallowsRender.printState(0);
        verify(outputInterface).print("""
               +---+
              [x]  |
              xxx  |
               x   |
              x x  |
                   |
              =========
            """);
        reset(outputInterface);

        defaultGallowsRender.printState(7);
        verify(outputInterface).print("""
               +---+
               |   |
               O   |
              /|\\  |
               |   |
                   |
            /=====\\
              =========
            """);
    }
}
