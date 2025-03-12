package de.cargame.view.game.sprites;

import de.cargame.view.game.GameObjectView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationTest;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BuildingSpritesTest extends ApplicationTest {

    private BuildingSprites buildingSprites;

    @BeforeEach
    void setUp() {
        buildingSprites = new BuildingSprites();
    }

    @Test
    void testInitialization() {
        assertNotNull(buildingSprites.paths, "Paths should be initialized");
        assertNotNull(buildingSprites.images, "Images should be initialized");
        assertFalse(buildingSprites.images.isEmpty(), "Images list should not be empty after initialization");
    }

    @Test
    void testWeightAssignment() {
        assertEquals(1, buildingSprites.weight, "Weight should be set to 1");
    }

    @Test
    void testGetRandomSprite() {
        String gameObjectId = "agile_car";

        GameObjectView sprite = buildingSprites.getRandomSprite(gameObjectId);

        assertNotNull(sprite, "Generated sprite should not be null");
        assertEquals(1, sprite.getWeight(), "GameObjectView should have the correct weight");
    }
}
