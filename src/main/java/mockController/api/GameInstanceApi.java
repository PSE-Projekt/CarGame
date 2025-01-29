package mockController.api;

import mockController.entity.GameModelData;

import java.util.List;

public interface GameInstanceApi {

    void startGamePlayerKeyboard();

    void startGamePlayerGamePad();

    void resetGameInstances();

    List<GameModelData> getModel();
}
