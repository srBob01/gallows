package backend.academy.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor public class FileInputReader implements InputInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileInputReader.class);
    private BufferedReader reader;

    public void initializeReader(String fileName) {
        try {
            reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
            LOGGER.info("FileInputReader initialized for file: {}", fileName);
        } catch (IOException e) {
            LOGGER.error("Error initializing FileInputReader for file {}", fileName, e);
            throw new RuntimeException("Error initializing FileInputReader for file: " + fileName, e);
        }
    }

    @Override public String read() {
        try {
            LOGGER.debug("Reading line from file.");
            return reader.readLine();
        } catch (IOException e) {
            final String error = "Error reading from file";
            LOGGER.error(error, e);
            throw new RuntimeException(error, e);
        }
    }

    @Override public void close() {
        try {
            reader.close();
            LOGGER.info("FileInputReader closed successfully.");
        } catch (IOException e) {
            final String error = "Error closing the file";
            LOGGER.error(error, e);
            throw new RuntimeException(error, e);
        }
    }
}
