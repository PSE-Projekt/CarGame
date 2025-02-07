package de.cargame.view.game;

import de.cargame.model.entity.player.PlayerObserver;
import de.cargame.model.entity.player.PlayerUpdate;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
/**
 * Registered by PlayerObservable. Contains UI elements for the player’s game values.
 */
class PlayerStats extends HBox implements PlayerObserver {
    private static final String HEALTH_INDICATOR = "Health: ";
    private static final String SCORE_INDICATOR = "Score: ";
    private final Text score;
    private final Text health;

    /**
     * Creates a new PlayerStats object. Initializes the stats that will be shown in the game.
     */
    PlayerStats() {
        this.score = new Text(SCORE_INDICATOR + 0);
        this.health = new Text(HEALTH_INDICATOR + 3);
        this.setSpacing(10);
        getChildren().addAll(score, health);
    }
    /**
     * Method will be invoked by the observable. Upon a change, the UI elements will
     * contain the refreshed new Player values.
     */
    @Override
    public void update(PlayerUpdate playerUpdate) {
        int newScore = playerUpdate.getScoreValue();
        int newHealth = playerUpdate.getLives();

        score.setText(SCORE_INDICATOR + newScore);
        health.setText(HEALTH_INDICATOR + newHealth);
    }
}
