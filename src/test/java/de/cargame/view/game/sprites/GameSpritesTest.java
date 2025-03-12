package de.cargame.view.game.sprites;

import de.cargame.view.game.GameObjectView;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameSpritesTest extends ApplicationTest {

    private TestGameSprites testGameSprites;

    @Start
    public void start() {
        // Ensure JavaFX Application Thread is initialized
        testGameSprites = new TestGameSprites();
    }

    @BeforeEach
    void setUp() {
        testGameSprites = new TestGameSprites();
    }

    @Test
    void testInitialization() {
        assertNotNull(testGameSprites.paths, "Paths should be initialized");
        assertNotNull(testGameSprites.images, "Images should be initialized");
        assertFalse(testGameSprites.images.isEmpty(), "Images list should not be empty after initialization");
    }

    @Test
    void testWeightAssignment() {
        assertEquals(5, testGameSprites.weight, "Weight should be set to 5");
    }

    @Test
    void testGetRandomSprite() {
        String gameObjectId = "test_object";

        // Ensure JavaFX operations are executed on the JavaFX thread
        GameObjectView sprite = testGameSprites.getRandomSprite(gameObjectId);

        assertNotNull(sprite, "Generated sprite should not be null");
        assertEquals(5, sprite.getWeight(), "GameObjectView should have the correct weight");
    }

    // Concrete subclass for testing
    static class TestGameSprites extends GameSprites {
        @Override
        protected void setWeight() {
            this.weight = 5;
        }

        @Override
        protected void setPaths() {
            this.paths.add("/sprites/missing.png");
            this.paths.add("/sprites/missing.png");
        }
    }
}
