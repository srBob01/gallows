package backend.academy.input;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleInputReader implements InputInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleInputReader.class);
    private final Scanner scanner;

    public ConsoleInputReader() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        LOGGER.info("Console input initialized.");
    }

    @Override
    public String read() {
        LOGGER.debug("Reading input from console.");
        return scanner.nextLine();
    }

    @Override
    public void close() {
        LOGGER.info("Console input closed.");
    }
}
