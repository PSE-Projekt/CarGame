package de.cargame.controller;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.model.service.PlayerService;
import de.cargame.view.ApplicationView;
import javafx.application.Platform;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class GameApplicationManager {


    private final GameStateAPI gameStateAPI = new GameStateController();
    private final PlayerAPI playerAPI = new PlayerController(new PlayerService());
    private final GameInstanceAPI gameInstanceAPI = new GameInstanceController(this, gameStateAPI, playerAPI);

    private ApplicationView applicationView;


    public void renderGameInstance() {
        Platform.runLater(() -> applicationView.renderGame());

    }

    public void registerApplicationView(ApplicationView applicationView) {
        this.applicationView = applicationView;
    }
}
