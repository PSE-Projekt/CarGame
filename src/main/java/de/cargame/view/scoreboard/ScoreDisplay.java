package de.cargame.view.scoreboard;

import de.cargame.view.config.ColorConfig;
import de.cargame.view.config.TextConfig;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Objects;

public class ScoreDisplay extends VBox implements Comparable<ScoreDisplay> {
    private final int scoreValue;

    public ScoreDisplay(int score, int playerIndex) {
        this.scoreValue = score;

        configureSelf();

        String label = "Player " + playerIndex + ": ";

        if (playerIndex == 0) {
            label = "";
        }

        Text scoreText = TextConfig.makeH1(label + scoreValue);
        scoreText.setFill(ColorConfig.SECONDARY_MAIN);

        getChildren().add(scoreText);
    }

    private void configureSelf() {
        setMaxSize(300, 50);
        setMinSize(300, 50);
        setPrefSize(300, 50);
        setAlignment(Pos.CENTER);
        setBorder(new Border(new BorderStroke(ColorConfig.PRIMARY_MAIN, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2))));
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
