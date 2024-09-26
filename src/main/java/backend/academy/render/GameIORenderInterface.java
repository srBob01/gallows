package backend.academy.render;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import java.util.List;

public interface GameIORenderInterface {
    Category selectCategory();

    Difficulty selectDifficulty();

    String selectNextCharacter();

    void print(String string);

    void printInfo(List<Character> word, int remainingAttempts);
}
