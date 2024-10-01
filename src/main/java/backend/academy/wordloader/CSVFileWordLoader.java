package backend.academy.wordloader;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import backend.academy.entity.Word;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class CSVFileWordLoader implements WordLoaderInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVFileWordLoader.class);
    private static final int REQUIRED_FIELDS_COUNT = 4;
    private static final int DIFFICULTY_INDEX = 0;
    private static final int CATEGORY_INDEX = 1;
    private static final int WORD_INDEX = 2;
    private static final int HINT_INDEX = 3;
    private final String fileName;

    @Override
    public List<Word> loadWords() {
        List<Word> words;
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName)),
                StandardCharsets.UTF_8))
        ) {
            words = new ArrayList<>(br.lines().skip(1).map(this::parseWordFromCSV).toList());
            LOGGER.info("Loaded {} words from CSV", words.size());
        } catch (IOException e) {
            final String error = "Error loading words from CSV";
            LOGGER.error(error, e);
            throw new RuntimeException(error, e);
        }
        return words;
    }

    private Word parseWordFromCSV(String line) {
        String[] fields = line.split(",");

        if (fields.length != REQUIRED_FIELDS_COUNT) {
            throw new IllegalArgumentException("Incorrect CSV format: each line must have 4 fields");
        }

        Difficulty difficulty = Difficulty.valueOf(fields[DIFFICULTY_INDEX].trim());
        Category category = Category.valueOf(fields[CATEGORY_INDEX].trim());
        String wordText = fields[WORD_INDEX].trim();
        String hint = fields[HINT_INDEX].trim();

        return new Word(difficulty, category, wordText, hint);
    }
}
