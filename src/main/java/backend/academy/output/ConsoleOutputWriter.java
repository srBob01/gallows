package backend.academy.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleOutputWriter implements OutputInterface {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutputWriter.class);

    @Override
    public void print(String output) {
        try {
            System.out.println(output);
            logger.info("Output to console: {}", output);
        } catch (Exception e) {
            logger.error("Error printing to console", e);
            throw new RuntimeException("Console output error", e);
        }
    }
}
