package de.cargame.view.navigation;

import de.cargame.controller.input.UserInputBundle;
import de.cargame.controller.input.UserInputType;
import de.cargame.model.service.SoundService;
import de.cargame.view.ApiHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NavigatorTest {
    @Mock
    private ApiHandler apiHandler;
    @Mock
    private SoundService soundService;

    private Navigator navigator;
    private Selectable selectableSpy;
    private SceneButton sceneButtonSpy;

    @BeforeEach
    void setUp() {
        navigator = new TestNavigator(apiHandler);

        Selectable selectable = new Selectable() {
            @Override
            public void deselect() {
                // do nothing
            }

            @Override
            public void select() {
                // do nothing
            }
        };

        SceneButton sceneButton = new SceneButton("/frontend/backToMenu_default.png", "/frontend/backToMenu_selected.png") {
            @Override
            public void onClick(ApiHandler apiHandler, String playerId) {
                // do nothing
            }
        };

        this.selectableSpy = spy(selectable);
        this.sceneButtonSpy = spy(sceneButton);
    }

    @Test
    void testReceiveInputNavigation() {
        Selectable selectableSpy2 = spy(new Selectable() {
            @Override
            public void deselect() { /* dummy */ }

            @Override
            public void select() { /* dummy */ }
        });

        UserInputBundle inputBundle = new UserInputBundle();
        inputBundle.addUserInput(UserInputType.UP);
        navigator.currentSelection = selectableSpy;
        selectableSpy.setNeighbour(Direction.UP, selectableSpy2);

        navigator.receiveInput(inputBundle, "player1");

        verify(selectableSpy).deselect();
        verify(selectableSpy2).select();
        assertEquals(selectableSpy2, navigator.getCurrentSelection());
    }

    @Test
    void testReceiveInputClickAction() {
        UserInputBundle inputBundle = new UserInputBundle();
        inputBundle.addUserInput(UserInputType.CONFIRM);

        assertTrue(inputBundle.isFastForward());

        navigator.setCurrentSelection(sceneButtonSpy);

        navigator.receiveInput(inputBundle, "player1");

        verify(sceneButtonSpy).onClick(apiHandler, "player1");
    }

    @Test
    void testReset() {
        navigator.setCurrentSelection(selectableSpy);
        navigator.reset();

        assertEquals(navigator.getInitialSelectable(), navigator.getCurrentSelection());
    }

    private static class TestNavigator extends Navigator {
        protected TestNavigator(ApiHandler apiHandler) {
            super(apiHandler);
        }
    }
}
