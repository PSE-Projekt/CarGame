package de.cargame.view;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameState;
import de.cargame.view.game.GameScene;
import de.cargame.view.menu.MenuScene;
import de.cargame.view.scoreboard.ScoreBoardScene;
import de.cargame.view.selection.SelectionScene;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class ApplicationViewTest {

    private ApplicationView applicationView;
    private Stage mockStage;
    private ApiHandler apiHandler;
    private GameInstanceAPI gameInstanceApi;
    private GameStateAPI gameStateApi;
    private PlayerAPI playerApi;
    private MenuScene mockedMenuScene;
    private SelectionScene mockedSelectionScene;
    private GameScene mockedGameScene;

    @BeforeEach
    void setUp() {
        // Mock the Stage to prevent JavaFX issues
        mockStage = mock(Stage.class);

        // Mock dependencies
        gameInstanceApi = mock(GameInstanceAPI.class);
        gameStateApi = mock(GameStateAPI.class);
        playerApi = mock(PlayerAPI.class);
        apiHandler = mock(ApiHandler.class);

        when(apiHandler.getGameInstanceApi()).thenReturn(gameInstanceApi);
        when(apiHandler.getGameStateApi()).thenReturn(gameStateApi);
        when(apiHandler.getPlayerApi()).thenReturn(playerApi);

        // Mock scene constructions and disable setup methods
        try (
                MockedConstruction<MenuScene> ignored = Mockito.mockConstruction(MenuScene.class,
                        (mock, context) -> {
                            doNothing().when(mock).setup();
                            mockedMenuScene = mock;
                        });
                MockedConstruction<SelectionScene> ignored1 = Mockito.mockConstruction(SelectionScene.class,
                        (mock, context) -> {
                            doNothing().when(mock).setup();
                            mockedSelectionScene = mock;
                        });
                MockedConstruction<ScoreBoardScene> ignored2 = Mockito.mockConstruction(ScoreBoardScene.class,
                        (mock, context) -> {
                            doNothing().when(mock).setup();
                        });
                MockedConstruction<GameScene> ignored3 = Mockito.mockConstruction(GameScene.class,
                        (mock, context) -> {
                            doNothing().when(mock).setup();
                            mockedGameScene = mock;
                        })
        ) {
            // Initialize ApplicationView within the try block
            applicationView = new ApplicationView(gameInstanceApi, gameStateApi, playerApi, mockStage);
        }
    }


    // add tests
    @Test
    void testInitialState() {
        assertEquals(GameState.MAIN_MENU, applicationView.getViewGameState(), "Application should start in MAIN_MENU state");
        verify(mockStage).setScene(any(Scene.class));
        assertNotNull(applicationView.getCurrentScene(), "Some Scene should have been instantiated");
        assertEquals(mockedMenuScene, applicationView.getCurrentScene(), "MenuScene should have been instantiated");
    }

    @Test
    void testSceneSwitchingValid() {
        applicationView.switchScene(GameState.CAR_SELECTION);

        // Ensure JavaFX tasks finish before checking assertions
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(GameState.CAR_SELECTION, applicationView.getViewGameState(), "Application should switch to CAR_SELECTION state");
        assertNotNull(applicationView.getCurrentScene(), "Some Scene should have been instantiated");
        assertEquals(mockedSelectionScene, applicationView.getCurrentScene(), "SelectionScene should have been instantiated");
    }

    @Test
    void testSceneSwitchingInvalid() {
        assertThrows(IllegalStateException.class, () -> applicationView.switchScene(GameState.TEST_INVALID), "Switching to an invalid state should throw an exception");
        assertEquals(GameState.MAIN_MENU, applicationView.getViewGameState(), "Application should stay in MAIN_MENU state");
        assertNotNull(mockedMenuScene, "MenuScene should still be active");
    }

    @Test
    void testRenderGame_ScoreBoardState() {
        when(gameStateApi.getGameState()).thenReturn(GameState.SCORE_BOARD);

        applicationView.renderGame();

        // Ensure JavaFX tasks finish before checking assertions
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockStage, times(2)).setScene(any(Scene.class));
        assertEquals(GameState.SCORE_BOARD, applicationView.getViewGameState(), "Application should switch to SCORE_BOARD state");
    }

    @Test
    void testRenderGame_InGameState() {
        when(gameStateApi.getGameState()).thenReturn(GameState.IN_GAME);

        applicationView.switchScene(GameState.IN_GAME);
        applicationView.renderGame();

        // Ensure JavaFX tasks finish before checking assertions
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockStage, times(2)).setScene(any(Scene.class));
        verify(mockedGameScene, times(1)).startRendering();
        assertEquals(GameState.IN_GAME, applicationView.getViewGameState(), "Application should switch to IN_GAME state");
    }

    @Test
    void testRenderGame_InvalidState() {
        when(gameStateApi.getGameState()).thenReturn(GameState.TEST_INVALID);

        assertThrows(IllegalStateException.class, () -> applicationView.renderGame(),
                "Rendering should fail for an invalid game state");
    }
}
