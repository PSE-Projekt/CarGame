package de.cargame.view.game;

import de.cargame.model.entity.player.PlayerObserver;
import de.cargame.model.entity.player.PlayerUpdate;
import de.cargame.view.config.ColorConfig;
import de.cargame.view.config.TextConfig;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Objects;

/**
 * Registered by PlayerObservable. Contains UI elements for the player’s game values.
 */
class PlayerStats extends HBox implements PlayerObserver {
    private static final String HEALTH_INDICATOR = " x";
    private static final String SCORE_INDICATOR = "Score: ";
    private final HBox healthContainer;
    private final Text score;
    private final Text health;

    /**
     * Creates a new PlayerStats object. Initializes the stats that will be shown in the game.
     */
    PlayerStats() {
        this.score = TextConfig.generateStatsText(SCORE_INDICATOR + 0);

        this.healthContainer = new HBox(3);
        healthContainer.setAlignment(Pos.CENTER);
        this.health = TextConfig.generateStatsText(3 + HEALTH_INDICATOR);
        this.setSpacing(20);

        ImageView heart;

        try {
            heart = new ImageView(
                    new Image(
                            Objects.requireNonNull(getClass().getResource("/frontend/heart.png")).toExternalForm()
                    )
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Image not found");
        }

        healthContainer.getChildren().addAll(health, heart);
        heart.setFitHeight(27);
        heart.setPreserveRatio(true);

        this.getChildren().addAll(score, healthContainer);
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
        health.setText(newHealth + HEALTH_INDICATOR);
    }
}
