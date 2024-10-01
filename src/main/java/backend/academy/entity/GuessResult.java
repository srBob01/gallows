package backend.academy.entity;

import lombok.Getter;

@Getter
public class GuessResult {
    protected static final int MAX_COUNT_ATTEMPTS = 15;
    private final Word word;
    private Character character;
    private Integer remainingAttempts;

    public GuessResult(Word word, int stepToSkip) {
        this.word = word;
        remainingAttempts = MAX_COUNT_ATTEMPTS - stepToSkip;
    }

    public ResultEnter resultEnter(String string) {
        ResultEnter result;

        if (string.length() == 1) {
            char inputChar = string.charAt(0);
            if (Character.isLetter(inputChar)) {
                this.character = Character.toLowerCase(inputChar);
                if (word.isGuessChar(character)) {
                    result = ResultEnter.CharTrue;
                } else {
                    remainingAttempts--;
                    result = ResultEnter.CharFalse;
                }
            } else if (inputChar == '!') {
                this.character = null;
                remainingAttempts--;
                result = ResultEnter.Hint;
            } else {
                this.character = null;
                remainingAttempts--;
                result = ResultEnter.Error;
            }
        } else {
            this.character = null;
            result = ResultEnter.Missing;
        }

        return result;
    }
}
