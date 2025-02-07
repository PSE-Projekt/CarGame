package de.cargame.view.menu;

import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuSceneTest {

    private GameConfigService gameConfigService = GameConfigService.getInstance();

    @Test
    public void testSinglePlayerTransition(){
        //TODO: create ApiHandler, perhaps Inputreceiver,

        MenuScene testedScene = new MenuScene();
        //TODO: simulate Input
        GameMode result = GameMode.SINGLEPLAYER;
        assertEquals(ApiHandler, result.toString(), "False, " + result +" expected but was "+ );

        return true;
    }
}