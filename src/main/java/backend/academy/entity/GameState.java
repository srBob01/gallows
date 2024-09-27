package backend.academy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor public enum GameState {
    InProgress("Game in Progress"), Win("You are win!"), Lose("You are lose");
    private final String info;
}
