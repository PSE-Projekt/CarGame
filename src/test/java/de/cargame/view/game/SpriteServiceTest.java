package de.cargame.view.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SpriteServiceTest extends ApplicationTest {

    private SpriteService spriteService;

    @Start
    public void start() {
        spriteService = new SpriteService();
    }

    @BeforeEach
    void setUp() {
        spriteService = new SpriteService();
    }

    @Test
    void testGetRandomLifeSprite() {
        interact(() -> {
            GameObjectView sprite = spriteService.getRandomLifeSprite("life_bonus");
            assertNotNull(sprite, "Life sprite should not be null");
        });
    }

    @Test
    void testGetRandomObstacleSprite() {
        interact(() -> {
            GameObjectView sprite = spriteService.getRandomObstacleSprite("obstacle");
            assertNotNull(sprite, "Obstacle sprite should not be null");
        });
    }

    @Test
    void testGetRandomKamikazeSprite() {
        interact(() -> {
            GameObjectView sprite = spriteService.getRandomKamikazeSprite("kamikaze");
            assertNotNull(sprite, "Kamikaze sprite should not be null");
        });
    }

    @Test
    void testGetRandomRoadMarkSprite() {
        interact(() -> {
            GameObjectView sprite = spriteService.getRandomRoadMarkSprite("roadmark");
            assertNotNull(sprite, "Road mark sprite should not be null");
        });
    }

    @Test
    void testGetRandomAgileCarSprite() {
        interact(() -> {
            GameObjectView sprite = spriteService.getRandomAgileCarSprite("agile_car");
            assertNotNull(sprite, "Agile car sprite should not be null");
        });
    }

    @Test
    void testGetRandomFastCarSprite() {
        interact(() -> {
            GameObjectView sprite = spriteService.getRandomFastCarSprite("fast_car");
            assertNotNull(sprite, "Fast car sprite should not be null");
        });
    }

    @Test
    void testGetRandomBuildingSprite() {
        interact(() -> {
            GameObjectView sprite = spriteService.getRandomBuildingSprite("building");
            assertNotNull(sprite, "Building sprite should not be null");
        });
    }
}