package backend.academy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor public enum Difficulty {
    EASY(0), MEDIUM(3), HARD(5);

    private final int stepsToSkip;
}
