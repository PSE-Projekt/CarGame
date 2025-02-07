package de.cargame.view.scoreboard;

import de.cargame.view.config.ColorConfig;
import de.cargame.view.config.TextConfig;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Objects;

/**
 * Represents a single score display in the scoreboard.
 */
class ScoreDisplay extends VBox implements Comparable<ScoreDisplay> {
    private final int scoreValue;

    /**
     * Creates a new ScoreDisplay instance.
     *
     * @param score       The score value to be displayed.
     * @param playerIndex The index of the player to be displayed.
     */
    public ScoreDisplay(int score, int playerIndex) {
        this.scoreValue = score;

        configureSelf();

        String label = "Player " + playerIndex + ": ";

        if (playerIndex == 0) {
            label = "";
        }

        Text scoreText = TextConfig.generateTitle(label + scoreValue + "pts");
        scoreText.setFill(ColorConfig.SECONDARY_MAIN);

        getChildren().add(scoreText);
    }

    private void configureSelf() {
        setMaxSize(350, 70);
        setMinSize(350, 70);
        setPrefSize(350, 70);

        setAlignment(Pos.CENTER);
        setBorder(new Border(
                new BorderStroke(
                        ColorConfig.PRIMARY_MAIN,
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(5),
                        new BorderWidths(5))
        ));
    }


    @Override
    public int compareTo(ScoreDisplay o) {
        return Integer.compare(o.scoreValue, this.scoreValue);  // Compares the score values of two ScoreDisplays
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ScoreDisplay that)) return false;
        return scoreValue == that.scoreValue;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(scoreValue);
    }
}
