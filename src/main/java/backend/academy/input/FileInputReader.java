package backend.academy.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileInputReader implements InputInterface {
    private static final Logger logger = LoggerFactory.getLogger(FileInputReader.class);
    private final BufferedReader reader;

    public FileInputReader(String fileName) {
        try {
            this.reader = new BufferedReader(new FileReader(fileName));
            logger.info("FileInputReader initialized for file: {}", fileName);
        } catch (IOException e) {
            logger.error("Error initializing FileInputReader for file: {}", fileName, e);
            throw new RuntimeException("Error initializing FileInputReader: Unable to open file " + fileName, e);
        }
    }

    @Override
    public String read() {
        try {
            logger.debug("Reading line from file.");
            return reader.readLine();
        } catch (IOException e) {
            logger.error("Error reading from file", e);
            throw new RuntimeException("Error reading from file", e);
        }
    }

    @Override
    public void close() {
        try {
            reader.close();
            logger.info("FileInputReader closed successfully.");
        } catch (IOException e) {
            logger.error("Error closing the file", e);
            throw new RuntimeException("Error closing the file", e);
        }
    }
}
