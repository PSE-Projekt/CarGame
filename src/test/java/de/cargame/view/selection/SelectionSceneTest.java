package de.cargame.view.selection;

import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.model.entity.gameobject.car.player.AgileCar;
import de.cargame.model.entity.gameobject.car.player.CarType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectionSceneTest {

    @Test
    public boolean testBackToMenu(){
        GameMode expectedMode = GameMode.NOT_SET;
        GameState expectedState = GameState.MAIN_MENU;
        //TODO: create ApiHandler with Mode=SinglePlayer and State= Selection

        SelectionScene testedScene = new SelectionScene(null);
        //TODO: simulate
        assertEquals(, expectedMode.toString(), "Wrong GameMode outcome");
        assertEquals(, expectedState.toString(), "Wrong GameState outcome");
        return true;
    }

    public boolean testCarSelection(){
        GameState expectedState = GameState.IN_GAME;
        CarType selectedCar = CarType.AGILE_CAR;

        //TODO: create ApiHandler with Mode=SinglePlayer and State= Selection

        SelectionScene testedScene = new SelectionScene(null);

        //simulate
        //TODO: assert players choice
        assertEquals(,selectedCar);
        return true;
    }

}