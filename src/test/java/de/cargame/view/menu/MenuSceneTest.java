package de.cargame.view.menu;

import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.InputReceiver;
import de.cargame.view.navigation.Selectable;
import de.cargame.view.navigation.Direction;
import de.cargame.controller.api.PlayerAPI;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class MenuSceneTest {

    private MenuScene menuScene;
    private ApiHandler mockApiHandler;
    private InputReceiver mockInputReceiverKeyboard;
    private InputReceiver mockInputReceiverGamePad;
    private PlayerAPI mockPlayerApi;

    @Start
    private void start(Stage stage) {
        mockApiHandler = mock(ApiHandler.class);
        mockInputReceiverKeyboard = mock(InputReceiver.class);
        mockInputReceiverGamePad = mock(InputReceiver.class);
        mockPlayerApi = mock(PlayerAPI.class);

        when(mockApiHandler.getPlayerApi()).thenReturn(mockPlayerApi);
        when(mockApiHandler.getInputReceiverKeyboard()).thenReturn(mockInputReceiverKeyboard);
        when(mockApiHandler.getInputReceiverGamePad()).thenReturn(mockInputReceiverGamePad);

        menuScene = new MenuScene(mockApiHandler);

        // Attach scene to stage for TestFX compatibility
        stage.setScene(menuScene);
        stage.show();
    }

    @Test
    public void testUIElementsAreInitialized() {
        VBox root = (VBox) menuScene.getRoot();
        assertNotNull(root, "Root should not be null");

        VBox sceneContent = (VBox) root.getChildren().get(0);
        assertNotNull(sceneContent, "Scene content should not be null");

        assertEquals(3, sceneContent.getChildren().size(), "Scene content should have 3 sections");

        assertInstanceOf(Text.class, ((StackPane) sceneContent.getChildren().get(0)).getChildren().get(0), "Title should be a Text element");
        assertInstanceOf(HBox.class, sceneContent.getChildren().get(2), "Button container should be HBox");
    }

    @Test
    public void testButtonsAreConfiguredCorrectly() {
        Selectable singlePlayerButton = menuScene.getSinglePlayerButton();
        Selectable multiPlayerButton = menuScene.getMultiPlayerButton();

        assertNotNull(singlePlayerButton, "Single Player Button should be initialized");
        assertNotNull(multiPlayerButton, "Multiplayer Button should be initialized");

        assertEquals(multiPlayerButton, singlePlayerButton.getNeighbour(Direction.RIGHT), "Single Player Button should have Multiplayer Button on the RIGHT");
        assertEquals(singlePlayerButton, multiPlayerButton.getNeighbour(Direction.LEFT), "Multiplayer Button should have Single Player Button on the LEFT");
    }

    @Test
    public void testSetup_CorrectlyAssignsNavigators() {
        when(mockPlayerApi.getGamepadPlayerId()).thenReturn("gamepadPlayer");
        when(mockPlayerApi.getKeyboardPlayerId()).thenReturn("keyboardPlayer");

        Platform.runLater(() -> menuScene.setup());
        WaitForAsyncUtils.waitForFxEvents(); // Ensure JavaFX execution completes

        // times 2 because of constructor call
        verify(mockInputReceiverKeyboard, times(2)).clear();
        verify(mockInputReceiverGamePad, times(2)).clear();

        verify(mockInputReceiverKeyboard).assignNavigator(eq("keyboardPlayer"), any(Navigator.class));
        verify(mockInputReceiverGamePad).assignNavigator(eq("gamepadPlayer"), any(Navigator.class));
    }
}
