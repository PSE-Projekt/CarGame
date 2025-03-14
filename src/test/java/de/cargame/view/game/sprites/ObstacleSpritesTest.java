package de.cargame.view.game.sprites;

import de.cargame.view.game.GameObjectView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ObstacleSpritesTest extends ApplicationTest {

    private ObstacleSprites obstacleSprites;

    @BeforeEach
    void setUp() {
        obstacleSprites = new ObstacleSprites();
    }

    @Test
    void testInitialization() {
        assertNotNull(obstacleSprites.paths, "Paths should be initialized");
        assertNotNull(obstacleSprites.images, "Images should be initialized");
        assertFalse(obstacleSprites.images.isEmpty(), "Images list should not be empty after initialization");
    }

    @Test
    void testWeightAssignment() {
        assertEquals(1, obstacleSprites.weight, "Weight should be set to 1");
    }

    @Test
    void testGetRandomSprite() {
        String gameObjectId = "agile_car";

        GameObjectView sprite = obstacleSprites.getRandomSprite(gameObjectId);

        assertNotNull(sprite, "Generated sprite should not be null");
        assertEquals(1, sprite.getWeight(), "GameObjectView should have the correct weight");
    }
}
