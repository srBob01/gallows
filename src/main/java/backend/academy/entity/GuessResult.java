package backend.academy.entity;

import lombok.Getter;

@Getter
public class GuessResult {
    private final int MAX_COUNT_ATTEMPTS = 15;
    private final Word word;
    private Character character;
    private Integer remainingAttempts;

    public GuessResult(Word word, int stepToSkip) {
        this.word = word;
        remainingAttempts = MAX_COUNT_ATTEMPTS - stepToSkip;
    }

    public ResultEnter resultEnter(String string) {
        if (string.length() == 1) {
            if (Character.isLetter(string.charAt(0))) {
                this.character = Character.toLowerCase(string.charAt(0));
                if (word.isGuessChar(character)) {
                    return ResultEnter.CharTrue;
                } else {
                    remainingAttempts--;
                    return ResultEnter.CharFalse;
                }
            } else if (string.equals("!")) {
                this.character = null;
                remainingAttempts--;
                return ResultEnter.Hint;
            } else {
                this.character = null;
                remainingAttempts--;
                return ResultEnter.Error;
            }
        } else {
            this.character = null;
            return ResultEnter.Missing;
        }
    }
}
