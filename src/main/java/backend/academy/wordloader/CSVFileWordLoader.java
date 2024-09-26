package backend.academy.wordloader;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import backend.academy.entity.Word;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVFileWordLoader implements WordLoaderInterface {
    private static final Logger logger = LoggerFactory.getLogger(CSVFileWordLoader.class);
    private final String filePath;

    public CSVFileWordLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Word> loadWords() {
        List<Word> words;
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath))))) {
            words = new ArrayList<>(br.lines()
                .skip(1)
                .map(this::parseWordFromCSV)
                .toList());
            logger.info("Loaded {} words from CSV", words.size());
        } catch (IOException e) {
            logger.error("Error loading words from CSV", e);
            throw new RuntimeException("Error loading words from CSV", e);
        }
        return words;
    }

    private Word parseWordFromCSV(String line) {
        String[] fields = line.split(",");
        Difficulty difficulty = Difficulty.valueOf(fields[0].trim());
        Category category = Category.valueOf(fields[1].trim());
        String wordText = fields[2].trim();
        String hint = fields[3].trim();
        return new Word(difficulty, category, wordText, hint);
    }
}
