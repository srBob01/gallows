package backend.academy.output;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor public class FileOutputWriter implements OutputInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileOutputWriter.class);
    private FileWriter writer;

    public void initializeWriter(String fileName) {
        try {
            this.writer = new FileWriter(fileName, StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            LOGGER.error("Error initializing FileWriter for file {}", fileName, e);
            throw new RuntimeException("Error initializing FileWriter for file: " + fileName, e);
        }
    }

    @Override public void print(String output) {
        try {
            writer.write(output + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            final String error = "Error writing to file";
            LOGGER.error(error, e);
            throw new RuntimeException(error, e);
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            final String error = "Error closing the file";
            LOGGER.error(error, e);
            throw new RuntimeException(error, e);
        }
    }
}
