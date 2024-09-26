package backend.academy.wordrepository;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import backend.academy.entity.Word;

public interface WordRepositoryInterface {
    Word getRandomWord(Category category, Difficulty difficulty);
}
