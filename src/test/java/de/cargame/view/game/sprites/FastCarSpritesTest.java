package de.cargame.view.game.sprites;

import de.cargame.view.game.GameObjectView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FastCarSpritesTest extends ApplicationTest {

    private FastCarSprites fastCarSprites;

    @BeforeEach
    void setUp() {
        fastCarSprites = new FastCarSprites();
    }

    @Test
    void testInitialization() {
        assertNotNull(fastCarSprites.paths, "Paths should be initialized");
        assertNotNull(fastCarSprites.images, "Images should be initialized");
        assertFalse(fastCarSprites.images.isEmpty(), "Images list should not be empty after initialization");
    }

    @Test
    void testWeightAssignment() {
        assertEquals(3, fastCarSprites.weight, "Weight should be set to 1");
    }

    @Test
    void testGetRandomSprite() {
        String gameObjectId = "agile_car";

        GameObjectView sprite = fastCarSprites.getRandomSprite(gameObjectId);

        assertNotNull(sprite, "Generated sprite should not be null");
        assertEquals(3, sprite.getWeight(), "GameObjectView should have the correct weight");
    }
}
