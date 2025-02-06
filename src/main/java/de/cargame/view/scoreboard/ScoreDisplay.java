package de.cargame.view.scoreboard;

import de.cargame.view.config.ColorConfig;
import de.cargame.view.config.TextConfig;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Objects;

class ScoreDisplay extends VBox implements Comparable<ScoreDisplay> {
    private final int scoreValue;

    ScoreDisplay(int score, int playerIndex) {
        this.scoreValue = score;

        configureSelf();

        String label = "Player " + playerIndex + ": ";

        if (playerIndex == 0) {
            label = "";
        }

        Text scoreText = TextConfig.makeH1(label + scoreValue + "pts");
        scoreText.setFill(ColorConfig.SECONDARY_MAIN);

        getChildren().add(scoreText);
    }

    private void configureSelf() {
        setMaxSize(350, 75);
        setMinSize(350, 75);
        setPrefSize(350, 75);

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
