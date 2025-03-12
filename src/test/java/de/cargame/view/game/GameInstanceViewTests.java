package de.cargame.view.game;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.gameobject.*;
import de.cargame.model.entity.gameobject.car.ai.KamikazeCar;
import de.cargame.model.entity.gameobject.car.player.AgileCar;
import de.cargame.model.entity.gameobject.car.player.FastCar;
import de.cargame.view.ApiHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.stage.Stage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class GameInstanceViewTest {

    private GameInstanceView gameInstanceView;
    private ApiHandler apiHandler;
    private GameInstanceAPI gameInstanceApi;
    private GameStateAPI gameStateApi;
    private PlayerAPI playerApi;
    private GameConfigService gameConfigService;
    private GameModelData mockModelData;

    private static final String PLAYER_ID = "player123";

    @Start
    public void start(Stage stage) {
        apiHandler = mock(ApiHandler.class);
        gameInstanceApi = mock(GameInstanceAPI.class);
        gameStateApi = mock(GameStateAPI.class);
        playerApi = mock(PlayerAPI.class);
        gameConfigService = mock(GameConfigService.class);

        when(apiHandler.getGameInstanceApi()).thenReturn(gameInstanceApi);
        when(apiHandler.getGameStateApi()).thenReturn(gameStateApi);
        when(apiHandler.getPlayerApi()).thenReturn(playerApi);
        when(gameInstanceApi.getModel()).thenReturn(List.of(mockModelData));

        // Simuliere die Config-Werte
        when(gameConfigService.loadInteger(ConfigKey.SCREEN_WIDTH)).thenReturn(800);
        when(gameConfigService.loadInteger(ConfigKey.SCREEN_HEIGHT)).thenReturn(600);

        // Simuliertes GameModelData für den Test
        mockModelData = mock(GameModelData.class);
        when(mockModelData.getPlayerId()).thenReturn(PLAYER_ID);
        when(mockModelData.getGameObjects()).thenReturn(List.of());

        gameInstanceView = new GameInstanceView(apiHandler, PLAYER_ID);
        stage.setScene(new javafx.scene.Scene(gameInstanceView));
        stage.show();
    }

    @Test
    void testInitialization() {
        assertNotNull(gameInstanceView);
        assertEquals(800, gameInstanceView.getPrefWidth());
        assertNotNull(gameInstanceView.lookup(".player-stats")); // Prüft, ob `PlayerStats` hinzugefügt wurde
    }

    @Test
    void testRenderAddsObjects() {
        GameObject mockObject = mock(Obstacle.class);
        when(mockObject.getId()).thenReturn("obstacle123");
        when(mockObject.getX()).thenReturn(50.0);
        when(mockObject.getY()).thenReturn(100.0);
        when(mockObject.getWidth()).thenReturn(30);
        when(mockObject.getHeight()).thenReturn(30);

        when(mockModelData.getGameObjects()).thenReturn(List.of(mockObject));

        gameInstanceView.render();

        assertFalse(gameInstanceView.getChildren().isEmpty(), "Children should not be empty after render");
        Node renderedObject = gameInstanceView.getChildren().get(0);
        assertNotNull(renderedObject);
    }

    @Test
    void testRenderThrowsExceptionForUnknownObject() {
        GameObject unknownObject = mock(GameObject.class);
        when(mockModelData.getGameObjects()).thenReturn(List.of(unknownObject));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> gameInstanceView.render());
        assertTrue(exception.getMessage().contains("Unknown game object type"));
    }

    @Test
    void testConfigureGreenArea() {
        List<Pane> greenAreas = gameInstanceView.configureGreenArea();
        assertEquals(5, greenAreas.size(), "There should be 5 UI elements for the green area.");
    }
}
