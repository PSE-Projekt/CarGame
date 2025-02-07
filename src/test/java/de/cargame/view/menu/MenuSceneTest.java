package de.cargame.view.menu;

import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameMode;
import de.cargame.view.ApiHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuSceneTest {

    private GameConfigService gameConfigService = GameConfigService.getInstance();
    private ApiHandler apiHandler;

    @BeforeEach
    public void setUp() {
        apiHandler = Mockito.mock(ApiHandler.class);

        // Mock other methods of ApiHandler as needed

    }


    @Test
    public void testSinglePlayerTransition(){
        MenuScene menuScene = new MenuScene(this.apiHandler);
        menuScene.setup();
    }
}