package de.cargame.view.navigation;

import de.cargame.controller.input.UserInputBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class InputReceiverTest {

    private InputReceiver inputReceiver;
    private Navigator mockNavigator;

    @BeforeEach
    void setUp() {
        inputReceiver = new InputReceiver();
        mockNavigator = Mockito.mock(Navigator.class);
    }

    @Test
    public void testAssignNavigator() {
        String playerID = "Player1";

        inputReceiver.assignNavigator(playerID, mockNavigator);
        inputReceiver.update(new UserInputBundle());

        verify(mockNavigator, times(1)).receiveInput(any(UserInputBundle.class), eq(playerID));
    }

    @Test
    public void testUpdate_NotifiesAllNavigators() {
        String player1ID = "Player1";
        String player2ID = "Player2";
        Navigator mockNavigator2 = Mockito.mock(Navigator.class);

        inputReceiver.assignNavigator(player1ID, mockNavigator);
        inputReceiver.assignNavigator(player2ID, mockNavigator2);

        UserInputBundle mockInput = new UserInputBundle();
        inputReceiver.update(mockInput);

        verify(mockNavigator, times(1)).receiveInput(mockInput, player1ID);
        verify(mockNavigator2, times(1)).receiveInput(mockInput, player2ID);
    }

    @Test
    public void testClear_RemovesAllNavigators() {
        inputReceiver.assignNavigator("Player1", mockNavigator);
        inputReceiver.assignNavigator("Player2", Mockito.mock(Navigator.class));

        inputReceiver.clear();
        inputReceiver.update(new UserInputBundle());

        verify(mockNavigator, never()).receiveInput(any(), any());
    }

    @Test
    public void testUpdate_UsesCorrectArguments() {
        String playerID = "Player1";
        inputReceiver.assignNavigator(playerID, mockNavigator);

        UserInputBundle mockInput = new UserInputBundle();
        inputReceiver.update(mockInput);

        ArgumentCaptor<UserInputBundle> inputCaptor = ArgumentCaptor.forClass(UserInputBundle.class);
        ArgumentCaptor<String> playerIDCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockNavigator).receiveInput(inputCaptor.capture(), playerIDCaptor.capture());

        assertEquals(mockInput, inputCaptor.getValue());
        assertEquals(playerID, playerIDCaptor.getValue());
    }
}
