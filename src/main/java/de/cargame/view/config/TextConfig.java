package de.cargame.view.config;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.NoArgsConstructor;

/**
 * This class provides the configuration for the text elements in the application.
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TextConfig {
    /**
     * The application font.
     */
    public static final Font APPLICATION_FONT =
            Font.loadFont(TextConfig.class.getResourceAsStream("/frontend/monomaniacOne.ttf"), 50);

    /**
     * Creates a new Text instance with the given text and the application font.
     *
     * @param text The text to be displayed.
     * @return The created Text instance.
     */
    public static Text generateTitle(String text) {
        Text h1 = new Text(text);
        h1.setFont(APPLICATION_FONT);
        h1.setStyle("-fx-font-size: 50px;");
        h1.translateYProperty().set(-5);
        return h1;
    }

    /**
     * Creates a new Text instance with the given text and the application font.
     *
     * @param text The text to be displayed.
     * @return The created Text instance.
     */
    public static Text generateStatsText(String text) {
        Text scoreText = new Text(text);
        scoreText.setFont(APPLICATION_FONT);
        scoreText.setStyle("-fx-font-size: 20px;");
        scoreText.translateYProperty().set(-5);
        scoreText.setFill(ColorConfig.SECONDARY_MAIN);
        return scoreText;
    }
}
