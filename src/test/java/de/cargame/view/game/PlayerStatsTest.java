package de.cargame.view.game;

import de.cargame.model.entity.player.PlayerUpdate;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlayerStatsTest extends ApplicationTest {

    private PlayerStats playerStats;

    @BeforeEach
    void setUp() {
        playerStats = new PlayerStats();
    }

    @Test
    void testInitialState() {
        assertNotNull(playerStats.getChildren(), "PlayerStats UI elements should be initialized");
        assertEquals("Score: 0", ((Text) playerStats.getChildren().get(0)).getText(), "Initial score should be 'Score: 0'");
    }

    @Test
    void testUpdate() {
        PlayerUpdate mockUpdate = new PlayerUpdate("some random id", 100, 2);

        playerStats.update(mockUpdate);

        // Ensure JavaFX tasks finish before checking assertions
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Score: 100", ((Text) playerStats.getChildren().get(0)).getText(), "Score should update correctly");
        assertEquals("2 x", ((Text) ((HBox) playerStats.getChildren().get(1)).getChildren().get(0)).getText(), "Health should update correctly");

    }
}
