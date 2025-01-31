package de.cargame.view.game;

import de.cargame.model.entity.player.PlayerObserver;
import de.cargame.model.entity.player.PlayerUpdate;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PlayerStats extends Pane implements PlayerObserver {
    private Text score;
    private Text health;

    public PlayerStats(){
        this.score = new Text("Score: 0");
        this.health = new Text("Health: 3");
        getChildren().addAll(score, health);
    }

    @Override
    public void update(PlayerUpdate playerUpdate) {
        int newScore = playerUpdate.getScoreValue();
        int newHealth = playerUpdate.getLives();

        score.setText("Score: " + newScore);
        health.setText("Health: " + newHealth);
    }
}
