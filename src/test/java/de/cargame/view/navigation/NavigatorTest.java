package de.cargame.view.navigation;

import de.cargame.controller.input.UserInput;
import de.cargame.controller.input.UserInputBundle;
import de.cargame.controller.input.UserInputType;
import de.cargame.model.service.SoundService;
import de.cargame.view.ApiHandler;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NavigatorTest {
    @Mock
    private ApiHandler apiHandler;
    @Mock
    private SoundService soundService;
    private Selectable selectableMock;
    private SceneButton sceneButtonMock;

    private Navigator navigator;

    @BeforeEach
    void setUp() {
        navigator = new TestNavigator(apiHandler);

        selectableMock = new Selectable() {
            @Override
            public void deselect() {
                // do nothing
            }

            @Override
            public void select() {
                // do nothing
            }
        };

        sceneButtonMock = new SceneButton("some path", "some path") {
            @Override
            public void onClick(ApiHandler apiHandler, String playerId) {
                // do nothing
            }
        };
    }

    @Test
    void testReceiveInputNavigation() {
        UserInputBundle inputBundle = mock(UserInputBundle.class);
        when(inputBundle.getLatestInput()).thenReturn(Optional.of(new UserInput(UserInputType.UP)));
        Selectable mockSelectable = mock(Selectable.class);
        when(navigator.getCurrentSelection().getNeighbour(Direction.UP)).thenReturn(mockSelectable);

        navigator.receiveInput(inputBundle, "player1");

        verify(soundService).playChangeSelectionSound();
        verify(navigator.getCurrentSelection()).deselect();
        verify(mockSelectable).select();
        assertEquals(mockSelectable, navigator.getCurrentSelection());
    }

    @Test
    void testReceiveInputClickAction() {
        UserInputBundle inputBundle = mock(UserInputBundle.class);
        when(inputBundle.isFastForward()).thenReturn(true);
        SceneButton clickableMock = mock(SceneButton.class);
        navigator.setCurrentSelection(clickableMock);

        navigator.receiveInput(inputBundle, "player1");

        verify(soundService).playSelectSound();
        verify(clickableMock).onClick(apiHandler, "player1");
    }

    @Test
    void testReset() {
        navigator.setCurrentSelection(selectableMock);
        navigator.reset();

        verify(selectableMock).setLockedInSelection(false);
        verify(selectableMock).deselect();
        assertEquals(navigator.getInitialSelectable(), navigator.getCurrentSelection());
        verify(navigator.getCurrentSelection()).select();
    }

    private static class TestNavigator extends Navigator {
        protected TestNavigator(ApiHandler apiHandler) {
            super(apiHandler);
        }
    }
}
