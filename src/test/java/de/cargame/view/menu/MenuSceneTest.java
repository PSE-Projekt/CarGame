package de.cargame.view.menu;

import static org.junit.jupiter.api.Assertions.*;

import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameMode;
import de.cargame.view.ApiHandler;
import org.junit.jupiter.api.Test;
class MenuSceneTest {

    private GameConfigService gameConfigService = GameConfigService.getInstance();

    @Test
    public boolean testSinglePlayerTransition(){
        //TODO: create ApiHandler, perhaps Inputreceiver,

        MenuScene testedScene = new MenuScene();
        //TODO: simulate Input
        GameMode result = GameMode.SINGLEPLAYER;
        assertEquals(ApiHandler, result.toString(), "False, " + result +" expected but was "+ );

        return true;
    }
}