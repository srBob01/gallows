package backend.academy.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter public class Word {
    private final Difficulty difficulty;
    private final Category category;
    private final String word;
    private final String hint;
    private final Map<Character, List<Integer>> charPositions;

    public Word(Difficulty difficulty, Category category, String word, String hint) {
        this.difficulty = difficulty;
        this.category = category;
        this.word = word;
        this.hint = hint;
        this.charPositions = new HashMap<>();

        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            charPositions.computeIfAbsent(currentChar, ArrayList::new).add(i);
        }
    }

    public void enterCharacter(List<Character> guessedWord, char character) {
        charPositions.get(character).forEach(i -> guessedWord.set(i, character));
    }

    public boolean isGuessChar(char character) {
        return charPositions.containsKey(character);
    }
}
