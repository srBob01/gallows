package backend.academy.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class ConsoleInputReader implements InputInterface {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleInputReader.class);
    private final Scanner scanner;

    public ConsoleInputReader() {
        this.scanner = new Scanner(System.in);
        logger.info("Console input initialized.");
    }

    @Override
    public String read() {
        logger.debug("Reading input from console.");
        return scanner.nextLine();
    }

    @Override
    public void close() {
        logger.info("Console input closed.");
    }
}
