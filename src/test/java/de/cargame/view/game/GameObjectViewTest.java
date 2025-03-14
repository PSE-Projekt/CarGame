package de.cargame.view.game;

import javafx.application.Platform;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameObjectViewTest extends ApplicationTest { // Ensures JavaFX is initialized

    private GameObjectView gameObjectView1;
    private GameObjectView gameObjectView2;
    private GameObjectView gameObjectView3;

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            Image mockImage = new Image("file:src/test/resources/mock-image.png"); // Ensure a valid image path

            gameObjectView1 = new GameObjectView(mockImage, 5, "objectA");
            gameObjectView2 = new GameObjectView(mockImage, 10, "objectB");
            gameObjectView3 = new GameObjectView(mockImage, 5, "objectC"); // Same weight as gameObjectView1 but different ID
        });
    }

    @Test
    void testGameObjectViewProperties() {
        Platform.runLater(() -> {
            assertEquals(5, gameObjectView1.getWeight());
            assertEquals("objectA", gameObjectView1.getGameObjectId());
        });
    }

    @Test
    void testCompareTo_DifferentWeights() {
        Platform.runLater(() -> {
            assertTrue(gameObjectView1.compareTo(gameObjectView2) < 0, "gameObjectView1 should be smaller than gameObjectView2 (weight comparison)");
            assertTrue(gameObjectView2.compareTo(gameObjectView1) > 0, "gameObjectView2 should be greater than gameObjectView1 (weight comparison)");
        });
    }

    @Test
    void testCompareTo_SameWeightDifferentId() {
        Platform.runLater(() -> {
            assertTrue(gameObjectView1.compareTo(gameObjectView3) < 0, "gameObjectView1 should be smaller than gameObjectView3 (ID comparison)");
            assertTrue(gameObjectView3.compareTo(gameObjectView1) > 0, "gameObjectView3 should be greater than gameObjectView1 (ID comparison)");
        });
    }

    @Test
    void testCompareTo_SameWeightSameId() {
        Platform.runLater(() -> {
            GameObjectView identicalGameObject = new GameObjectView(gameObjectView1.getImage(), gameObjectView1.getWeight(), gameObjectView1.getGameObjectId());
            assertEquals(0, gameObjectView1.compareTo(identicalGameObject), "Two identical GameObjectViews should be equal");
        });
    }
}
