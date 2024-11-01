package backend.academy.render;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import backend.academy.input.InputInterface;
import backend.academy.output.OutputInterface;
import backend.academy.random.RandomGeneratorInterface;
import java.util.List;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class DefaultGameIORender implements GameIORenderInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGameIORender.class);
    private final InputInterface inputInterface;
    private final OutputInterface outputInterface;
    private final RandomGeneratorInterface randomGeneratorInterface;

    private <T extends Enum<T>> T selectOption(
        String name, T[] values, RandomGeneratorInterface randomGeneratorInterface
    ) {
        outputInterface.print("Select " + name + ".");

        IntStream.range(0, values.length).forEach(i -> outputInterface.print(i + " - " + values[i]));

        outputInterface.print("other - random.");

        int choice;
        try {
            choice = Integer.parseInt(inputInterface.read());
            if (choice < 0 || choice >= values.length) {
                LOGGER.warn("Invalid input for {}. Selecting random option.", name);
                choice = randomGeneratorInterface.nextInt(values.length);
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid input format for {}. Selecting random option.", name, e);
            choice = randomGeneratorInterface.nextInt(values.length);
        }

        outputInterface.print("Your choice " + name + ": " + values[choice].name());
        LOGGER.info("Selected {}: {}", name, values[choice].name());
        return values[choice];
    }

    @Override
    public Category selectCategory() {
        return selectOption("category", Category.values(), randomGeneratorInterface);
    }

    @Override
    public Difficulty selectDifficulty() {
        return selectOption("difficulty", Difficulty.values(), randomGeneratorInterface);
    }

    @Override
    public String selectNextCharacter() {
        outputInterface.print("Input next char -> ");
        String input = inputInterface.read();
        LOGGER.debug("Selected next character: {}", input);
        return input;
    }

    @Override
    public void print(String string) {
        outputInterface.print(string);
        LOGGER.info("Printed message: {}", string);
    }

    @Override
    public void printInfo(List<Character> word, int remainingAttempts) {
        StringBuilder sb = new StringBuilder();
        for (Character c : word) {
            sb.append(c);
        }
        outputInterface.print("Current word: " + sb);
        outputInterface.print("Remaining attempts: " + remainingAttempts);
        outputInterface.print("If you need the hint enter !.");
        LOGGER.debug("Printed game info. Word: {}, Remaining attempts: {}", sb, remainingAttempts);
    }
}
