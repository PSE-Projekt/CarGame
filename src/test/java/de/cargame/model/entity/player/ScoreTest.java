package de.cargame.model.entity.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreTest {

    @Test
    void increaseScore_shouldIncreaseValue_whenPositiveInput() {
        // Arrange
        Score score = new Score();
        double initialScore = score.getValue();
        double increment = 10.5;

        // Act
        score.increaseScore(increment);

        // Assert
        assertEquals(initialScore + increment, score.getValue());
    }

    @Test
    void increaseScore_shouldNotChangeValue_whenInputIsZero() {
        // Arrange
        Score score = new Score();
        double initialScore = score.getValue();

        // Act
        score.increaseScore(0);

        // Assert
        assertEquals(initialScore, score.getValue());
    }

    @Test
    void increaseScore_shouldNotChangeValue_whenNegativeInput() {
        // Arrange
        Score score = new Score();
        double initialScore = score.getValue();

        // Act
        score.increaseScore(-5.0);

        // Assert
        assertEquals(initialScore, score.getValue());
    }

    @Test
    void increaseScore_multipleCallsMaintainCorrectTotal() {
        // Arrange
        Score score = new Score();
        double firstIncrement = 15.0;
        double secondIncrement = 20.0;
        double expectedScore = score.getValue() + firstIncrement + secondIncrement;

        // Act
        score.increaseScore(firstIncrement);
        score.increaseScore(secondIncrement);

        // Assert
        assertEquals(expectedScore, score.getValue());
    }

    @Test
    void increaseScore_shouldWorkAfterReset() {
        // Arrange
        Score score = new Score();
        score.increaseScore(25.0);
        score.reset();
        double postResetIncrement = 30.0;

        // Act
        score.increaseScore(postResetIncrement);

        // Assert
        assertEquals(postResetIncrement, score.getValue());
    }
}