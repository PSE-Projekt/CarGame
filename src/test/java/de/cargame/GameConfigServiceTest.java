package de.cargame;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameConfigServiceTest {



    private GameConfigService gameConfigService = GameConfigService.getInstance();



    @Test
    void setGameConfigServiceIsNotNull_TEST(){
        assertNotNull(gameConfigService);
    }

    @Test
    void loadIntegerValueGetsCorrectlyLoaded(){
        int gameSpeed = gameConfigService.loadInteger(ConfigKey.SCREEN_WIDTH);
        assertEquals(1280, gameSpeed);
    }

    @Test
    void loadDoubleValueGetsCorrectlyLoaded(){
        double aiCarSpeed = gameConfigService.loadDouble(ConfigKey.AI_CAR_SPEED);
        assertEquals(1.5, aiCarSpeed);
    }

    @Test
    void loadIntegerValueGetsCorrectlyLoadedNewSingleton(){
        GameConfigService gameConfigServiceNew = GameConfigService.getInstance();
        int gameSpeed = gameConfigServiceNew.loadInteger(ConfigKey.SCREEN_WIDTH);
        assertEquals(1280, gameSpeed);

    }


    @Test
    void singletonWorksTest(){
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
