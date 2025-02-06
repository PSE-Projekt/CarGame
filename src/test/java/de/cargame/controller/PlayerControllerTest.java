package de.cargame.controller;

import de.cargame.exception.PlayerNotFoundException;
import de.cargame.model.entity.player.Player;
import de.cargame.model.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {


    Player testPlayer = new Player();
    @Mock
    private PlayerService playerService;
    @InjectMocks
    private PlayerController playerController;

    @Test
    void testGetKeyboardPlayer_KeyboardPlayer_exists() {

        //when
        when(playerService.getKeyboardPlayer()).thenReturn(testPlayer);
        Player keyboardPlayer = playerController.getKeyboardPlayer();


        //then
        assertEquals(keyboardPlayer, testPlayer);
    }

    @Test
    void testGetGamePadPlayer_GamePadPlayer_exists() {

        //when
        when(playerService.getGamepadPlayer()).thenReturn(testPlayer);
        Player gamepadPlayer = playerController.getGamepadPlayer();


        //then
        assertEquals(gamepadPlayer, testPlayer);
    }

    @Test
    void testGetKeyboardPlayer_KeyboardPlayer_doesNotExist() {
        assertThrows(PlayerNotFoundException.class, () -> playerController.getKeyboardPlayer());
    }

    @Test
    void testGetGamePadPlayer_GamePadPlayer_doesNotExist() {
        assertThrows(PlayerNotFoundException.class, () -> playerController.getGamepadPlayer());
    }
}
