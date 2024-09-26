package backend.academy.render;

import backend.academy.entity.Category;
import backend.academy.input.InputInterface;
import backend.academy.output.OutputInterface;
import backend.academy.random.RandomGeneratorInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultGameIORenderTest {

    private InputInterface inputInterface;
    private OutputInterface outputInterface;
    private RandomGeneratorInterface randomGeneratorInterface;
    private DefaultGameIORender ioRender;

    @BeforeEach
    void setUp() {
        inputInterface = mock(InputInterface.class);
        outputInterface = mock(OutputInterface.class);
        randomGeneratorInterface = mock(RandomGeneratorInterface.class);
        ioRender = new DefaultGameIORender(inputInterface, outputInterface, randomGeneratorInterface);
    }

    @Test
    void testSelectCategoryValidInput() {
        when(inputInterface.read()).thenReturn("1");

        Category category = ioRender.selectCategory();

        assertEquals(Category.values()[1], category);
    }

    @Test
    void testSelectCategoryInvalidIndex() {
        when(inputInterface.read()).thenReturn("10");
        when(randomGeneratorInterface.nextInt(anyInt())).thenReturn(0);

        Category category = ioRender.selectCategory();

        assertEquals(Category.values()[0], category);
    }

    @Test
    void testSelectCategoryInvalidFormat() {
        when(inputInterface.read()).thenReturn("invalid");
        when(randomGeneratorInterface.nextInt(anyInt())).thenReturn(2);

        Category category = ioRender.selectCategory();

        assertEquals(Category.values()[2], category);

        verify(outputInterface, atLeastOnce()).print(anyString());
    }
}
