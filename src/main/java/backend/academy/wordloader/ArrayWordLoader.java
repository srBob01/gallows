package backend.academy.wordloader;

import backend.academy.entity.Word;
import java.util.List;

public class ArrayWordLoader implements WordLoaderInterface {
    private final List<Word> words;

    public ArrayWordLoader(List<Word> words) {
        this.words = words;
    }

    @Override
    public List<Word> loadWords() {
        return words;
    }
}
