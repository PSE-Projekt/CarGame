package de.cargame.view.selection;

import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;

public class CarSelectionPanel extends SceneButton implements Clickable {

    private final CarType carType;
    private final SelectionInstanceView instanceView;

    public CarSelectionPanel(CarType carType, SelectionInstanceView instanceView) {


        super(getDefaultCarImg(carType), getSelectedCarImg(carType));
        this.carType = carType;
        this.instanceView = instanceView;
    }

    private static String getDefaultCarImg(CarType carType) { //TODO: static + switch best option?
        return switch (carType) {
            case AGILE_CAR -> "/frontend/agileCar_default.png";
            case FAST_CAR -> "/frontend/fastCar_default.png";
            default -> null;
        };
    }

    private static String getSelectedCarImg(CarType carType) {
        return switch (carType) {
            case AGILE_CAR -> "/frontend/agileCar_selected.png";
            case FAST_CAR -> "/frontend/fastCar_selected.png";
            default -> null;
        };
    }

    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getPlayerApi().setCarSelection(playerID, this.carType);
        setLockedInSelection(true);
        instanceView.confirmChoice();
    }
}
