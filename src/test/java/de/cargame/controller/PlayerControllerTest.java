package de.cargame.controller;

import de.cargame.exception.PlayerNotFoundException;
import de.cargame.model.entity.player.Player;
import de.cargame.model.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PlayerControllerTest {


    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;


    Player testPlayer = new Player();


    @Test
    public void testGetKeyboardPlayer_KeyboardPlayer_exists(){

        //when
        when(playerService.getKeyboardPlayer()).thenReturn(testPlayer);
        Player keyboardPlayer = playerController.getKeyboardPlayer();


        //then
        assertEquals(keyboardPlayer, testPlayer);
    }

    @Test
    public void testGetGamePadPlayer_GamePadPlayer_exists(){

        //when
        when(playerService.getGamepadPlayer()).thenReturn(testPlayer);
        Player gamepadPlayer = playerController.getGamepadPlayer();


        //then
        assertEquals(gamepadPlayer, testPlayer);
    }

    @Test
    public void testGetKeyboardPlayer_KeyboardPlayer_doesNotExist(){
        assertThrows(PlayerNotFoundException.class, () -> playerController.getKeyboardPlayer());
    }

    @Test
    public void testGetGamePadPlayer_GamePadPlayer_doesNotExist(){
        assertThrows(PlayerNotFoundException.class, () -> playerController.getGamepadPlayer());
    }
}
