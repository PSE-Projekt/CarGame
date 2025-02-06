package de.cargame.view.game;

import de.cargame.model.entity.player.PlayerObserver;
import de.cargame.model.entity.player.PlayerUpdate;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PlayerStats extends HBox implements PlayerObserver {
    private static final String HEALTH_INDICATOR = "Health: ";
    private static final String SCORE_INDICATOR = "Score: ";
    private Text score;
    private Text health;


    public PlayerStats() {
        this.score = new Text(SCORE_INDICATOR + 0);
        this.health = new Text(HEALTH_INDICATOR + 3);
        getChildren().addAll(score, health);
    }

    @Override
    public void update(PlayerUpdate playerUpdate) {
        int newScore = playerUpdate.getScoreValue();
        int newHealth = playerUpdate.getLives();

        score.setText(SCORE_INDICATOR + newScore);
        health.setText(HEALTH_INDICATOR + newHealth);
    }
}
