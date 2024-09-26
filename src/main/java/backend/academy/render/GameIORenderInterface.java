package backend.academy.render;

import backend.academy.entity.Category;
import backend.academy.entity.Difficulty;
import java.util.List;

public interface GameIORenderInterface {
    Category selectCategory() throws Exception;

    Difficulty selectDifficulty() throws Exception;

    String selectNextCharacter() throws Exception;

    void print(String string);

    void printInfo(List<Character> word, int remainingAttempts);
}
