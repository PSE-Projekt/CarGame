package de.cargame.view.config;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TextConfig {
    public static final Font APPLICATION_FONT =
            Font.loadFont(TextConfig.class.getResourceAsStream("/frontend/monomaniacOne.ttf"), 50);

    public static Text makeH1(String text) {
        Text h1 = new Text(text);
        h1.setFont(APPLICATION_FONT);
        h1.setStyle("-fx-font-size: 50px;");
        h1.translateYProperty().set(-4);
        return h1;
    }

    public static Text generateStatsText(String text) {
        Text scoreText = new Text(text);
        scoreText.setFont(APPLICATION_FONT);
        scoreText.setStyle("-fx-font-size: 20px;");
        scoreText.translateYProperty().set(-4);
        scoreText.setFill(ColorConfig.SECONDARY_MAIN);
        return scoreText;
    }
}
