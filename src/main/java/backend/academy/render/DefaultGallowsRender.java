package backend.academy.render;

import backend.academy.output.OutputInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class DefaultGallowsRender implements GallowsRenderInterface {
    private static final Logger logger = LoggerFactory.getLogger(DefaultGallowsRender.class);

    private final OutputInterface outputInterface;
    @Getter
    private final String[] steps = {
        """
               +---+
              [x]  |
              xxx  |
               x   |
              x x  |
                   |
              =========
            """,
        """
               +---+
              [x]  |
              /x\\  |
               |   |
              / \\  |
                   |
              =========
            """,
        """
               +---+
              [O]  |
              /|\\  |
               |   |
              / \\  |
                   |
              =========
            """,
        """
               +---+
              [O   |
              /|\\  |
               |   |
              / \\  |
                   |
              =========
            """,
        """
               +---+
               |   |
               O   |
              /|\\  |
               |   |
              / \\  |
                   |
              =========
            """,
        """
               +---+
               |   |
               O   |
              /|\\  |
               |   |
              / \\  |
            /=====\\
              =========
            """,
        """
               +---+
               |   |
               O   |
              /|\\  |
               |   |
              /    |
            /=====\\
              =========
            """,
        """
               +---+
               |   |
               O   |
              /|\\  |
               |   |
                   |
            /=====\\
              =========
            """,
        """
               +---+
               |   |
               O   |
              /|\\  |
                   |
                   |
            /=====\\
              =========
            """,
        """
               +---+
               |   |
               O   |
              /|   |
                   |
                   |
            /=====\\
              =========
            """,
        """
               +---+
               |   |
               O   |
               |   |
                   |
                   |
            /=====\\
              =========
            """,
        """
               +---+
               |   |
               O   |
                   |
                   |
                   |
            /=====\\
              =========
            """,
        """
               +---+
               |   |
                   |
                   |
                   |
                   |
            /=====\\
              =========
            """,
        """
               +---+
                   |
                   |
                   |
                   |
                   |
            /=====\\
              =========
            """,
        """
                   +
                   |
                   |
                   |
                   |
                   |
            /=====\\
              =========
            """
    };

    public void printState(int state) {
        if (state >= 0 && state < steps.length) {
            outputInterface.print(steps[state]);
        } else {
            throw new IllegalArgumentException("Invalid state: " + state);
        }
    }
}