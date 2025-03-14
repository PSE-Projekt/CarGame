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
    void increaseScore_withSmallestPositiveDoubleValue() {
        // Arrange
        Score score = new Score();
        double initialScore = score.getValue();
        double increment = Double.MIN_VALUE;

        // Act
        score.increaseScore(increment);

        // Assert
        assertEquals(initialScore + increment, score.getValue());
    }

    @Test
    void increaseScore_withLargestDoubleValue() {
        // Arrange
        Score score = new Score();
        double initialScore = score.getValue();
        double increment = Double.MAX_VALUE;

        // Act
        score.increaseScore(increment);

        // Assert
        assertEquals(initialScore + increment, score.getValue());
    }

    @Test
    void increaseScore_maintainsPrecisionAfterMultipleUpdates() {
        // Arrange
        Score score = new Score();
        score.increaseScore(0.1);
        score.increaseScore(0.2);

        // Assert
        assertEquals(0.3, score.getValue(), 1e-10); // Precision check
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

    @Test
    void reset_shouldSetValueToDefaultWhenScoreIsZero() {
        // Arrange
        Score score = new Score();
        double expectedDefault = 0;

        // Act
        score.reset();

        // Assert
        assertEquals(expectedDefault, score.getValue());
    }

    @Test
    void reset_shouldSetValueToDefaultWhenScoreHasValue() {
        // Arrange
        Score score = new Score();
        score.increaseScore(15.0);
        double expectedDefault = 0;

        // Act
        score.reset();

        // Assert
        assertEquals(expectedDefault, score.getValue());
    }

    @Test
    void reset_shouldMaintainDefaultValueAfterMultipleCalls() {
        // Arrange
        Score score = new Score();
        score.increaseScore(20.0);
        score.reset();
        score.reset(); // Call reset multiple times
        double expectedDefault = 0;

        // Assert
        assertEquals(expectedDefault, score.getValue());
    }
}