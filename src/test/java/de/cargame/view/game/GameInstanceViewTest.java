package de.cargame.view.game;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.gameobject.Coordinate;
import de.cargame.model.entity.gameobject.Dimension;
import de.cargame.model.entity.gameobject.GameObjectBoundType;
import de.cargame.model.entity.gameobject.Obstacle;
import de.cargame.view.ApiHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameInstanceViewTest extends ApplicationTest {

    @Mock
    private ApiHandler apiHandler;
    @Mock
    private PlayerAPI playerAPI;
    @Mock
    private GameInstanceAPI gameInstanceAPI;
    @Mock
    private GameStateAPI gameStateAPI;
    @Mock
    private GameModelData gameModelData;

    private GameInstanceView gameInstanceView;

    @BeforeEach
    void setUp() {
        when(apiHandler.getGameInstanceApi()).thenReturn(gameInstanceAPI);
        when(apiHandler.getGameStateApi()).thenReturn(gameStateAPI);
        when(gameStateAPI.getGameMode()).thenReturn(GameMode.SINGLEPLAYER);
        when(apiHandler.getPlayerApi()).thenReturn(playerAPI);
        when(gameInstanceAPI.getModel()).thenReturn(List.of(gameModelData));
        when(gameModelData.getPlayerId()).thenReturn("player1");

        gameInstanceView = new GameInstanceView(apiHandler, "player1");
    }

    @Test
    void testInitialization() {
        assertNotNull(gameInstanceView, "GameInstanceView should be initialized");
        assertFalse(gameInstanceView.getChildren().isEmpty(), "UI elements should be present");
    }

    @Test
    void testRenderWithEmptyObjects() {
        gameInstanceView.render();
        assertFalse(gameInstanceView.getChildren().isEmpty(), "UI elements should still be present even if no game objects exist");
    }

    @Test
    void testRenderWithGameObjects() {
        Obstacle mockGameObject =
                new Obstacle(new Coordinate(0, 0), new Dimension(0, 0), GameObjectBoundType.RECTANGLE, GameMode.SINGLEPLAYER);
        when(gameModelData.getGameObjects()).thenReturn(List.of(mockGameObject));

        gameInstanceView.render();
        assertTrue(gameInstanceView.getChildren().size() > 1, "Game objects should be rendered");
    }

    @Test
    void testThrowsExceptionForInvalidPlayerId() {
        when(gameInstanceAPI.getModel()).thenReturn(List.of());
        assertThrows(IllegalArgumentException.class, () -> new GameInstanceView(apiHandler, "invalid_player"), "Should throw exception for invalid player ID");
    }
}