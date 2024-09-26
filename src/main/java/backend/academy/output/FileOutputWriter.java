package backend.academy.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutputWriter implements OutputInterface {
    private static final Logger logger = LoggerFactory.getLogger(FileOutputWriter.class);
    private final FileWriter writer;

    public FileOutputWriter(String fileName) throws IOException {
        try {
            this.writer = new FileWriter(fileName, true);
        } catch (IOException e) {
            logger.error("Error initializing FileWriter for file: {}", fileName, e);
            throw new RuntimeException("Error initializing FileWriter for file: " + fileName, e);
        }
    }

    @Override
    public void print(String output) {
        try {
            writer.write(output + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            logger.error("Error writing to file", e);
            throw new RuntimeException("Error writing to file", e);
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            logger.error("Error closing the file", e);
            throw new RuntimeException("Error closing the file", e);
        }
    }
}
