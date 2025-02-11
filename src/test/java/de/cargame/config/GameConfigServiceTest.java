package de.cargame.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigServiceTest {


    private GameConfigService gameConfigService = GameConfigService.getInstance();


    @Test
    void testConfigLoadAllKeys() {
        GameConfig config = new GameConfig();
        for (ConfigKey key : ConfigKey.values()) {
            assertTrue(config.getValueKey(key).isPresent(), "Missing configuration key: " + key);
        }
    }


    @Test
    void setGameConfigServiceIsNotNull_TEST() {
        assertNotNull(gameConfigService);
    }

    @Test
    void loadIntegerValueGetsCorrectlyLoadedTest() {
        int gameSpeed = gameConfigService.loadInteger(ConfigKey.SCREEN_WIDTH);
        assertEquals(1366, gameSpeed);
    }

    @Test
    void loadDoubleValueGetsCorrectlyLoadedTest() {
        double aiCarSpeed = gameConfigService.loadDouble(ConfigKey.AI_CAR_SPEED);
        assertEquals(1.3, aiCarSpeed);
    }

    @Test
    void loadIntegerValueGetsCorrectlyLoadedNewSingletonTest() {
        GameConfigService gameConfigServiceNew = GameConfigService.getInstance();
        int gameSpeed = gameConfigServiceNew.loadInteger(ConfigKey.SCREEN_WIDTH);
        assertEquals(1366, gameSpeed);

    }


    @Test
    void singletonWorksTest() {
        GameConfigService gameConfigService2 = GameConfigService.getInstance();
        GameConfigService gameConfigService3 = GameConfigService.getInstance();
        GameConfigService gameConfigService4 = GameConfigService.getInstance();
        GameConfigService gameConfigService5 = GameConfigService.getInstance();
        GameConfigService gameConfigService6 = GameConfigService.getInstance();


        assertEquals(gameConfigService, gameConfigService2);
        assertEquals(gameConfigService2, gameConfigService3);
        assertEquals(gameConfigService3, gameConfigService4);
        assertEquals(gameConfigService4, gameConfigService5);
        assertEquals(gameConfigService5, gameConfigService6);
    }


}
