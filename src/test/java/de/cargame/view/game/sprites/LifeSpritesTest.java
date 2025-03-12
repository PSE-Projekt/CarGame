package de.cargame.view.game.sprites;

import de.cargame.view.game.GameObjectView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationTest;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LifeSpritesTest extends ApplicationTest {

    private LifeSprites lifeSprites;

    @BeforeEach
    void setUp() {
        lifeSprites = new LifeSprites();
    }

    @Test
    void testInitialization() {
        assertNotNull(lifeSprites.paths, "Paths should be initialized");
        assertNotNull(lifeSprites.images, "Images should be initialized");
        assertFalse(lifeSprites.images.isEmpty(), "Images list should not be empty after initialization");
    }

    @Test
    void testWeightAssignment() {
        assertEquals(1, lifeSprites.weight, "Weight should be set to 1");
    }

    @Test
    void testGetRandomSprite() {
        String gameObjectId = "life_bonus";

        GameObjectView sprite = lifeSprites.getRandomSprite(gameObjectId);

        assertNotNull(sprite, "Generated sprite should not be null");
        assertEquals(1, sprite.getWeight(), "GameObjectView should have the correct weight");
    }
}
