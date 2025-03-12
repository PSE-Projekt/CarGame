package de.cargame.view.game.sprites;

import de.cargame.view.game.GameObjectView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationTest;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoadMarkSpritesTest extends ApplicationTest {

    private RoadMarkSprites roadMarkSprites;

    @BeforeEach
    void setUp() {
        roadMarkSprites = new RoadMarkSprites();
    }

    @Test
    void testInitialization() {
        assertNotNull(roadMarkSprites.paths, "Paths should be initialized");
        assertNotNull(roadMarkSprites.images, "Images should be initialized");
        assertFalse(roadMarkSprites.images.isEmpty(), "Images list should not be empty after initialization");
    }

    @Test
    void testWeightAssignment() {
        assertEquals(0, roadMarkSprites.weight, "Weight should be set to 1");
    }

    @Test
    void testGetRandomSprite() {
        String gameObjectId = "road_mark";

        GameObjectView sprite = roadMarkSprites.getRandomSprite(gameObjectId);

        assertNotNull(sprite, "Generated sprite should not be null");
        assertEquals(0, sprite.getWeight(), "GameObjectView should have the correct weight");
    }
}
