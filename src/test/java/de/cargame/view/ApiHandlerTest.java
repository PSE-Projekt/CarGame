package de.cargame.view;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameState;
import de.cargame.view.navigation.InputReceiver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ApiHandlerTest {

    private GameInstanceAPI mockGameInstanceApi;
    private GameStateAPI mockGameStateApi;
    private PlayerAPI mockPlayerApi;
    private ApplicationView mockApplicationView;
    private ApiHandler apiHandler;

    @BeforeEach
    void setUp() {
        // Create mock dependencies
        mockGameInstanceApi = mock(GameInstanceAPI.class);
        mockGameStateApi = mock(GameStateAPI.class);
        mockPlayerApi = mock(PlayerAPI.class);
        mockApplicationView = mock(ApplicationView.class);

        // Mock behavior for PlayerAPI methods
        when(mockPlayerApi.getKeyboardPlayerId()).thenReturn("keyboardPlayer123");
        when(mockPlayerApi.getGamepadPlayerId()).thenReturn("gamepadPlayer456");

        // Create an instance of ApiHandler
        apiHandler = new ApiHandler(mockGameInstanceApi, mockGameStateApi, mockPlayerApi, mockApplicationView);
    }

    @Test
    void testConstructorInitializesApisCorrectly() {
        // Ensure that the APIs are correctly initialized
        assertEquals(mockGameInstanceApi, apiHandler.getGameInstanceApi());
        assertEquals(mockGameStateApi, apiHandler.getGameStateApi());
        assertEquals(mockPlayerApi, apiHandler.getPlayerApi());
    }

    @Test
    void testInputReceiversAreInitialized() {
        // Ensure that input receivers are properly created
        assertNotNull(apiHandler.getInputReceiverKeyboard());
        assertNotNull(apiHandler.getInputReceiverGamePad());
    }

    @Test
    void testPlayersAreCreatedAndRegistered() {
        // Verify that the methods to create players were called exactly once
        verify(mockPlayerApi, times(1)).createPlayerKeyboard();
        verify(mockPlayerApi, times(1)).createPlayerGamepad();

        // Verify that input receivers were registered with the correct Player IDs
        verify(mockPlayerApi, times(1)).registerInputObserver(any(InputReceiver.class), eq("keyboardPlayer123"));
        verify(mockPlayerApi, times(1)).registerInputObserver(any(InputReceiver.class), eq("gamepadPlayer456"));
    }

    @Test
    void testSwitchSceneCallsApplicationView() {
        GameState mockGameState = mock(GameState.class);

        // Call switchScene method
        apiHandler.switchScene(mockGameState);

        // Verify that ApplicationView.switchScene was called with the correct parameter
        verify(mockApplicationView, times(1)).switchScene(mockGameState);
    }
}
