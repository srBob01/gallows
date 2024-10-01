package backend.academy.wordloader;

import backend.academy.entity.Word;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArrayWordLoader implements WordLoaderInterface {
    private final List<Word> words;

    @Override
    public List<Word> loadWords() {
        return words;
    }
}
