package de.cargame.controller.input.gamepadmapping;

import lombok.Getter;

@Getter
public enum GamePads {

    XBOX_WIRELESS_CONTROLLER(new XBoxWirelessControllerGamepadPadMapping()),
    PS3_Controller(new Ps3Mapping());


    private final GamePadMapping gamePadMapping;


    GamePads(GamePadMapping gamePadMapping) {
        this.gamePadMapping = gamePadMapping;
    }
}
