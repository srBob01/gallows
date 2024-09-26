package backend.academy.wordloader;

import backend.academy.entity.Word;
import java.util.List;

public interface WordLoaderInterface {
    List<Word> loadWords();
}
